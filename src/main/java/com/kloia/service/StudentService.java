package com.kloia.service;


import com.kloia.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

@Slf4j
@Service
public class StudentService {

    @Autowired
    @Qualifier("myExecutor")
    private Executor executor;

    @Autowired
    private AuxiliaryService auxiliaryService;

    public List<Student> getStudents() {
        for (int i = 0; i < 200; i++) {
            executor.execute(auxiliaryService::auxiliaryActions);
        }
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
}
