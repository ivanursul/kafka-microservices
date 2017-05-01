package org.lohika.kafka.serializers;

import org.lohika.kafka.BaseJacksonSerializer;
import org.lohika.kafka.resource.ChargeAccountResource;

public class AccountChargeJacksonSerializer extends BaseJacksonSerializer<ChargeAccountResource> {

    @Override
    public Class<ChargeAccountResource> getClz() {
        return ChargeAccountResource.class;
    }
}
