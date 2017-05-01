package org.lohika.kafka.serializers;

import org.lohika.kafka.BaseJacksonSerializer;
import org.lohika.kafka.resource.OrderProcessedResource;

public class OrderProcessedJacksonSerializer extends BaseJacksonSerializer<OrderProcessedResource> {

    @Override
    public Class<OrderProcessedResource> getClz() {
        return OrderProcessedResource.class;
    }
}
