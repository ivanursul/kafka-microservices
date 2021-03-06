package org.lohika.kafka.config;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.processor.WallclockTimestampExtractor;
import org.lohika.kafka.DomainSerdes;
import org.lohika.kafka.properties.KafkaStreamsProperties;
import org.lohika.kafka.resource.BalanceStatus;
import org.lohika.kafka.resource.ChargeAccountResource;
import org.lohika.kafka.resource.OrderProcessedResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.streams.kstream.internals.AbstractStream.keyValueStore;


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
    public KTable<String, ChargeAccountResource> chargeStream(KStreamBuilder kStreamBuilder) {

        Serde<ChargeAccountResource> valSerde = DomainSerdes.from(ChargeAccountResource.class);

        KTable<String, ChargeAccountResource> ktable = kStreamBuilder.stream(
                        Serdes.String(),
                        valSerde,
                        "login.account.charged.v2"
                )
                .groupByKey()
                .reduce(
                        (ch1, ch2) -> new ChargeAccountResource(ch1.getAmount().add(ch2.getAmount())),
                        keyValueStore(Serdes.String(), valSerde, "users-balance")
                );

        ktable.to(Serdes.String(), valSerde, "users.balance.v2");

        return ktable;
    }

    @Bean
    public KStream<String, ChargeAccountResource> withDrawStream(KStreamBuilder kStreamBuilder) {

        Serde<OrderProcessedResource> valSerde = DomainSerdes.from(OrderProcessedResource.class);

        KStream<String, ChargeAccountResource> stream = kStreamBuilder.stream(
                    Serdes.String(),
                    valSerde,
                    "order.processed.v2"
                )
                .filter((k, v) -> BalanceStatus.AVAILABLE.equals(v.getBalanceStatus()))
                .map((k, v) -> KeyValue.pair(v.getLogin(), new ChargeAccountResource(
                        v.getPrice().multiply(new BigDecimal(-1))
                )));

        stream.to(
                Serdes.String(),
                DomainSerdes.from(ChargeAccountResource.class),
                "login.account.charged.v2"
        );

        return stream;
    }

}
