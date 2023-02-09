package net.nikiwhite.cryptoservice.cryptosingservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.nikiwhite.cryptoservice.cryptosingservice.service.SignService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
public class SignatureController {

    private final SignService signService;

    @SneakyThrows
    @PostMapping("/sign/sign-file/{email}")
    public ResponseEntity<String> signFile(@RequestParam("file") MultipartFile file,
                                           @PathVariable("email") String email) {
        return ResponseEntity.ok(signService.signFile(file, email));
    }

    @SneakyThrows
    @PostMapping("/sign/verify-sign/{email}")
    public ResponseEntity<HttpStatus> verifySign(@RequestParam("file") MultipartFile file,
                                                 @PathVariable("email") String email) {
        signService.verifySign(file, email);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/signs/{email}")
    public ResponseEntity<Map<String, String>> getAllSings(@PathVariable("email") String email) {
        return ResponseEntity.ok(signService.getAllSigns(email));
    }
}
