package org.elvira.studentdeanury.server.repository.dao;

import lombok.*;
import lombok.experimental.Accessors;
import org.elvira.studentdeanury.codegen.model.SubjectDto;

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
@Table(name = "subject")
public class SubjectModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subject_name")//todo добавить тогда надо  unique = true) сюда, раз у тебя equals по этому полю.
    private String subjectName;

    @ManyToMany
    @JoinTable(
            name = "student_subject",
            joinColumns = @JoinColumn(name = "subject_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<StudentModel> students = new HashSet<>();

    public SubjectModel convertToSubjectModel(SubjectDto subjectDto) {
        SubjectModel subjectModel = new SubjectModel();
        subjectModel.setSubjectName(subjectDto.getSubjectName());
        return subjectModel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubjectModel subjectDao = (SubjectModel) o;
        return subjectName.equals(subjectDao.subjectName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subjectName);
    }

}
