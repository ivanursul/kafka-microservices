package org.lohika.kafka.controller;

import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.apache.kafka.streams.state.StreamsMetadata;
import org.lohika.kafka.resource.OrderCreatedResource;
import org.lohika.kafka.resource.OrderProcessedResource;
import org.lohika.kafka.resource.OrderResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KStreamBuilderFactoryBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.util.UUID;

@RestController
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private KafkaTemplate<String, OrderCreatedResource> kafkaTemplate;

    @Autowired
    private KStreamBuilderFactoryBean kStreamBuilderFactoryBean;

    @RequestMapping(method = RequestMethod.POST)
    public String createOrder(@RequestBody OrderResource resource) {
        logger.info("Creating resource: {}", resource);

        String orderUid = UUID.randomUUID().toString();

        kafkaTemplate.send(
                "order.created.v2",
                orderUid,
                new OrderCreatedResource(
                        resource.getLogin(),
                        orderUid,
                        resource.getPrice(),
                        resource.getProducts()
                )
        );

        return orderUid;
    }

    @RequestMapping(value = "/{orderUid}", method = RequestMethod.GET)
    public OrderProcessedResource getOrderStatus(@PathVariable("orderUid") String orderUid) throws Exception {

        KafkaStreams kafkaStreams = getKafkaStreams();

        StreamsMetadata streamsMetadata = kafkaStreams.metadataForKey(
                "order-status", orderUid, new StringSerializer()
        );

        if (InetAddress.getLocalHost().getHostAddress().equals(streamsMetadata.host())) {
            ReadOnlyKeyValueStore<String, OrderProcessedResource> store
                    = kafkaStreams.store("order-status", QueryableStoreTypes.keyValueStore());

            return store.get(orderUid);
        } else {
            // http call to other instance
        }

        return null;
    }

    public KafkaStreams getKafkaStreams() throws IllegalAccessException, NoSuchFieldException {
        Field f = kStreamBuilderFactoryBean.getClass().getDeclaredField("kafkaStreams"); //NoSuchFieldException
        f.setAccessible(true);
        return (KafkaStreams) f.get(kStreamBuilderFactoryBean);
    }
}
