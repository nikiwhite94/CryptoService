package net.nikiwhite.cryptorabbitmq.model;

import lombok.Data;

@Data
public class Person {

    private String name;

    private String surname;

    private String middleName;

    private String email;

    private Role role;
}
