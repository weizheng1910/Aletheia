
Jackson serializing only works when exact.

* How to organize the webclient
* How to organize java config 

## 
* spring-boot-starter-parent 

## Plan
* Send to Kafka via avro
* Create KafkaProducer 
  * KafkaConfig  
  * KafkaTemplate
* Create Kafka aliases - [X]
* Create Schema Registry
* Create Topic 
* Serialize

https://docs.confluent.io/3.3.0/quickstart.html 

### Avro serialization
* Need to convert format into Avro. It will be the format in which it is sent to the Kafka broker.
* Need to map it from Java Pojo to Avro Pojo. 
* Avro Pojo can be defined using .asvc and generated using maven plugin. 

### Difference between avdl and avsc
* avsc is **JSON-formatted** schema of the object - the schema defines the fields of the object and the data-type of each field. **in JSON format**
* avdl is another way to express the schema of the object, in a cleaner way as compared to asvc (something like protobuf)

#### avdl format example 
```
record Employee {
  string name;
  boolean active = true;
  long salary;
}
```
#### Maven build for avdl
```
<build>
  <plugins>
    <plugin>
      <groupId>org.apache.avro</groupId>
      <artifactId>avro-maven-plugin</artifactId>
      <executions>
        <execution>
          <goals>
            <goal>idl-protocol</goal>
          </goals>
        </execution>
      </executions>
    </plugin>
  </plugins>
</build>
```
#### Maven build for avsc
```
<plugin>
                <groupId>org.apache.avro</groupId>
                <artifactId>avro-maven-plugin</artifactId>
                <version>1.11.1</version>
                <executions>
                    <execution>
                        <phase>generate-sources</phase>
                        <goals>
                            <goal>schema</goal>
                            <goal>protocol</goal>
                            <goal>idl-protocol</goal>
                        </goals>
                        <configuration>
                            <sourceDirectory>${project.basedir}/src/main/avro/</sourceDirectory>
                            <outputDirectory>${project.basedir}/src/main/java/</outputDirectory>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
```
#### avsc format example
```
{"namespace": "example.avro",
 "type": "record",
 "name": "User",
 "fields": [
     {"name": "name", "type": "string"},
     {"name": "favorite_number",  "type": ["int", "null"]},
     {"name": "favorite_color", "type": ["string", "null"]}
 ]
}
```

for every field in the object, 
get the name, get the value
for (PropertyDescriptor pd)
record.put(pd.getName(),pd.getReadMethod().invoke())

## Noice done
Now to send to Kafka

##
1. Refactor
2. By default Spring AutoConfiguration. How to overrid kafka bean