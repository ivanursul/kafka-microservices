package org.kafka.microservices.order.serializers;

import org.lohika.kafka.BaseJacksonSerializer;
import org.kafka.microservices.order.resource.OrderProcessedResource;

public class OrderProcessedJacksonSerializer extends BaseJacksonSerializer<OrderProcessedResource> {

    @Override
    public Class<OrderProcessedResource> getClz() {
        return OrderProcessedResource.class;
    }
}
