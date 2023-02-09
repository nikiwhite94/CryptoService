package net.nikiwhite.cryptoservice.personcrypto.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonLoginDto {

    @Email(message = "Невалидный Email")
    @NotBlank(message = "Введите Email")
    private String email;

    @NotBlank(message = "Введите пароль")
    @Size(min = 8, max = 16, message = "Пароль должен содержать от 8 до 16 символов")
    private String password;
}
