package com.kloia.service;


import com.kloia.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

@Slf4j
@Service
public class StudentService {

    @Autowired
    @Qualifier("myExecutor")
    private Executor executor;

    @Autowired
    private AuxiliaryService auxiliaryService;

    @Autowired
    private Auxiliary2Service auxiliary2Service;

    public List<Student> getStudents() throws ExecutionException, InterruptedException {

        CompletableFuture<Boolean> future1 = CompletableFuture.supplyAsync(() -> auxiliaryService.auxiliaryActions(), executor);
        CompletableFuture<Boolean> future2 = CompletableFuture.supplyAsync(() -> auxiliary2Service.auxiliaryActions(), executor);
        boolean myValue = future1.get() && future2.get();
        if (!myValue) {
            System.err.println("NOT TRUE");
        }

//        for (int i = 0; i < 100; i++) {
//            CompletableFuture<Void> future3 = CompletableFuture.runAsync(() -> auxiliaryService.auxiliaryActions(), executor);
//            CompletableFuture<Void> future4 = CompletableFuture.runAsync(() -> auxiliary2Service.auxiliaryActions(), executor);
//            CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(future3, future4);
//            voidCompletableFuture.get();
//            if (!voidCompletableFuture.isDone()) {
//                System.out.println("NOT DONE");
//            }
//        }

        List<CompletableFuture<Void>> futureList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            futureList.add(CompletableFuture.runAsync(() -> auxiliaryService.auxiliaryActions(), executor));
        }
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[futureList.size()]));
        voidCompletableFuture.get();
        if (!voidCompletableFuture.isDone()) {
            System.out.println("NOT DONE");
        }


//        for (int i = 0; i < 200; i++) {
//            executor.execute(auxiliaryService::auxiliaryActions);
//            executor.execute(auxiliary2Service::auxiliaryActions);
//        }
        //auxiliaryService.auxiliaryActions();
        return Collections.singletonList(
                Student.builder()
                        .id(1)
                        .name("Hikmet Semiz")
                        .age(24)
                        .email("hikmetsemiz@kloia.com")
                        .classroomId(1)
                        .build()
        );
    }


//    static class MyCompletableFuture<T> extends CompletableFuture<T> {
//        Supplier<T> mySupplier = new Supplier<T>(Supplier<T> originalSupplier) {
//            @Override
//            public T get() {
//                try {
//                    CustomContext.set(mainContext);
//                    return originalSupplier.get();
//                } finally {
//                    CustomContext.remove();
//                }
//            }
//        }

}

