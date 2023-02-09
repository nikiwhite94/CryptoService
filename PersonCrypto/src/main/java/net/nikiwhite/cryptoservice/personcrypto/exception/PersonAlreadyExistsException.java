package net.nikiwhite.cryptoservice.personcrypto.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonAlreadyExistsException extends RuntimeException {
    public String message = "Пользователь с таким Email уже зарегистрирован";
}
