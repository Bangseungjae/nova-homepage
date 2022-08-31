package nova.novahomepage.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class JasyptController {
    private final StringEncryptor jasyptStringEncryptor;


    @PostMapping("/encrypt")
    public String encryptUsingRequest(@RequestBody String re) {

        log.info(re);

        StringEncryptor newStringEncryptor = createEncryptor("enc");
        String resultText = newStringEncryptor.encrypt(re);
        String encrypt = jasyptStringEncryptor.encrypt(re);
        log.info("encrypted1 = {}", resultText);
        log.info("encrypted2 = {}", encrypt);

        return resultText;
    }

    private StringEncryptor createEncryptor(String password){

        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(password);
        config.setAlgorithm("PBEWithMD5AndTripleDES"); // 권장되는 기본 알고리즘
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);

        return encryptor;
    }
}
