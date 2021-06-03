package com.kloia.configuration.kafka;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.kloia.configuration.RequestScopedAttributes;
import com.kloia.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.BytesSerializer;
import org.apache.kafka.common.utils.Bytes;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class KafkaProducer {

    private Map<String, Object> kafkaProperties;
    private ProducerFactory<Bytes, Object> producerFactory;
    private KafkaTemplate<Bytes, Object> kafkaTemplate;

    public KafkaProducer(RequestScopedAttributes requestScopedAttributes) {
        this.kafkaProperties = new HashMap<>();
        this.kafkaProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        this.kafkaProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, BytesSerializer.class);
        this.kafkaProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        this.producerFactory = new DefaultKafkaProducerFactory<>(this.kafkaProperties,
                new BytesSerializer(), new MessageWrapperSerializer<>(requestScopedAttributes));
        this.kafkaTemplate = new KafkaTemplate<>(producerFactory);
    }

    public void produce(String topic, Student student) {
        if (StringUtils.isBlank(topic)) {
            log.info("Topic: {} can't be blank!", topic);
            return;
        }

        ListenableFuture<SendResult<Bytes, Object>> future;

        future = kafkaTemplate.send(topic, student);

        future.addCallback(new KafkaProducerCallbackListener());
    }
}
