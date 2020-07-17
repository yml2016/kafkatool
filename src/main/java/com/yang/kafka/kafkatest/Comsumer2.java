package com.yang.kafka.kafkatest;

import com.yang.kafka.kafkatest.util.Consumable;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Component;

@Component
public class Comsumer2 implements Consumable<ConsumerRecord<String, String>> {

    @Override
    public void doConsume(ConsumerRecord<String, String> record) {
        System.out.println("Comsumer2收到数据为======"+record.value());
    }
}
