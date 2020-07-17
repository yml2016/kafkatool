package com.yang.kafka.kafkatest.config;


import com.yang.kafka.kafkatest.config.property.MultiKafkaProperty;
import com.yang.kafka.kafkatest.util.KafkaFactory;
import com.yang.kafka.kafkatest.util.KafkaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Slf4j
@Configuration
@EnableConfigurationProperties(MultiKafkaProperty.class)
public class KafkaConfiguration implements EnvironmentAware {

    private Environment environment;

    //private String PRODUCER_PERFIX = "kafka.producer";
    //private String CONSUMER_PERFIX = "kafka.consumer";

    /*@Bean
    public MultiKafkaProperty multiKafkaProperty() {
        Binder binder = Binder.get(environment);
        MultiKafkaProperty multiKafkaProperty = new MultiKafkaProperty();
        try {
            multiKafkaProperty = binder
                    .bind("kafka", Bindable.of(MultiKafkaProperty.class))
                    .get();

        } catch (Exception e) {
            log.info("not kafka config.");
        }
        return multiKafkaProperty;
    }
*/

    /*@Lazy
    @Bean
    public MultiProducerProperty multiProducerProperty() {
        Binder binder = Binder.get(environment);
        MultiProducerProperty multiProducerProperty = new MultiProducerProperty();
        try {
            Map<String, Properties> propertiesMap = binder
                    .bind(PRODUCER_PERFIX, Bindable.mapOf(String.class, Properties.class))
                    .get();
            multiProducerProperty.putAll(propertiesMap);
        } catch (Exception e) {
            log.info("not producer");
        }
        return multiProducerProperty;
    }

    @Lazy
    @Bean
    public MultiConsumerProperty multiConsumerProperty() {
        Binder binder = Binder.get(environment);
        MultiConsumerProperty multiConsumerProperty = new MultiConsumerProperty();
        try{
            Map<String, Properties> propertiesMap = binder
                    .bind(CONSUMER_PERFIX, Bindable.mapOf(String.class, Properties.class))
                    .get();
            multiConsumerProperty.putAll(propertiesMap);
        }
        catch (Exception e){
            log.info("not consumer");
        }
        return multiConsumerProperty;
    }*/

    @Bean
    @ConditionalOnMissingBean
    public KafkaFactory kafkaFactory(){
        return new KafkaFactory();
    }

    @Bean
    @ConditionalOnMissingBean
    public KafkaUtil kafkaUtil(){
        return new KafkaUtil();
    }

    @Override
    public void setEnvironment(org.springframework.core.env.Environment environment) {
        this.environment = environment;
    }
}
