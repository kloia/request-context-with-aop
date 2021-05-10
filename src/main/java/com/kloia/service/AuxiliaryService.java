package com.kloia.service;

import com.kloia.aspect.component.RequestScopedAttributes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuxiliaryService {

    private final RequestScopedAttributes requestScopedAttributes;

    public void auxiliaryActions() {
        log.info(requestScopedAttributes.getUserId());
        log.info(requestScopedAttributes.getStudentId().toString());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info(requestScopedAttributes.getUserId());
        log.info(requestScopedAttributes.getStudentId().toString());
    }
}
