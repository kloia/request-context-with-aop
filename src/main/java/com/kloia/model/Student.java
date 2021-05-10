package com.kloia.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    private int id;

    private String name;

    private Integer age;

    private String email;

    private Integer classroomId;
}
