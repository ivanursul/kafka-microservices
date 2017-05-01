package org.lohika.kafka.config;

import org.lohika.kafka.properties.KafkaProducerProperties;
import org.lohika.kafka.resource.ChargeAccountResource;
import org.lohika.kafka.resource.WithdrawStatusResource;
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
    public ProducerFactory<String, ChargeAccountResource> producerFactory(KafkaProducerProperties properties) {
        Map<String, Object> props = producerConfigs(properties);
        props.put("value.serializer", "org.lohika.kafka.serializers.AccountChargeJacksonSerializer");

        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public ProducerFactory<String, WithdrawStatusResource> withdrawStatusProducerFactory(KafkaProducerProperties properties) {
        Map<String, Object> props = producerConfigs(properties);
        props.put("value.serializer", "org.lohika.kafka.serializers.WithdrawStatusJacksonSerializer");

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

        return props;
    }

    @Bean(name = "kafkaTemplate")
    public KafkaTemplate<String, ChargeAccountResource> kafkaTemplate(KafkaProducerProperties properties) {
        return new KafkaTemplate<>(producerFactory(properties));
    }

/*
    @Bean(name = "withdrawStatusKafkaTemplate")
    public KafkaTemplate<String, WithdrawStatusResource> withdrawStatusKafkaTemplate(KafkaProducerProperties properties) {
        return new KafkaTemplate<>(withdrawStatusProducerFactory(properties));
    }
*/

}
