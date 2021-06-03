package com.kloia.configuration.kafka;

import com.kloia.configuration.CustomContext;
import com.kloia.configuration.RequestScopedAttributes;
import com.kloia.model.Student;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Configuration
public class KafkaProducerConfiguration {

    @Value("${kafka.bootstrapServers.ip:localhost:9092}")
    private String bootstrapServers;

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return props;
    }

    @Bean
    public ProducerFactory<String, Student> producerFactory(Supplier<RequestScopedAttributes> contextProvider) {
        return new DefaultKafkaProducerFactory<>(producerConfigs(), new StringSerializer(), new MessageWrapperSerializer<>(contextProvider));
    }

    @Bean
    public KafkaTemplate<String, Student> kafkaTemplate(Supplier<RequestScopedAttributes> contextProvider) {
        return new KafkaTemplate<>(producerFactory(contextProvider));
    }

    @Bean
    public Supplier<RequestScopedAttributes> contextProvider() {
        return CustomContext::get;
    }
}
