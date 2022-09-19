package core;

import core.avro.Insider;
import core.config.OtherProperties;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

@SpringBootApplication
public class Application {

  @Autowired KafkaProperties kafkaProperties;
  @Autowired OtherProperties otherProperties;

  public static void main(String[] args) {
    ApplicationContext context = SpringApplication.run(Application.class, args);
  }

  @Bean
  public ConsumerFactory<String, String> consumerFactory() {
    var props = kafkaProperties.getConsumer().buildProperties();
    props.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG,otherProperties.getSchemaRegistry());
    props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, kafkaProperties.getProperties().get("specific.avro.reader"));
    return new DefaultKafkaConsumerFactory<>(props);
  }

  @KafkaListener(topics = "Insider", groupId = "foo")
  public void listen(SpecificRecord message) {
    System.out.println("YAS");
    System.out.println("Received Message in group foo: " + message);
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, String> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());
    return factory;
  }
}
