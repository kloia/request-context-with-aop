package com.kloia.controller;

import com.kloia.aspect.annotation.AttributeExtractor;
import com.kloia.model.Student;
import com.kloia.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = StudentController.ENDPOINT)
public class StudentController {

    public static final String ENDPOINT = "students";

    @Autowired
    private StudentService studentService;

    @GetMapping
    @AttributeExtractor
    public List<Student> getStudents(HttpServletRequest request,
                                     @RequestParam(value = "studentId", defaultValue = "0") String studentId) {
        return studentService.getStudents();
    }

}
