package net.nikiwhite.cryptoservice.cryptosingservice.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonIsHomelessException extends RuntimeException {
    public String message = "На вашем счете недостаточно средств";
}
