package org.elvira.studentdeanury.server.repository.dao;

import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "student")// todo имена таблиц в единственном числе пишем
public class StudentModel {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @Column(name = "login",nullable = false, unique = true)
    private String login;

    private String firstName;

    private String middleName;

    private String lastName;

    private Integer age;

    @ManyToMany
    @JoinTable(
            name = "student_subject",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private Set<SubjectModel> subjectModels = new HashSet<>();

    public void addSubject(SubjectModel subjectModel) {
        this.subjectModels.add(subjectModel);
    }

    public void removeSubject(UUID subjectId) {
        subjectModels = subjectModels.stream().filter(o -> !o.getId().equals(subjectId)).collect(Collectors.toSet());
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentModel student = (StudentModel) o;
        return login.equals(student.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }

}
