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

        log.info("ThreadId = , From Context:  " + threadId + "----" + requestScopedAttributesFromContext.getUserId());
        log.info("ThreadId = , From Context:  " + threadId + "----" + requestScopedAttributesFromContext.getStudentId());
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        log.info("ThreadId = , From Bean: " + threadId + "----" + requestScopedAttributes.getUserId());
        log.info("ThreadId = , From Bean: " + threadId + "----" + requestScopedAttributes.getStudentId());
    }
}
