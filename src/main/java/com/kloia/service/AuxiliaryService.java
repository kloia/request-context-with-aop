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
public class AuxiliaryService {

    @SneakyThrows
    public boolean auxiliaryActions() {
        long threadId = Thread.currentThread().getId();

        // RequestScopedAttributes requestScopedAttributesFromContext = ContextUtils.getRequestContext();
        RequestScopedAttributes requestScopedAttributesFromContext = CustomContext.get();

        Thread.sleep(1000L);

        log.info("ThreadId = " + threadId + ", From Context:  ---- UserId: " + requestScopedAttributesFromContext.getUserId());
        log.info("ThreadId = " + threadId + ", From Context:  ---- StudentId: " + requestScopedAttributesFromContext.getStudentId());

        return true;
    }
}
