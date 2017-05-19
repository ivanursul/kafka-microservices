package org.kafka.microservices.order.converter;

import org.kafka.microservices.order.domain.Order;
import org.kafka.microservices.order.domain.OrderProduct;
import org.kafka.microservices.order.domain.OrderStatus;
import org.kafka.microservices.order.resource.OrderCreatedResource;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class OrderCreatedResourceConverter {


    public Order convert(OrderCreatedResource resource) {
        List<OrderProduct> orderProducts = resource.getProducts().stream()
                .map(p -> new OrderProduct(p.getName(), p.getCount()))
                .collect(toList());

        Order order = new Order();
        order.setStatus(OrderStatus.CREATED);
        order.setOrderUid(resource.getUid());
        order.setPrice(resource.getPrice());
        order.setLogin(resource.getLogin());
        order.setProducts(orderProducts);

        return order;
    }

}
