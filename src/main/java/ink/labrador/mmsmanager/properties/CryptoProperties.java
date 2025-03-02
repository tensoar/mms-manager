package ink.labrador.mmsmanager.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("mms.crypto")
@Data
public class CryptoProperties {
    private RSAProperties rsa = new RSAProperties();

    @Data
    public static class RSAProperties {
        private String publicKeyFile;
        private String privateKeyFile;
    }
}
