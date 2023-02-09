package net.nikiwhite.cryptoservice.personcrypto.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonRegDto {

    private static final String MESSAGE = " не может может содержать больше 33 символов";

    @NotBlank(message = "Введите имя")
    @Size(max = 33, message = "Имя" + MESSAGE)
    private String name;

    @Size(max = 33, message = "Фамилия" + MESSAGE)
    private String surname;

    @Size(max = 33, message = "Отчество" + MESSAGE)
    private String middleName;

    @Email(message = "Невалидный Email")
    @NotBlank(message = "Введите email")
    private String email;

    @NotBlank(message = "Введите пароль")
    @Size(min = 8, max = 16, message = "Пароль должен содержать от 8 до 16 символов")
    private String password;
}
