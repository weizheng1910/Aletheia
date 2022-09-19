package core;

import core.config.KafkaConfig;
import core.service.ApiService;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import org.apache.avro.generic.GenericData;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;


@SpringBootApplication
public class Application {
    @Autowired
    KafkaConfig kafkaConfig;

    @Autowired
    KafkaProperties kafkaProperties;

    @Value("${spring.kafka.producer.schemaRegistry}")
    private String confluentSchemaRegistry;

    public static void main(String[] args) throws Exception{
        ApplicationContext context = SpringApplication.run(Application.class, args);
        var apiService = context.getBean(ApiService.class);
        apiService.callExampleAletheiaMethod();
    }

    @Bean
    public NewTopic InsiderTopic() {
        return new NewTopic("Insider", 1, (short) 1);
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory() {

        var producerProperties = kafkaProperties.getProducer();

        Map<String,Object> prop = new HashMap<>();
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,producerProperties.getBootstrapServers());
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,producerProperties.getKeySerializer());
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,producerProperties.getValueSerializer());
        prop.put(KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG,confluentSchemaRegistry);

        return new DefaultKafkaProducerFactory<>(prop);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        Map<String,Object> prop = new HashMap<>();
        return new KafkaTemplate<>(producerFactory(),true);
    }


}
