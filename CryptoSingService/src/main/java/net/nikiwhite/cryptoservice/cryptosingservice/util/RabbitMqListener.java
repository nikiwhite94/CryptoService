package net.nikiwhite.cryptoservice.cryptosingservice.util;

import lombok.RequiredArgsConstructor;
import net.nikiwhite.cryptoservice.cryptosingservice.converter.PersonConverter;
import net.nikiwhite.cryptoservice.cryptosingservice.dto.PersonRabbitDto;
import net.nikiwhite.cryptoservice.cryptosingservice.service.PersonService;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit
@RequiredArgsConstructor
public class RabbitMqListener {

    private final PersonService personService;
    private final PersonConverter personConverter;

    @RabbitListener(queues = "myQueue2")
    public void savePerson(PersonRabbitDto rabbitDto) {
        personService.savePerson(personConverter.rabbitDtoToPerson(rabbitDto));
    }
}
