package net.nikiwhite.cryptoservice.personcrypto.service;

import net.nikiwhite.cryptoservice.personcrypto.dto.PersonLoginDto;
import net.nikiwhite.cryptoservice.personcrypto.dto.PersonRegDto;
import net.nikiwhite.cryptoservice.personcrypto.model.Person;

import java.util.Map;
import java.util.Optional;

public interface PersonService {

    void addPerson(PersonRegDto dto);

    Map<String, String> loginPerson(PersonLoginDto loginDto);

    Optional<Person> findByEmail(String email);
}
