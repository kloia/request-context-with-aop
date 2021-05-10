package com.kloia.service;


import com.kloia.aspect.annotation.AttributeExtractor;
import com.kloia.model.Student;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentService {

    private final AuxiliaryService auxiliaryService;

    public List<Student> getStudents() {
        auxiliaryService.auxiliaryActions();
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
