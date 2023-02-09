package net.nikiwhite.cryptoservice.cryptosingservice.service;

import lombok.RequiredArgsConstructor;
import net.nikiwhite.cryptoservice.cryptosingservice.model.Person;
import net.nikiwhite.cryptoservice.cryptosingservice.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("personService")
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Transactional
    @Override
    public void savePerson(Person person) {
        person.setBalance(100.0);
        personRepository.save(person);
    }
}
