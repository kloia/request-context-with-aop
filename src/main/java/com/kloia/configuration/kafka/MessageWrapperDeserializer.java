/*
 * Copyright 2015-2016 the original author or authors.
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

import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.kloia.configuration.RequestScopedAttributes;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.core.ResolvableType;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Consumer;


@Slf4j
public class MessageWrapperDeserializer<T> implements Deserializer<T> {
    protected final ObjectMapper objectMapper;
    private final Class<T> targetType;
    private final Consumer<RequestScopedAttributes> contextApplier;
    private ObjectReader wrapperReader;
    private ObjectReader reader;

    protected MessageWrapperDeserializer(Consumer<RequestScopedAttributes> contextApplier) {
        this(contextApplier, (Class<T>) null);
    }

    protected MessageWrapperDeserializer(Consumer<RequestScopedAttributes> contextApplier, ObjectMapper objectMapper) {
        this(contextApplier, null, objectMapper);
    }

    public MessageWrapperDeserializer(Consumer<RequestScopedAttributes> contextApplier, Class<T> targetType) {
        this(contextApplier, targetType, new ObjectMapper());
        this.objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @SuppressWarnings("unchecked")
    public MessageWrapperDeserializer(Consumer<RequestScopedAttributes> contextApplier, Class<T> targetType, ObjectMapper objectMapper) {
        this.contextApplier = contextApplier;
        Assert.notNull(objectMapper, "'objectMapper' must not be null.");
        this.objectMapper = objectMapper;
        if (targetType == null) {
            targetType = (Class<T>) ResolvableType.forClass(getClass()).getSuperType().resolveGeneric(0);
        }
        Assert.notNull(targetType, "'targetType' cannot be resolved.");
        this.targetType = targetType;
        this.reader = this.objectMapper.readerFor(targetType);
    }

    public void configure(Map<String, ?> configs, boolean isKey) {
        // No-op
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        if (this.wrapperReader == null) {
            this.wrapperReader = this.objectMapper.readerFor(MessageWrapper.class);
        }
        try {
            MessageWrapper result;
            if (data != null) {
                result = this.wrapperReader.readValue(data);
            } else {
                return null;
            }

            RequestScopedAttributes requestScopedAttributes = result.getRequestScopedAttributes();
            contextApplier.accept(requestScopedAttributes);

            if (result.getEventNode() == null) {
                return null;
            } else {
                if (targetType.isAssignableFrom(TreeNode.class)) {
                    return (T) result.getEventNode();
                } else {
                    return this.reader.readValue(result.getEventNode());
                }
            }

        } catch (IOException e) {
            log.error("Can't deserialize data [" + Arrays.toString(data) +
                    "] from topic [" + topic + "]", e);
            return null;
        }
    }

    public void close() {
        // No-op
    }
}
