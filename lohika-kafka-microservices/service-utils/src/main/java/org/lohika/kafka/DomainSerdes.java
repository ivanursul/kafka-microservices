package org.lohika.kafka;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

public class DomainSerdes {

    public static <E> Serde<E> from(Class<E> clz) {
        return Serdes.serdeFrom(
                new DefaultJacksonProvider<>(clz),
                new DefaultJacksonProvider<>(clz)
        );
    }

}
