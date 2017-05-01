package org.lohika.kafka.config;

import org.lohika.kafka.properties.KafkaProducerProperties;
import org.lohika.kafka.resource.OrderCreatedResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class KafkaProducerConfig {

    @Bean
    public ProducerFactory<String, OrderCreatedResource> producerFactory(KafkaProducerProperties properties) {
        Map<String, Object> props = producerConfigs(properties);
        props.put("value.serializer", "org.lohika.kafka.serializers.OrderCreatedJacksonSerializer");

        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public Map<String, Object> producerConfigs(KafkaProducerProperties properties) {
        Map<String, Object> props = new HashMap<>();

        props.put("bootstrap.servers", properties.getBootstrapServers());

        props.put("acks", properties.getAcks());
        props.put("retries", properties.getRetries());
        props.put("batch.size", properties.getBatchSize());
        props.put("linger.ms", properties.getLingerMs());
        props.put("buffer.memory", properties.getBufferMemory());

        props.put("key.serializer", properties.getKeySerializer());
        props.put("value.serializer", properties.getValueSerializer());

        return props;
    }

    @Bean(name = "kafkaTemplate")
    public KafkaTemplate<String, OrderCreatedResource> kafkaTemplate(KafkaProducerProperties properties) {
        return new KafkaTemplate<>(producerFactory(properties));
    }

}
