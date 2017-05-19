package org.kafka.microservices.customer.repository;

import org.kafka.microservices.customer.model.Customer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    @Modifying
    @Query("update customers c set c.amount = c.amount + ?2 where c.login = ?1")
    void updateBalance(String login, BigDecimal amount);

    @Modifying
    @Query("update customers c set c.amount = c.amount - ?2 where c.login = ?1 and c.amount >= ?2")
    int withdrawAmount(String login, BigDecimal amount);

    Optional<Customer> findByLogin(String login);

}
