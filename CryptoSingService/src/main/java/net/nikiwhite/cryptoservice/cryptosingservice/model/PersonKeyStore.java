package net.nikiwhite.cryptoservice.cryptosingservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "key_store")
public class PersonKeyStore {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "public_key")
    private PublicKey publicKey;

    @NotNull
    @Column(name = "private_key")
    private PrivateKey privateKey;

    @NotNull
    @Column(name = "certificate")
    private X509Certificate certificate;

    @NotNull
    @Column(name = "valid_from")
    private Date validFrom;

    @NotNull
    @Column(name = "valid_util")
    private Date validUtil;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;

    @JsonIgnore
    @OneToMany(mappedBy = "personKeyStore",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Sign> signatures;
}
