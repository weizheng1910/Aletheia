package core.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.client.WebClientService;
import core.mapper.Mapper;
import core.pojos.Insider;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericData;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ApiService {
    @Autowired
    private WebClientService webClientService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private Mapper avroMapper;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(Object msg) {
        kafkaTemplate.send("Insider", msg);
    }

    public void callExampleAletheiaMethod() throws Exception{

        var results = webClientService.sampleApiCall_LatestTransaction();

        var pojos = Arrays.stream(results).map(object -> mapper.convertValue(object,Insider.class)).collect(Collectors.toList());

        List<SpecificRecord> avroList = new ArrayList<>();
        
        for(var pojo: pojos){
            var record = (SpecificRecord) avroMapper.mapJavaPojoToAvroRecord(pojo, "core.avro.Insider");
            sendMessage(record);
        }

        

    }
}
