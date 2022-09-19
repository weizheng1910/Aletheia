package core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "aletheia")
@Getter
@Setter
public class WebClientConfig {
    private String apiKey;
    private String baseUri;
}
