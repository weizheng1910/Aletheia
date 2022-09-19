package core.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class CustomBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof KafkaAdmin){
            log.info("org.springframework.kafka.core.KafkaAdmin bean is present");
            var obj = (KafkaAdmin) bean;
            var configProperties = obj.getConfigurationProperties();
            for(var e : configProperties.entrySet()){
                var key = e.getKey();
                var value = e.getValue();
                System.out.println(key + " : " + value.toString());
            }
        }

        if(bean instanceof ProducerFactory){
            log.info("org.springframework.kafka.core.ProducerFactory bean is present");
            var obj = (ProducerFactory) bean;
            Map<String,Object> configProperties = obj.getConfigurationProperties();
            for(var e : configProperties.entrySet()){
                var key = e.getKey();
                var value = e.getValue();
                System.out.println(key + ": " + value.toString());
            }
        }

        return bean;
    }
}
