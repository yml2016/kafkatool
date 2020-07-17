package com.yang.kafka.kafkatest;

import com.yang.kafka.kafkatest.util.Consumable;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Component;

@Component
public class Comsumer1 implements Consumable<ConsumerRecord<String, String>> {

    @Override
    public void doConsume(ConsumerRecord<String, String> record) {
        System.out.println("Comsumer1收到数据为======"+record.value());
    }
}
