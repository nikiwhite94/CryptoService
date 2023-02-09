package net.nikiwhite.cryptoservice.cryptosingservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Map;

@Service
public interface SignService {

    String signFile(MultipartFile file, String email)
            throws NoSuchAlgorithmException, InvalidKeyException, IOException, SignatureException;

    void verifySign(MultipartFile file, String email)
            throws IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException;

    Map<String, String> getAllSigns(String email);
}
