package net.nikiwhite.cryptoservice.personcrypto.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.nikiwhite.cryptoservice.personcrypto.dto.PersonLoginDto;
import net.nikiwhite.cryptoservice.personcrypto.dto.PersonRegDto;
import net.nikiwhite.cryptoservice.personcrypto.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @PostMapping("/persons/")
    public ResponseEntity<HttpStatus> addPerson(@RequestBody @Valid PersonRegDto regDto) {
        personService.addPerson(regDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/person/login")
    public Map<String, String> login(@RequestBody @Valid PersonLoginDto loginDto) {
        return personService.loginPerson(loginDto);
    }
}
