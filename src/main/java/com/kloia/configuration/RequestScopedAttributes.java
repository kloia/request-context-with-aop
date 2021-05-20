package com.kloia.configuration;

import com.kloia.model.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestScopedAttributes {
    private String userId;
    private String studentId;
    private List<Student> students;
}
