package com.yang.kafka.kafkatest.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;
import java.util.Properties;


@Setter
@Getter
@ConfigurationProperties(prefix = "kafka")
public class MultiKafkaProperty {
    private Map<String, Properties> producer;
    private Map<String, Properties> consumer;
}
