//package org.elvira.studentdeanury.server;
//
//import org.elvira.studentdeanury.server.repository.StudentRepository;
//import org.elvira.studentdeanury.server.repository.SubjectRepository;
//import org.elvira.studentdeanury.server.repository.dao.StudentModel;
//import org.elvira.studentdeanury.server.repository.dao.SubjectModel;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest(classes = DeaneryApplication.class)
//@DataJpaTest
//public class StudentServiceImplDataJpaTest {
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//    @Autowired
//    private StudentRepository studentRepository;
//
//    @Autowired
//    private SubjectRepository subjectRepository;
//
//    @Test
//    public void testCreateStudent() {
//        SubjectModel math = new SubjectModel().setName("Математика");
//        SubjectModel physics = new SubjectModel().setName("Физика");
//        SubjectModel programming = new SubjectModel().setName("Программирование");
//        entityManager.persist(math);
//        entityManager.persist(physics);
//        entityManager.persist(programming);
//        entityManager.flush();
//
//        StudentModel studentDao = new StudentModel();
//        studentDao.setLogin("testLogin");
//        studentDao.setFirstName("Test");
//        studentDao.setMiddleName("User");
//        studentDao.setLastName("Testovich");
//        studentDao.setAge(20);
//        Set<SubjectModel> subjectDaoSet = new HashSet<>();
//        subjectDaoSet.add(math);
//        subjectDaoSet.add(physics);
//        subjectDaoSet.add(programming);
//        studentDao.setSubjectDao(subjectDaoSet);
//
//        StudentModel savedStudent = studentRepository.save(studentDao);
//
//        assertThat(savedStudent.getId()).isNotNull();
//        assertThat(savedStudent.getSubjectDao()).hasSize(3);
//
//        assertThat(savedStudent.getSubjectDao()).extracting(SubjectModel::getName)
//                .containsExactlyInAnyOrder("Математика", "Физика", "Программирование");
//
//    }
//}
