package org.elvira.studentdeanury.server;

import org.elvira.studentdeanury.server.repository.StudentRepository;
import org.elvira.studentdeanury.server.repository.SubjectRepository;
import org.elvira.studentdeanury.server.repository.dao.StudentDao;
import org.elvira.studentdeanury.server.repository.dao.SubjectDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest(classes = DemoDeaneryApplication.class)
@DataJpaTest
public class StudentServiceImplDataJpaTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Test
    public void testCreateStudent() {
        SubjectDao math = new SubjectDao().setName("Математика");
        SubjectDao physics = new SubjectDao().setName("Физика");
        SubjectDao programming = new SubjectDao().setName("Программирование");
        entityManager.persist(math);
        entityManager.persist(physics);
        entityManager.persist(programming);
        entityManager.flush();

        StudentDao studentDao = new StudentDao();
        studentDao.setLogin("testLogin");
        studentDao.setFirstName("Test");
        studentDao.setMiddleName("User");
        studentDao.setLastName("Testovich");
        studentDao.setAge(20);
        Set<SubjectDao> subjectDaoSet = new HashSet<>();
        subjectDaoSet.add(math);
        subjectDaoSet.add(physics);
        subjectDaoSet.add(programming);
        studentDao.setSubjectDao(subjectDaoSet);

        StudentDao savedStudent = studentRepository.save(studentDao);

        assertThat(savedStudent.getId()).isNotNull();
        assertThat(savedStudent.getSubjectDao()).hasSize(3);

        assertThat(savedStudent.getSubjectDao()).extracting(SubjectDao::getName)
                .containsExactlyInAnyOrder("Математика", "Физика", "Программирование");

    }
}
