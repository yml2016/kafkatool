package com.yang.kafka.kafkatest;

import com.yang.kafka.kafkatest.annotation.OnKafka;
import com.yang.kafka.kafkatest.config.property.MultiKafkaProperty;
import com.yang.kafka.kafkatest.util.KafkaFactory;
import com.yang.kafka.kafkatest.util.KafkaUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

@OnKafka
@SpringBootTest
class BindertestApplicationTests {


   /* @Test
    void contextLoads() {
        Binder binder = Binder.get(myEnvronment.getEnvironment());
        Properties properties = binder.bind("kafka.producer.shouyi", Properties.class).get();
        Map<String, Properties> propertiesMap = binder
                .bind("kafka.producer", Bindable.mapOf(String.class, Properties.class))
                .get();
        MultiProducerProperty.putAll(propertiesMap);
        System.out.println(MultiProducerProperty.getProducerProperties().get("shouyi"));

    }*/

    /*@Test
    public void testProperty(){
        multiProducerProperty.getProducerProperties().get("shouyi");
        System.out.printf("Producer参数信息为{}"+multiProducerProperty.getProducerProperties().get("shouyi"));
        System.out.printf("Consumer参数信息为{}"+multiConsumerProperty.getProducerProperties().get("shouyi"));
    }*/

    @Autowired
    KafkaFactory kafkaFactory;
    @Autowired
    KafkaUtil kafkaUtil;

    @Autowired
    MultiKafkaProperty multiKafkaProperty;
    @Test
    public void testProducer() throws ExecutionException, InterruptedException {

       kafkaUtil.consumeMessage("shouyi", Arrays.asList("yml-test"));
        //multiKafkaProperty.getConsumer();
        //System.out.println(multiKafkaProperty);
    }


}
