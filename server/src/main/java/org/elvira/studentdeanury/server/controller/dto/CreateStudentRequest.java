package org.elvira.studentdeanury.server.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CreateStudentRequest {
    private Long id;
    private String login;
    private String name;
    private String surname;
    private Integer age;
    private CreateSubjectRequest subject;
}
