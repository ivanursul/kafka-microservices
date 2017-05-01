package org.lohika.kafka;

import org.lohika.kafka.config.KafkaConsumerConfig;
import org.lohika.kafka.config.KafkaProducerConfig;
import org.lohika.kafka.properties.KafkaConsumerProperties;
import org.lohika.kafka.properties.KafkaProducerProperties;
import org.lohika.kafka.properties.OrderServiceProperties;
import org.lohika.kafka.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@Import({
		KafkaProducerConfig.class,
		KafkaConsumerConfig.class
})
@EnableConfigurationProperties({
		OrderServiceProperties.class,
		KafkaProducerProperties.class,
		KafkaConsumerProperties.class
})
@EnableKafka
public class OrderServiceApp implements ApplicationRunner {

	@Autowired
	private OrderRepository orderRepository;

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApp.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		orderRepository.deleteAll();
	}
}
