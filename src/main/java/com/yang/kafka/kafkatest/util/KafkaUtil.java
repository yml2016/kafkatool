package com.yang.kafka.kafkatest.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.ClientUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Slf4j
public class KafkaUtil implements ApplicationContextAware {

    @Autowired
    KafkaFactory kafkaFactory;

    ApplicationContext applicationContext;

    public void sendMessage(String message) {
        sendMessage(null, message);
    }

    public void sendMessage(String topicParam, String message) {
        sendMessage(null, topicParam, message);
    }

    public void sendMessage(String kfkName, String topicParam, String message) {
        String topic = topicParam;
        if (StringUtils.isEmpty(topic)) {
            Properties producerProperty = (Properties) kafkaFactory.getProducerProperties().values().toArray()[0];
            topic = producerProperty.getProperty("topic");
        }
        try(KafkaProducer<String, String> kafkaProducer = kafkaFactory.createProducer(kfkName)){
            kafkaProducer.send(new ProducerRecord<>(topic, message),
                    (metadata, exception) -> {
                        if (exception != null)
                            log.debug("消息的分区是[{}],偏移量为[{}]...", metadata.partition(), metadata.offset());
                        else
                            exception.printStackTrace(); //打印异常信息；
                    });
        }
        //kafkaProducer.close();
        //Runtime.getRuntime().addShutdownHook(new Thread(() -> ClientUtils.closeQuietly(kafkaProducer,null,null)));
    }

    public void consumeMessage() {
        consumeMessage("");
    }

    public void consumeMessage(String kfkName) {
        consumeMessage(kfkName, null);
    }

    public void consumeMessage(List<String> topics) {
        consumeMessage(null, topics);
    }

    public void consumeMessage(String kfkName, List<String> topics) {
        Map<String, Consumable> conMap = applicationContext.getBeansOfType(Consumable.class);
        if (CollectionUtils.isEmpty(conMap)) {
            conMap.put(null, x -> log.warn("找不到Consumable接口的实现类。消费到的信息为{}", x));
        }
        consumeMessage(kfkName, topics, conMap.values().toArray(new Consumable[conMap.values().size()]));
    }

    public void consumeMessage(String kfkName, List<String> topics, Consumable... cons) {
        KafkaConsumer<String, String>  kafkaConsumer = kafkaFactory.createConsumer(kfkName, topics);
        while (true) {
            ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(200));
            for (ConsumerRecord<String, String> record : consumerRecords) {
                Arrays.stream(cons).forEach(con -> con.doConsume(record));
            }
            kafkaConsumer.commitAsync();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
