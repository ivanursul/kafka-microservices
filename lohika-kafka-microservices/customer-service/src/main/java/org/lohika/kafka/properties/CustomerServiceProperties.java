package org.lohika.kafka.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class CustomerServiceProperties {

    private KafkaProperties kafka;

    @Data
    public static class KafkaProperties {

        private KafkaProducerProperties producer;

        private KafkaConsumerProperties consumer;
    }
}
