package org.kafka.microservices.order.serializers;

import org.kafka.microservices.order.resource.OrderCreatedResource;
import org.lohika.kafka.BaseJacksonSerializer;

public class OrderCreatedJacksonSerializer extends BaseJacksonSerializer<OrderCreatedResource> {

    @Override
    public Class<OrderCreatedResource> getClz() {
        return OrderCreatedResource.class;
    }
}
