package com.kloia.service;

import com.kloia.configuration.CustomContext;
import com.kloia.configuration.RequestScopedAttributes;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class Auxiliary2Service {

    @SneakyThrows
    public boolean auxiliaryActions() {
        long threadId = Thread.currentThread().getId();

        Thread.sleep(1000L);

        // RequestScopedAttributes requestScopedAttributesFromContext = ContextUtils.getRequestContext();
        RequestScopedAttributes requestScopedAttributesFromContext = CustomContext.get();

        log.info("2- ThreadId = " + threadId + ", From Context:  ---- UserId: " + requestScopedAttributesFromContext.getUserId());
        log.info("2- ThreadId = " + threadId + ", From Context:  ---- StudentId: " + requestScopedAttributesFromContext.getStudentId());

        return true;
    }
}