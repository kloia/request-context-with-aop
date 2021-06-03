package com.kloia.service;

import com.kloia.configuration.CustomContext;
import com.kloia.configuration.RequestScopedAttributes;
import com.kloia.kafka.KafkaSenderService;
import com.kloia.model.Student;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class Auxiliary2Service {

    private final KafkaSenderService kafkaSenderService;

    @SneakyThrows
    public boolean auxiliaryActions() {
        long threadId = Thread.currentThread().getId();

        kafkaSenderService.sendMessage(Student.builder().build());

        // RequestScopedAttributes requestScopedAttributesFromContext = ContextUtils.getRequestContext();
        RequestScopedAttributes requestScopedAttributesFromContext = CustomContext.get();
        Thread.sleep(1000L);

        log.info("2- ThreadId = " + threadId + ", From Context:  ---- UserId: " + requestScopedAttributesFromContext.getUserId());
        log.info("2- ThreadId = " + threadId + ", From Context:  ---- StudentId: " + requestScopedAttributesFromContext.getStudentId());

        return true;
    }
}
