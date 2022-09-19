package core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PreProcessor implements BeanPostProcessor {


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        if(bean instanceof KafkaProperties){
            log.info("Found KafkaProperties");
            var consumer = ((KafkaProperties) bean).getConsumer();
            log.info("Kafka consumer.bootstrap-servers {}",consumer.getBootstrapServers());
            log.info("Kafka consumer.key-deserializer {}",consumer.getKeyDeserializer().getCanonicalName());
            log.info("Kafka consumer.value-deserializer {}",consumer.getValueDeserializer().getCanonicalName());

        }

        return bean;
    }
}
