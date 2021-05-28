package com.kloia.configuration.executor;


import com.kloia.configuration.CustomContext;
import com.kloia.configuration.RequestScopedAttributes;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class ExecutorConfig extends AsyncConfigurerSupport {

    @Override
    @Bean(name = "myExecutor")
    public Executor getAsyncExecutor() {
        ContextAwarePoolExecutor contextAwarePoolExecutor = new ContextAwarePoolExecutor();
        contextAwarePoolExecutor.setCorePoolSize(200);
        return contextAwarePoolExecutor;
    }

    public static class ContextAwarePoolExecutor extends ThreadPoolTaskExecutor {
        @Override
        public void execute(Runnable task) {
            RequestScopedAttributes requestContext = CustomContext.get();
            super.execute(new ContextAwareRunnable(task, requestContext));
        }
    }

    @RequiredArgsConstructor
    public static class ContextAwareRunnable implements Runnable {
        private final Runnable task;
        private final RequestScopedAttributes context;

        @Override
        public void run() {
            if (context != null) {
                CustomContext.set(context);
            }

            try {
                task.run();
            } finally {
                CustomContext.remove();
            }
        }
    }

}

