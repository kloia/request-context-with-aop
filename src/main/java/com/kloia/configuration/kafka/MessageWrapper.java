package com.kloia.configuration.kafka;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.JsonNode;
import com.kloia.configuration.RequestScopedAttributes;
import lombok.Data;

import java.util.Map;

@Data

public class MessageWrapper {
    @JsonRawValue
    private String event;
    @JsonIgnore
    private JsonNode eventNode;
    private RequestScopedAttributes requestScopedAttributes;

    public MessageWrapper() {
    }

    public MessageWrapper(String event, RequestScopedAttributes requestScopedAttributes) {
        this.event = event;
        this.requestScopedAttributes = requestScopedAttributes;
    }

    private void setEvent(JsonNode eventNode) {
        this.eventNode = eventNode;
    }
}
