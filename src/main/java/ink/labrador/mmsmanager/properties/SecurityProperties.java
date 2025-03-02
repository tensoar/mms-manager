package ink.labrador.mmsmanager.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

@Configuration
@ConfigurationProperties("mms.security")
@Data
public class SecurityProperties {
    private Integer tokenExpireTimeInMinute = 1440;
    private List<String> ignoredPaths = Collections.emptyList();
    private String tokenHeaderName = "X-ACCESS-TOKEN";
    private String tokenPrefix = "";
}
