package org.lohika.kafka.serializers;

import org.lohika.kafka.BaseJacksonSerializer;
import org.lohika.kafka.resource.WithdrawStatusResource;

public class WithdrawStatusJacksonSerializer extends BaseJacksonSerializer<WithdrawStatusResource> {

    @Override
    public Class<WithdrawStatusResource> getClz() {
        return WithdrawStatusResource.class;
    }
}
