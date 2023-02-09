package net.nikiwhite.cryptoservice.cryptosingservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Min(0)
    @Column(name = "balance")
    private Double balance;

    @JsonIgnore
    @OneToMany(mappedBy = "person",
            cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private List<Sign> signatures;

    @JsonIgnore
    @OneToOne(mappedBy = "person",
            cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private PersonKeyStore personKeyStore;
}
