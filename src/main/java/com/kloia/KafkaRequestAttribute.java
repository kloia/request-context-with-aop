package com.kloia;

import com.kloia.configuration.CustomContext;
import com.kloia.configuration.RequestScopedAttributes;
import com.kloia.configuration.kafka.KafkaProducer;
import com.kloia.model.Student;
import org.springframework.stereotype.Component;


@Component
public class KafkaRequestAttribute {

    private KafkaProducer kafkaProducer;

    public void send(Student student) {
        RequestScopedAttributes requestScopedAttributes = CustomContext.get();
        kafkaProducer = new KafkaProducer(requestScopedAttributes);
        kafkaProducer.produce("kafka-topic", student);
    }
}
