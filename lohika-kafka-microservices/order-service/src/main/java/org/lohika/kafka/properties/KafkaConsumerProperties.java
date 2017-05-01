package org.lohika.kafka.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("kafka.consumer")
public class KafkaConsumerProperties {

    private String bootstrapServers;
    private String groupId;
    private String keyDeserializer;
    private String valueDeserializer;
}
