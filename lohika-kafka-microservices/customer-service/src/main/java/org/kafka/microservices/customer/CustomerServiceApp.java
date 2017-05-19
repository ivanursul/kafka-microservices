package org.kafka.microservices.customer;

import org.kafka.microservices.customer.config.KafkaConsumerConfig;
import org.kafka.microservices.customer.config.KafkaProducerConfig;
import org.kafka.microservices.customer.properties.CustomerServiceProperties;
import org.kafka.microservices.customer.properties.KafkaConsumerProperties;
import org.kafka.microservices.customer.properties.KafkaProducerProperties;
import org.kafka.microservices.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EntityScan(basePackages = "org.kafka.microservices.customer.model")
@EnableJpaRepositories(basePackages = "org.kafka.microservices.customer.repository")
@Import({
        KafkaProducerConfig.class,
        KafkaConsumerConfig.class
})
@EnableConfigurationProperties({
        CustomerServiceProperties.class,
        KafkaProducerProperties.class,
        KafkaConsumerProperties.class
})
@EnableKafka
public class CustomerServiceApp {

    @Autowired
    private CustomerRepository customerRepository;

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApp.class, args);
    }

}
