package com.yang.kafka.kafkatest.annotation;

import com.yang.kafka.kafkatest.config.KafkaConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(KafkaConfiguration.class)
public @interface OnKafka {
    Class<?>[] value() default {};
}
