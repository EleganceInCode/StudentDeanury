package org.elvira.studentdeanury.server.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.util.Set;

@Data
@Accessors(chain = true)
public class StudentResponse {

    private Long id;
    private String login;
    private String firstName;
    private String middleName;
    private String lastName;
    private Integer age;
    private String status;
    private Instant date;
    private Set<SubjectResponse> subjectResponses;

}
