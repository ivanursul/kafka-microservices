package org.lohika.kafka.config;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.processor.WallclockTimestampExtractor;
import org.lohika.kafka.DomainSerdes;
import org.lohika.kafka.domain.OrderBalance;
import org.lohika.kafka.properties.KafkaStreamsProperties;
import org.lohika.kafka.resource.BalanceStatus;
import org.lohika.kafka.resource.ChargeAccountResource;
import org.lohika.kafka.resource.OrderCreatedResource;
import org.lohika.kafka.resource.OrderProcessedResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class KafkaStreamsConfig {

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public StreamsConfig kStreamsConfigs(KafkaStreamsProperties properties) {
        Map<String, Object> props = new HashMap<>();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, properties.getGroupId());
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
        props.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.TIMESTAMP_EXTRACTOR_CLASS_CONFIG, WallclockTimestampExtractor.class.getName());
        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 3000);

        return new StreamsConfig(props);
    }

    @Bean
    public KStream<String, OrderProcessedResource> orderCreatedStreams(KStreamBuilder kStreamBuilder) {

        Serde<OrderCreatedResource> valSerde = DomainSerdes.from(OrderCreatedResource.class);

        GlobalKTable<String, ChargeAccountResource> userBalanceGlobalTable =
                kStreamBuilder.globalTable(
                        Serdes.String(),
                        DomainSerdes.from(ChargeAccountResource.class),
                        "users.balance.v2",
                        "users.balance"
                );

        KStream<String, OrderProcessedResource> stream = kStreamBuilder.stream(
                    Serdes.String(),
                    valSerde,
                    "order.created.v2"
                )
                .map((k, v) -> KeyValue.pair(v.getLogin(), v))
                .join(
                        userBalanceGlobalTable,
                        (k, v) -> k,
                        (v, gv) -> new OrderBalance(
                                v.getLogin(), v.getUid(), gv.getAmount(), v.getPrice()
                        )
                )
                .map((k, v) -> KeyValue.pair(k, new OrderProcessedResource(
                        v.getLogin(),
                        v.getUid(),
                        v.getOrderPrice(),
                        BalanceStatus.calculate(
                                v.getAmount(), v.getOrderPrice()
                        )
                )));

        stream.to(
                Serdes.String(),
                DomainSerdes.from(OrderProcessedResource.class),
                "order.processed.v2"
        );

        return stream;
    }
}
