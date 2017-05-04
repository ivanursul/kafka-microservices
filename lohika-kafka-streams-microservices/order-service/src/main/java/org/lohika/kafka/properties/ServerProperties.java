package org.lohika.kafka.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("kafka.producer")
public class ServerProperties {

    private String address;
    private int port;

}
