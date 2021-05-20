package com.kloia.service;

import com.kloia.configuration.RequestScopedAttributes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuxiliaryService {

    private final RequestScopedAttributes requestScopedAttributes;

    public void auxiliaryActions() {
        long threadId = Thread.currentThread().getId();

        RequestScopedAttributes requestScopedAttributesFromContext = ContextUtils.getRequestContext();

        log.info("ThreadId = " + threadId + ", From Context:  ---- UserId: " + requestScopedAttributesFromContext.getUserId());
        log.info("ThreadId = " + threadId + ", From Context:  ---- StudentId: " + requestScopedAttributesFromContext.getStudentId());

        log.info("ThreadId = " + threadId + ", From Bean:  ---- UserId: " + requestScopedAttributes.getUserId());
        log.info("ThreadId = " + threadId + ", From Bean:  ---- StudentId: " + requestScopedAttributes.getStudentId());
    }
}
