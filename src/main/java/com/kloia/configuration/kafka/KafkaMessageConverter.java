package com.kloia.configuration.kafka;

import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.errors.SerializationException;
import org.springframework.kafka.support.converter.MessagingMessageConverter;

import java.io.IOException;
import java.lang.reflect.Type;

public class KafkaMessageConverter extends MessagingMessageConverter {
    private final ObjectMapper objectMapper;

    public KafkaMessageConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @Override
    protected Object extractAndConvertValue(ConsumerRecord<?, ?> record, Type type) {
        Object value = record.value();
        if (value instanceof TreeNode) {
            try {
                return objectMapper.treeToValue((TreeNode) value, TypeFactory.rawClass(type));
            } catch (IOException e) {
                throw new SerializationException(e);
            }
        } else {
            return super.extractAndConvertValue(record, type);
        }
    }
}
