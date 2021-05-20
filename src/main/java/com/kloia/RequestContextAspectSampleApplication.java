package com.kloia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RequestContextAspectSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(RequestContextAspectSampleApplication.class, args);
    }


//
//    @Configuration
//    public class ExecutorConfig extends AsyncConfigurerSupport {
//        @Override
//        @Bean
//        public Executor getAsyncExecutor() {
//            return new ContextAwarePoolExecutor();
//        }
//    }
//
//    public class ContextAwarePoolExecutor extends ThreadPoolTaskExecutor {
//        @Override
//        public <T> Future<T> submit(Callable<T> task) {
//            return super.submit(new ContextAwareCallable(task, RequestContextHolder.currentRequestAttributes()));
//        }
//
//        @Override
//        public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
//            return super.submitListenable(new ContextAwareCallable(task, RequestContextHolder.currentRequestAttributes()));
//        }
//    }
//    public class ContextAwareCallable<T> implements Callable<T> {
//        private Callable<T> task;
//        private RequestAttributes context;
//
//        public ContextAwareCallable(Callable<T> task, RequestAttributes context) {
//            this.task = task;
//            this.context = context;
//        }
//
//        @Override
//        public T call() throws Exception {
//            if (context != null) {
//                RequestContextHolder.setRequestAttributes(context);
//            }
//
//            try {
//                return task.call();
//            } finally {
//                RequestContextHolder.resetRequestAttributes();
//            }
//        }
//    }

}
