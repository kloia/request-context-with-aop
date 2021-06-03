package com.kloia.kafka;

import com.kloia.model.Student;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaSenderService {

    private static final String TOPIC = "students";

    private final KafkaTemplate<String, Student> kafkaTemplate;
    private final KafkaProducerCallbackListener<String, Student> kafkaProducerCallbackListener;

    public void sendMessage(Student message) {
        try {
            ListenableFuture<SendResult<String, Student>> future = kafkaTemplate.send(TOPIC, message);
            future.addCallback(kafkaProducerCallbackListener);
        } catch (SerializationException e) {
            log.error("Could not send seller update message: " + message, e);
        }
    }
}
