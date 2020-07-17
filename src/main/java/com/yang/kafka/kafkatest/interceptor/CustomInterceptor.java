package com.yang.kafka.kafkatest.interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * 需要配置到property中
 **/
public class CustomInterceptor implements ProducerInterceptor {

    int succ = 0;
    int error = 0;

    @Override
    public ProducerRecord onSend(ProducerRecord record) {
        String value = record.value().toString() + System.currentTimeMillis();
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(record.topic(), value);
        return producerRecord;
    }

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
        if (metadata != null && exception == null)
            succ++;
        else
            error++;
    }

    @Override
    public void close() {
        System.out.println("成功发送" + succ);
        System.out.println("失败发送" + error);
    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
