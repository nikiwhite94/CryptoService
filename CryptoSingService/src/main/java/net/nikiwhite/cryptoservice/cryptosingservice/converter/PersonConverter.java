package net.nikiwhite.cryptoservice.cryptosingservice.converter;

import net.nikiwhite.cryptoservice.cryptosingservice.dto.PersonRabbitDto;
import net.nikiwhite.cryptoservice.cryptosingservice.model.Person;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PersonConverter {

    private final ModelMapper mapper = new ModelMapper();

    public Person rabbitDtoToPerson(PersonRabbitDto rabbitDto) {
        return mapper.map(rabbitDto, Person.class);
    }
}
