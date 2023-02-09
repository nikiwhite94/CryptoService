package net.nikiwhite.cryptoservice.personcrypto.converter;

import lombok.RequiredArgsConstructor;
import net.nikiwhite.cryptoservice.personcrypto.dto.PersonRabbitDto;
import net.nikiwhite.cryptoservice.personcrypto.dto.PersonRegDto;
import net.nikiwhite.cryptoservice.personcrypto.model.Person;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonConverter {

    private final ModelMapper mapper = new ModelMapper();
    private final PasswordEncoder passwordEncoder;

    public Person regDtoToPerson(PersonRegDto regDto) {
        regDto.setPassword(passwordEncoder.encode(regDto.getPassword()));
        return mapper.map(regDto, Person.class);
    }

    public PersonRabbitDto personToRabbitDto(Person person) {
        return mapper.map(person, PersonRabbitDto.class);
    }
}
