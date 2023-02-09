package net.nikiwhite.cryptoservice.cryptosingservice.yandexDisk;

import net.nikiwhite.cryptoservice.cryptosingservice.model.Person;
import net.nikiwhite.cryptoservice.cryptosingservice.model.PersonKeyStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Component
@PropertySource("classpath:application-secret.properties")
public class YandexDiskClient {

    @Value("${yandex.token}")
    private String yandexToken;
    @Value("${yandex.folder-url}")
    private String folderUrl;
    @Value("${yandex.upload-url}")
    private String uploadUrl;
    @Value("${yandex.download-url}")
    private String downloadUlr;
    @Value("${yandex.upload-overwrite}")
    private String overWrite;
    @Value("${yandex.delete-permanently}")
    private String deletePermanently;
    @Value("${keystore.signature-format}")
    private String signatureFormat;
    private static final String AUTHORIZATION = "Authorization";

    private final RestTemplate restTemplate = new RestTemplate();

    public String uploadSign(MultipartFile file, byte[] fileSignature, Person person) {

        YandexDiskResponse upload = restTemplate.exchange(
                        uploadUrl + person.getId() + "/"
                                + file.getOriginalFilename() + signatureFormat + overWrite,
                        HttpMethod.GET,
                        getAuthorization(),
                        YandexDiskResponse.class)
                .getBody();

        restTemplate.put(upload.getHref(), fileSignature);

        YandexDiskResponse getDownloadUrl = restTemplate.exchange(
                        downloadUlr + person.getId() + "/"
                                + file.getOriginalFilename() + signatureFormat,
                        HttpMethod.GET,
                        getAuthorization(),
                        YandexDiskResponse.class)
                .getBody();

        return getDownloadUrl.getHref();
    }

    public void deleteFolder(PersonKeyStore personKeyStore) {

        restTemplate.exchange(
                folderUrl + personKeyStore.getPerson().getId() + deletePermanently,
                HttpMethod.DELETE,
                getAuthorization(),
                YandexDiskResponse.class
        );
    }

    public void createFolder(PersonKeyStore personKeyStore) {

        restTemplate.exchange(
                folderUrl + personKeyStore.getPerson().getId(),
                HttpMethod.PUT,
                getAuthorization(),
                YandexDiskResponse.class
        );
    }

    private HttpEntity<Object> getAuthorization() {

        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION, yandexToken);
        return new HttpEntity<>(headers);
    }
}
