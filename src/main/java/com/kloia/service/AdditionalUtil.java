package com.kloia.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdditionalUtil {

    public boolean someMethod() throws InterruptedException {
        long threadId = Thread.currentThread().getId();
        Thread.sleep(1000L);
        log.info("ThreadId = " + threadId + ", AdditionalUtil");
        return true;
    }
}
