package org.elvira.studentdeanury.server.repository.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "student_subject")
public class StudentSubjectDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentDao student;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private SubjectDao subjectDao;
}
