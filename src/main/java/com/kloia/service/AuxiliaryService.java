package com.kloia.service;

import com.kloia.configuration.RequestScopedAttributes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuxiliaryService {

    public boolean auxiliaryActions() {
        long threadId = Thread.currentThread().getId();

        RequestScopedAttributes requestScopedAttributesFromContext = ContextUtils.getRequestContext();

        log.info("ThreadId = , From Context:  " + threadId + "----" + requestScopedAttributesFromContext.getUserId());
        log.info("ThreadId = , From Context:  " + threadId + "----" + requestScopedAttributesFromContext.getStudentId());

        return true;
    }
}
