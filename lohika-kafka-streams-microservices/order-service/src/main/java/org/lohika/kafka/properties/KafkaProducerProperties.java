package org.lohika.kafka.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("kafka.producer")
public class KafkaProducerProperties {

    private String bootstrapServers;
    private String acks;
    private Integer retries;
    private Integer batchSize;
    private Integer lingerMs;
    private Integer bufferMemory;

    private String keySerializer;
    private String valueSerializer;
}
