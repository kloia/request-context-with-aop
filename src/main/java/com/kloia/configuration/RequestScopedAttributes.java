package com.kloia.configuration;

import com.kloia.model.Student;
import lombok.Data;

import java.util.List;

@Data
public class RequestScopedAttributes {
    private String userId;
    private String studentId;
    private List<Student> students;
}
