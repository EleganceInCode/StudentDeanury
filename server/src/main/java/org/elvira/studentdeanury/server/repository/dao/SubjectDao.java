package org.elvira.studentdeanury.server.repository.dao;

import lombok.*;
import lombok.experimental.Accessors;

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


    public void setStudents(Set<StudentDao> studentDaos) {
        Set<StudentDao> students = new HashSet<>();

        for (StudentDao student : studentDaos) {
            StudentDao studentDao = new StudentDao();
            studentDao.setLogin(student.getLogin());
            studentDao.setFirstName(student.getFirstName());
            studentDao.setMiddleName(student.getMiddleName());
            studentDao.setLastName(student.getLastName());
            studentDao.setAge(student.getAge());

            students.add(student);
        }

        this.students = students;

    }
}
