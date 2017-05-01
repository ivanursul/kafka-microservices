package org.lohika.kafka;

import org.lohika.kafka.config.KafkaStreamsConfig;
import org.lohika.kafka.config.KafkaProducerConfig;
import org.lohika.kafka.properties.KafkaProducerProperties;
import org.lohika.kafka.properties.KafkaStreamsProperties;
import org.lohika.kafka.properties.OrderServiceProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@SpringBootApplication
@Import({
		KafkaProducerConfig.class,
		KafkaStreamsConfig.class
})
@EnableConfigurationProperties({
		OrderServiceProperties.class,
		KafkaProducerProperties.class,
		KafkaStreamsProperties.class
})
@EnableKafka
@EnableKafkaStreams
public class OrderServiceApp {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApp.class, args);
	}

}
