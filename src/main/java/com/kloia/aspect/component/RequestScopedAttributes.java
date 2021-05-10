package com.kloia.aspect.component;

import com.kloia.model.Student;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@Getter
@Setter
@Component
@RequestScope
public class RequestScopedAttributes {
    private String userId;
    private String studentId;
    private List<Student> students;
}
