package net.nikiwhite.cryptoservice.cryptosingservice.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.nikiwhite.cryptoservice.cryptosingservice.exception.PersonIsHomelessException;
import net.nikiwhite.cryptoservice.cryptosingservice.model.Person;
import net.nikiwhite.cryptoservice.cryptosingservice.model.PersonKeyStore;
import net.nikiwhite.cryptoservice.cryptosingservice.model.Sign;
import net.nikiwhite.cryptoservice.cryptosingservice.repository.PersonRepository;
import net.nikiwhite.cryptoservice.cryptosingservice.repository.SignRepository;
import net.nikiwhite.cryptoservice.cryptosingservice.yandexDisk.YandexDiskClient;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.security.Security;
import java.security.Signature;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//todo по возможности сделать рефакторинг

@RequiredArgsConstructor
@Service("signService")
@Transactional(readOnly = true)
public class SignServiceImpl implements SignService {

    private final YandexDiskClient yandexDiskClient;
    private final SignRepository signRepository;
    private final PersonRepository personRepository;

    @SneakyThrows
    @Transactional
    @Override
    public String signFile(MultipartFile file, String email) {

        if (file.isEmpty()) {
            throw new FileNotFoundException("Загрузите файл");
        } else {
            Security.addProvider(new BouncyCastleProvider());

            Person person = personRepository.findByEmail(email);

            Sign sign;
            if (signRepository.findByNameAndPerson(file.getOriginalFilename(), person) == null) {
                sign = new Sign();
                sign.setPerson(person);
            } else {
                sign = signRepository.findByNameAndPerson(file.getOriginalFilename(), person);
            }
            PersonKeyStore personKeyStore = person.getPersonKeyStore();

            byte[] fileSignature = signAndGetSignBytes(file, personKeyStore);

            sign.setSignByte(fileSignature);
            sign.setPersonKeyStore(personKeyStore);
            sign.setCreatedAt(LocalDateTime.now());
            sign.setName(file.getOriginalFilename());

            signRepository.save(sign);

            String downloadUrl = yandexDiskClient.uploadSign(file, fileSignature, person);

            sign.setDownloadUrl(downloadUrl);

            return downloadUrl;
        }
    }

    @SneakyThrows
    private static byte[] signAndGetSignBytes(MultipartFile file, PersonKeyStore personKeyStore) {
        Signature signature = Signature.getInstance(personKeyStore.getCertificate().getSigAlgName());
        signature.initSign(personKeyStore.getPrivateKey());
        signature.update(file.getBytes());

        return signature.sign();
    }

    @SneakyThrows
    @Override
    public void verifySign(MultipartFile file, String email) {

        if (file.isEmpty()) {
            throw new FileNotFoundException("Загрузите файл");
        } else {
            Security.addProvider(new BouncyCastleProvider());

            Person person = personRepository.findByEmail(email);

            byte[] signByte;
            try {
                signByte = signRepository
                        .findByNameAndPerson(file.getOriginalFilename(), person)
                        .getSignByte();
            } catch (NullPointerException e) {
                throw new RuntimeException("У данного файла нет подписи в нашем сервисе");
            }
            PersonKeyStore personKeyStore = person.getPersonKeyStore();

            Signature signature = initVerify(file, personKeyStore);

            if (!signature.verify(signByte)) {
                throw new RuntimeException("Данный файл не соответствует подписи");
            }
        }
    }

    @SneakyThrows
    private static Signature initVerify(MultipartFile file, PersonKeyStore personKeyStore) {
        Signature signature = Signature.getInstance(personKeyStore.getCertificate().getSigAlgName());
        signature.initVerify(personKeyStore.getPublicKey());
        signature.update(file.getBytes());
        return signature;
    }

    @Transactional
    @Override
    public Map<String, String> getAllSigns(String email) {

        Person person = personRepository.findByEmail(email);

        if (person.getSignatures().isEmpty()) {
            throw new RuntimeException("У вас нет подписей");
        } else {
            try {
                person.setBalance(person.getBalance() - 100.0);
            } catch (Exception e) {
                throw new PersonIsHomelessException();
            }
            List<Sign> signs = signRepository.findByPerson(person);

            return signs.stream().collect(
                    Collectors.toMap(Sign::getName, Sign::getDownloadUrl)
            );
        }
    }
}
