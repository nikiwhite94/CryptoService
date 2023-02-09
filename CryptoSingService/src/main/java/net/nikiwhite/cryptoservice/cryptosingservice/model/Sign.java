package net.nikiwhite.cryptoservice.cryptosingservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sign")
public class Sign {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "sign_byte")
    private byte[] signByte;

    //todo додумать логику
    @Column(name = "price")
    private Double price;

    @NotNull
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @NotBlank
    @Column(name = "download_url")
    private String downloadUrl;

    //todo написать логику перемещения подписей в архив при замене PersonKeytore
    @Column(name = "is_active")
    boolean isActive;

    @JsonIgnore
    @NotNull
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;

    @JsonIgnore
    @NotNull
    @ManyToOne
    @JoinColumn(name = "key_store_id", referencedColumnName = "id")
    private PersonKeyStore personKeyStore;
}
