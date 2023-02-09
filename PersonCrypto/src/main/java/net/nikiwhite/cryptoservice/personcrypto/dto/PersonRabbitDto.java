package net.nikiwhite.cryptoservice.personcrypto.dto;

import lombok.Getter;
import lombok.Setter;
import net.nikiwhite.cryptoservice.personcrypto.model.Role;

@Getter
@Setter
public class PersonRabbitDto {

    private String name;

    private String surname;

    private String middleName;

    private String email;

    private Role role;
}
