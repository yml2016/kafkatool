package com.yang.kafka.kafkatest.util;

import com.yang.kafka.kafkatest.config.property.MultiKafkaProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

@Slf4j
public class KafkaFactory{

    @Autowired
    MultiKafkaProperty multiKafkaProperty;

    /*发送KAFKA的例子
    public void send(String message){
        Properties properties = multiProducerProperty.getPropertiesMap().get("shouyi");
//        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
//                "org.apache.kafka.common.serialization.StringSerializer");
//        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
//                "org.apache.kafka.common.serialization.StringSerializer");
//        ProducerConfig.addSerializerToConfig(properties, new StringSerializer(), new StringSerializer());
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);
        for (int i = 0; i < 10; i++) {
            kafkaProducer.send(new ProducerRecord<>(properties.get("topic").toString(), message + i));
        }
        kafkaProducer.close();
    }*/

    /*消费数据的例子
    public void consume() {
        Properties properties = getConsumerProperties().get("shouyi");
        KafkaConsumer<Object, Object> kafkaConsumer = new KafkaConsumer<>(properties);
        kafkaConsumer.subscribe(Collections.singletonList("yml-test"));
        while (true) {
            ConsumerRecords<Object, Object> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(200));
            for (ConsumerRecord<Object, Object> record : consumerRecords) {
                System.out.println(record.key() + "*************");
                System.out.println(record.value());
            }
        }

    }*/


    public KafkaConsumer<String, String> createConsumer() {
        return createConsumer(null);
    }

    public KafkaConsumer<String, String> createConsumer(String kfkName) {
        return createConsumer(kfkName, null);
    }

    public KafkaConsumer<String, String> createConsumer(String kfkName, List<String> topics) {
        return createConsumer(kfkName, topics, defaultConsumerProperties());
    }

    public KafkaConsumer<String, String> createConsumer(String kfkName, List<String> topics, Properties customProperties) {
        Properties consumerProperty;
        if (StringUtils.isEmpty(kfkName)) {
            consumerProperty = (Properties) getConsumerProperties().values().toArray()[0];
        } else {
            consumerProperty = getConsumerProperties().get(kfkName);
        }
        if (customProperties != null) {
            consumerProperty.putAll(customProperties);
        }
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(consumerProperty);
        String topicStr = consumerProperty.getProperty("topic");
        List<String> topicLst = new ArrayList<>();
        if (!StringUtils.isEmpty(topicStr)) {
            String[] topicArr = consumerProperty.getProperty("topic").split(",", -1);
            topicLst.addAll(Arrays.asList(topicArr));
        }
        if (!CollectionUtils.isEmpty(topics)) {
            topicLst.addAll(topics);
        }
        kafkaConsumer.subscribe(topicLst);
        return kafkaConsumer;
    }

    public KafkaProducer<String, String> createProducer() {
        return createProducer(null);
    }

    public KafkaProducer<String, String> createProducer(String kfkName) {
        return createProducer(kfkName, defaultProducerProperties());
    }

    public KafkaProducer<String, String> createProducer(String kfkName, Properties customProperties) {
        Properties producerProperty;
        if (StringUtils.isEmpty(kfkName)) {
            producerProperty = (Properties) getProducerProperties().values().toArray()[0];
        } else {
            producerProperty = getProducerProperties().get(kfkName);
        }
        if (customProperties != null) {
            producerProperty.putAll(customProperties);
        }
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(producerProperty);
        return kafkaProducer;
    }


    public Map<String, Properties> getProducerProperties() {
        //MultiKafkaProperty multiKafkaProperty = applicationContext.getBean(MultiKafkaProperty.class);
        return multiKafkaProperty.getProducer();

    }

    public Map<String, Properties> getConsumerProperties() {
        //MultiKafkaProperty multiKafkaProperty = applicationContext.getBean(MultiKafkaProperty.class);
        return multiKafkaProperty.getConsumer();

    }

    public Properties parseProperties(Map<String, String> map) {
        Properties properties = new Properties();
        properties.putAll(map);
        return properties;
    }


    public Properties defaultProducerProperties() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        return properties;
    }

    public Properties defaultConsumerProperties() {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        return properties;
    }
}
