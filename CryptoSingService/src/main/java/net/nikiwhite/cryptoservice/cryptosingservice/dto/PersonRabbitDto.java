package net.nikiwhite.cryptoservice.cryptosingservice.dto;

import lombok.Getter;
import lombok.Setter;
import net.nikiwhite.cryptoservice.cryptosingservice.model.Role;

@Getter
@Setter
public class PersonRabbitDto {

    private String name;

    private String surname;

    private String middleName;

    private String email;

    private Role role;
}
