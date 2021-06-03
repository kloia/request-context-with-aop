/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kloia.configuration.kafka;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kloia.configuration.RequestScopedAttributes;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Map;
import java.util.function.Supplier;

/**
 *
 */
public class MessageWrapperSerializer<T> implements Serializer<T> {

    protected final ObjectMapper objectMapper;
    private final Supplier<RequestScopedAttributes> contextProvider;

    public MessageWrapperSerializer(Supplier<RequestScopedAttributes> contextProvider) {
        this(contextProvider, new ObjectMapper());
        this.objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public MessageWrapperSerializer(Supplier<RequestScopedAttributes> contextProvider, ObjectMapper objectMapper) {
        Assert.notNull(objectMapper, "'objectMapper' must not be null.");
        Assert.notNull(contextProvider, "'contextProvider' must not be null.");
        this.contextProvider = contextProvider;
        this.objectMapper = objectMapper;
    }

    public void configure(Map<String, ?> configs, boolean isKey) {
        // No-op
    }

    @Override
    public byte[] serialize(String topic, T data) {
        try {
            String event = null;
            if (data != null) {
                if (data instanceof MessageWrapper)
                    event = this.objectMapper.writeValueAsString(((MessageWrapper) data).getEventNode());
                else
                    event = this.objectMapper.writeValueAsString(data);
            }
            return objectMapper.writeValueAsBytes(new MessageWrapper(event, contextProvider.get()));
        } catch (IOException ex) {
            throw new SerializationException("Can't serialize data [" + data + "] for topic [" + topic + "]", ex);
        }
    }

    public void close() {
        // No-op
    }
}
