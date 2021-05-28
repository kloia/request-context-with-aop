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

    private final AdditionalUtil additionalUtil;

    @SneakyThrows
    public boolean auxiliaryActions() {
        long threadId = Thread.currentThread().getId();

        // RequestScopedAttributes requestScopedAttributesFromContext = ContextUtils.getRequestContext();
        RequestScopedAttributes requestScopedAttributesFromContext = CustomContext.get();

        Thread.sleep(1000L);

        boolean someMethod = additionalUtil.someMethod();
        int hashCode = additionalUtil.hashCode();
        log.info("ThreadId = " + threadId + " HashCode: " + additionalUtil + ", From Context:  ---- UserId: " + requestScopedAttributesFromContext.getUserId());
        log.info("ThreadId = " + threadId + " HashCode: " + additionalUtil + ", From Context:  ---- StudentId: " + requestScopedAttributesFromContext.getStudentId());

        return true;
    }
}
