package net.nikiwhite.cryptorabbitmq.util;

import net.nikiwhite.cryptorabbitmq.model.Person;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit
public class RabbitMqListener {

    //todo возможно добавить какую-то логику

    private final RabbitTemplate rabbitTemplate;

    public RabbitMqListener(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "myQueue1")
    public void sendPerson(Person person) {
        rabbitTemplate.convertAndSend("exchange", "person", person);
    }
}
