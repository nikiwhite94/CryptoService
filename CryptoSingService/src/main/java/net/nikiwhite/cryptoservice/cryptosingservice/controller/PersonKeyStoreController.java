package net.nikiwhite.cryptoservice.cryptosingservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.nikiwhite.cryptoservice.cryptosingservice.service.PersonKeyStoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/keystore")
public class PersonKeyStoreController {

    private final PersonKeyStoreService personKeyStoreService;

    @SneakyThrows
    @PostMapping("/{password}&{email}")
    public ResponseEntity<HttpStatus> addKeyStore(@RequestParam("file") MultipartFile file,
                                                  @PathVariable("password") String password,
                                                  @PathVariable("email") String email) {
        personKeyStoreService.addKeyStore(file, password, email);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<HttpStatus> deleteKeyStore(@PathVariable("email") String email) {
        personKeyStoreService.deleteKeyStore(email);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
