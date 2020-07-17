package com.yang.kafka.kafkatest.util;

@FunctionalInterface
public interface Consumable<T> {

    void doConsume(T param);

}
