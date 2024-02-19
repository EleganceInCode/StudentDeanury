package org.elvira.studentdeanury.server.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

@Data
@Accessors(chain = true)
public class SubjectResponse {
    private String name;

    private Set<StudentResponse> students;

    public void setStudents(Set<StudentResponse> studentResponses) {
        this.students = studentResponses;
    }
}
