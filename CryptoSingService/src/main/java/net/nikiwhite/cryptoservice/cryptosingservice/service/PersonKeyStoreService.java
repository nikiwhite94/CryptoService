package net.nikiwhite.cryptoservice.cryptosingservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

public interface PersonKeyStoreService {

    void addKeyStore(MultipartFile file, String password, String email) throws
            IOException,
            KeyStoreException,
            CertificateException,
            NoSuchAlgorithmException,
            UnrecoverableEntryException;

    void deleteKeyStore(String email);
}
