package org.openapitools.server.repository.dao;

import lombok.*;
import lombok.experimental.Accessors;
import org.openapitools.server.controller.dto.StudentResponse;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "subjects")
public class SubjectDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(
            name = "student_subject",
            joinColumns = @JoinColumn(name = "subject_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<StudentDao> students = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubjectDao subjectDao = (SubjectDao) o;
        return name.equals(subjectDao.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public void setStudents(Set<StudentResponse> studentResponses) {
        Set<StudentDao> students = new HashSet<>();

        for (StudentResponse studentResponse : studentResponses) {
            StudentDao student = new StudentDao();
            student.setLogin(studentResponse.getLogin());
            student.setName(studentResponse.getName());
            student.setSurname(studentResponse.getSurname());
            student.setAge(studentResponse.getAge());

            students.add(student);
        }

        this.students = students;

    }
}
