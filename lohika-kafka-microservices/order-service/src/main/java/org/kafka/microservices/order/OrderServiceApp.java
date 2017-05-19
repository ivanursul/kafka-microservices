package org.kafka.microservices.order;

import org.kafka.microservices.order.properties.KafkaConsumerProperties;
import org.kafka.microservices.order.properties.KafkaProducerProperties;
import org.kafka.microservices.order.repository.OrderRepository;
import org.kafka.microservices.order.config.KafkaConsumerConfig;
import org.kafka.microservices.order.config.KafkaProducerConfig;
import org.kafka.microservices.order.properties.OrderServiceProperties;
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
