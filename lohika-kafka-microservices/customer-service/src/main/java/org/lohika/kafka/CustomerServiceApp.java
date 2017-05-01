package org.lohika.kafka;

import org.lohika.kafka.config.KafkaConsumerConfig;
import org.lohika.kafka.config.KafkaProducerConfig;
import org.lohika.kafka.model.Customer;
import org.lohika.kafka.properties.CustomerServiceProperties;
import org.lohika.kafka.properties.KafkaConsumerProperties;
import org.lohika.kafka.properties.KafkaProducerProperties;
import org.lohika.kafka.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;

import java.math.BigDecimal;

@SpringBootApplication
@EntityScan(basePackages = "org.lohika.kafka.model")
@EnableJpaRepositories(basePackages = "org.lohika.kafka.repository")
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
public class CustomerServiceApp implements ApplicationRunner {

    @Autowired
    private CustomerRepository customerRepository;

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApp.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Customer ivanursul = new Customer();
        ivanursul.setLogin("ivanursul");
        ivanursul.setAmount(new BigDecimal(100.0));

        customerRepository.save(ivanursul);
    }
}
