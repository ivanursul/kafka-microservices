package org.kafka.microservices.customer.config;

import org.kafka.microservices.customer.properties.KafkaConsumerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class KafkaConsumerConfig {

    @Bean
    ConcurrentKafkaListenerContainerFactory<Integer, String> kafkaListenerContainerFactory(
            KafkaConsumerProperties properties
    ) {
        ConcurrentKafkaListenerContainerFactory<Integer, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory(properties));
        factory.setMessageConverter(new StringJsonMessageConverter());
        return factory;
    }

    @Bean
    public ConsumerFactory<Integer, String> consumerFactory(KafkaConsumerProperties properties) {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(properties));
    }

    @Bean
    public Map<String, Object> consumerConfigs(KafkaConsumerProperties properties) {
        Map<String, Object> props = new HashMap<>();

        props.put("bootstrap.servers", properties.getBootstrapServers());
        props.put("group.id", properties.getGroupId());
        props.put("key.deserializer", properties.getKeyDeserializer());
        props.put("value.deserializer", properties.getValueDeserializer());

        return props;
    }


}
