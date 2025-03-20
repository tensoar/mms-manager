package ink.labrador.mmsmanager.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("mms.cors")
@Data
public class CorsProperties {
    private Boolean allowCredentials = false;
    private String[] mappings = new String[]{"/**"};
    private String[] allowedOriginPatterns = new String[]{"*"};
    private String[] allowedMethods = new String[]{"GET", "POST", "PUT", "DELETE", "OPTIONS"};
    private String[] allowedHeaders = new String[]{"X-Access-Token"};
    private String[] exposedHeaders = new String[]{"*"};
}
