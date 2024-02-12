package org.openapitools.server.controller.dto;

import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Accessors;

import java.util.Set;

@Data
@Accessors(chain = true)
public class StudentResponse {

    private Long id;
    private String login;
    private String name;
    private String surname;
    private Integer age;
    private Set<SubjectResponse> subjectResponses;

    @NonNull
    public StudentResponse setSubjectResponses(Set<SubjectResponse> subjectResponses) {
        this.subjectResponses = subjectResponses;
        return this;
    }
}
