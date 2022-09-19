package core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Deprecated
@Component
@ConfigurationProperties(prefix = "spring.kafka.producer")
@Getter
@Setter
public class KafkaConfig {
    private String keySerializer;
    private String valueSerializer;
    private String bootStrapServers;
    private String schemaRegistry;
}
