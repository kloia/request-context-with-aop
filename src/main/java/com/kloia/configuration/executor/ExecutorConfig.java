package com.kloia.configuration.executor;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.concurrent.Executor;

@Configuration
public class ExecutorConfig extends AsyncConfigurerSupport {

    @Override
    @Bean(name = "myExecutor")
    public Executor getAsyncExecutor() {
        ContextAwarePoolExecutor contextAwarePoolExecutor = new ContextAwarePoolExecutor();
        contextAwarePoolExecutor.setCorePoolSize(10);
        return contextAwarePoolExecutor;
    }

    public static class ContextAwarePoolExecutor extends ThreadPoolTaskExecutor {
        @Override
        public void execute(Runnable task) {
            super.execute(new ContextAwareRunnable(task, RequestContextHolder.getRequestAttributes()));
        }
    }

    @RequiredArgsConstructor
    public static class ContextAwareRunnable implements Runnable {
        private final Runnable task;
        private final RequestAttributes context;

        @Override
        public void run() {
            if (context != null) {
                RequestContextHolder.setRequestAttributes(context);
            }

            try {
                task.run();
            } finally {
                RequestContextHolder.resetRequestAttributes();
            }
        }
    }

}

