package org.elvira.studentdeanury.server.repository.dao;

import lombok.*;
import lombok.experimental.Accessors;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "students")
public class StudentDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login",nullable = false, unique = true)
    private String login;


    private String firstName;


    private String middleName;


    private String lastName;


    private Integer age;

    @Getter
    @ManyToMany
    @JoinTable(
            name = "student_subject",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private Set<SubjectDao> subjectDao = new HashSet<>();

    public void addSubject(SubjectDao subjectDao) {
        this.subjectDao.add(subjectDao);
    }

    public void removeSubject(Long subjectId) {
        subjectDao = subjectDao.stream().filter(o -> !o.getId().equals(subjectId)).collect(Collectors.toSet());
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentDao student = (StudentDao) o;
        return login.equals(student.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }

}
