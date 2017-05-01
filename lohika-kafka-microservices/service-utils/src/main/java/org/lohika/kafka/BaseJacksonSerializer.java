package org.lohika.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;


public abstract class BaseJacksonSerializer<POJO> implements Closeable, AutoCloseable, Serializer<POJO>, Deserializer<POJO> {
    private ObjectMapper mapper;

    public BaseJacksonSerializer() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public void configure(Map<String, ?> map, boolean b) {
        if(mapper == null) {
            mapper = new ObjectMapper();
        }
    }

    @Override
    public byte[] serialize(String s, POJO orderResource) {
        try {
            return mapper.writeValueAsBytes(orderResource);
        } catch(JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public POJO deserialize(String topic, byte[] bytes) {
        try {
            byte[] bts = bytes != null ? bytes : new String("{}").getBytes();
            return mapper.readValue(bts, getClz());
        } catch(IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public abstract Class<POJO> getClz();

    @Override
    public void close() {
        mapper = null;
    }
}
