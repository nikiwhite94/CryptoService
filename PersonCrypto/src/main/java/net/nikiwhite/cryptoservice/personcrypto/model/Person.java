package net.nikiwhite.cryptoservice.personcrypto.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "person",
        uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
public class Person {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @NotBlank
    @Size(max = 33)
    @Column(name = "name")
    private String name;

    @Size(max = 33)
    @Column(name = "surname")
    private String surname;

    @Size(max = 33)
    @Column(name = "middle_name")
    private String middleName;

    @Email
    @NotBlank
    @Column(name = "email")
    private String email;

    @NotBlank
    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
}
