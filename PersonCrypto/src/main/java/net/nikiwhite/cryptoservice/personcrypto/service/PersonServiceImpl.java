package net.nikiwhite.cryptoservice.personcrypto.service;

import lombok.RequiredArgsConstructor;
import net.nikiwhite.cryptoservice.personcrypto.converter.PersonConverter;
import net.nikiwhite.cryptoservice.personcrypto.dto.PersonLoginDto;
import net.nikiwhite.cryptoservice.personcrypto.dto.PersonRegDto;
import net.nikiwhite.cryptoservice.personcrypto.exception.PersonAlreadyExistsException;
import net.nikiwhite.cryptoservice.personcrypto.model.Person;
import net.nikiwhite.cryptoservice.personcrypto.model.Role;
import net.nikiwhite.cryptoservice.personcrypto.repository.PersonRepository;
import net.nikiwhite.cryptoservice.personcrypto.security.JwtUtil;
import net.nikiwhite.cryptoservice.personcrypto.util.MailSender;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service("personService")
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final AuthenticationManager authenticationManager;
    private final PersonRepository personRepository;
    private final PersonConverter personConverter;
    private final RabbitTemplate rabbitTemplate;
    private final MailSender mailSender;
    private final JwtUtil jwtUtil;

    @Transactional
    @Override
    public void addPerson(PersonRegDto regDto) {

        if (personRepository.findByEmail(regDto.getEmail()).isPresent()) {
            throw new PersonAlreadyExistsException();
        } else {

            Person person = personConverter.regDtoToPerson(regDto);
            person.setRole(Role.USER);

            personRepository.save(person);

            rabbitTemplate.convertAndSend("exchange", "rabbit",
                    personConverter.personToRabbitDto(person));

            mailSender.send(regDto.getEmail(), "CryptoPersonService",
                    "Добро пожаловать, " + regDto.getName() + "! Вы успешно зарегистрировались.");
        }
    }

    @Override
    public Map<String, String> loginPerson(PersonLoginDto loginDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                );
        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            return Map.of("message", "Неправильная почта или пароль");
        }

        String token = jwtUtil.generateToken(loginDto.getEmail());
        return Map.of("jwtToken", token);
    }

    @Override
    public Optional<Person> findByEmail(String email) {
        return personRepository.findByEmail(email);
    }
}
