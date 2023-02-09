package net.nikiwhite.cryptoservice.cryptosingservice.service;


import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.nikiwhite.cryptoservice.cryptosingservice.model.Person;
import net.nikiwhite.cryptoservice.cryptosingservice.model.PersonKeyStore;
import net.nikiwhite.cryptoservice.cryptosingservice.repository.PersonKeyStoreRepository;
import net.nikiwhite.cryptoservice.cryptosingservice.repository.PersonRepository;
import net.nikiwhite.cryptoservice.cryptosingservice.util.MailSender;
import net.nikiwhite.cryptoservice.cryptosingservice.yandexDisk.YandexDiskClient;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.X509Certificate;

//todo по возможности сделать рефакторинг

@RequiredArgsConstructor
@Service("personKeyStoreService")
@Transactional(readOnly = true)
public class PersonKeyStoreServiceImpl implements PersonKeyStoreService {

    private final PersonKeyStoreRepository personKeyStoreRepository;
    private final PersonRepository personRepository;
    private final YandexDiskClient yandexDiskClient;
    private final MailSender mailSender;

    @SneakyThrows
    @Transactional
    @Override
    public void addKeyStore(MultipartFile file, String password, String email) {

        if (file.isEmpty()) {
            throw new FileNotFoundException("Загрузите файл");
        } else {
            KeyStore loadKeyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            try {
                loadKeyStore.load(file.getInputStream(), password.toCharArray());
            } catch (IOException e) {
                throw new KeyStoreException("Неправильный пароль");
            }
            X509Certificate certificate = (X509Certificate) loadKeyStore
                    .getCertificate(FilenameUtils.getBaseName(file.getOriginalFilename()));

            KeyStore.ProtectionParameter entryPassword = new KeyStore.PasswordProtection(password.toCharArray());
            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) loadKeyStore
                    .getEntry(FilenameUtils.getBaseName(file.getOriginalFilename()), entryPassword);

            Person person = personRepository.findByEmail(email);

            if (person.getPersonKeyStore() != null) {
                if (person.getPersonKeyStore().getCertificate().equals(certificate)) {
                    throw new KeyStoreException("Данный сертификат уже установлен");
                } else deleteKeyStore(email);
            }

            PersonKeyStore keyStore = new PersonKeyStore();
            keyStore.setPerson(person);
            keyStore.setCertificate(certificate);
            keyStore.setName(file.getOriginalFilename());
            keyStore.setValidFrom(certificate.getNotBefore());
            keyStore.setValidUtil(certificate.getNotAfter());
            keyStore.setPublicKey(certificate.getPublicKey());
            keyStore.setPrivateKey(privateKeyEntry.getPrivateKey());

            personKeyStoreRepository.save(keyStore);

            yandexDiskClient.createFolder(keyStore);

            mailSender.send(person.getEmail(), "Добавление сертификата",
                    "Вы успешно добавили новый сертификат \"" + file.getOriginalFilename() + "\"!");
        }
    }

    @Override
    @Transactional
    public void deleteKeyStore(String email) {
        Person person = personRepository.findByEmail(email);

        personKeyStoreRepository.deleteByPerson(person);

        yandexDiskClient.deleteFolder(person.getPersonKeyStore());

        mailSender.send(person.getEmail(), "Удаление сертификата",
                "Вы успешно удалили сертификат \"" + person.getPersonKeyStore().getName() + "\"!");
    }
}
