package net.nikiwhite.cryptoservice.personcrypto.security;


import lombok.RequiredArgsConstructor;
import net.nikiwhite.cryptoservice.personcrypto.model.Person;
import net.nikiwhite.cryptoservice.personcrypto.repository.PersonRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Person> person = personRepository.findByEmail(email);

        if (person.isEmpty()) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }

        return new PersonDetails(person.get());
    }
}
