package com.kloia.kafka;

import com.kloia.configuration.CustomContext;
import com.kloia.configuration.RequestScopedAttributes;
import com.kloia.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaListenerService {

    private static final String TOPIC = "students";

    @KafkaListener(topics = TOPIC, containerFactory = "kafkaListenerContainerFactory")
    public void receiveMessage(Student studentMessage) {
        long threadId = Thread.currentThread().getId();
        log.info("Received Student: {}", studentMessage);
        RequestScopedAttributes requestScopedAttributes = CustomContext.get();
        log.info("KafkaListenerThreadId = " + threadId + ", From Context:  ---- UserId: " + requestScopedAttributes.getUserId());
        log.info("KafkaListenerThreadId = " + threadId + ", From Context:  ---- StudentId: " + requestScopedAttributes.getStudentId());

    }

}
