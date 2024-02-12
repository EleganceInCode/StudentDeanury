package org.openapitools.server.controller.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Set;

@Data
@Accessors(chain = true)
public class SubjectResponse {
    private String name;

    private List<StudentResponse> students;

    public void setStudents(Set<StudentResponse> studentResponses) {
        this.students = List.copyOf(studentResponses);
    }
}
