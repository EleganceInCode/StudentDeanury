//package org.elvira.studentdeanury.server;
//
//import org.elvira.studentdeanury.server.repository.StudentRepository;
//import org.elvira.studentdeanury.server.repository.dao.StudentModel;
//import org.elvira.studentdeanury.server.repository.dao.SubjectModel;
//import org.elvira.studentdeanury.server.service.StudentServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.Set;
//import java.util.stream.Collectors;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//public class StudentServiceImplTest {
//
//    @Mock
//    private StudentRepository studentRepository;
//
//    @InjectMocks
//    private StudentServiceImpl studentService;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test void testCreateStudent() {
//        CreateSubjectRequest math = new CreateSubjectRequest().setName("Математика");
//        CreateSubjectRequest physics = new CreateSubjectRequest().setName("Физика");
//        CreateSubjectRequest programming = new CreateSubjectRequest().setName("Программирование");
//
//        Set<CreateSubjectRequest> subjects = Set.of(math,physics,programming);
//
//        CreateStudentRequest request = new CreateStudentRequest()
//                .setLogin("testLogin")
//                .setFirstName("Test")
//                .setMiddleName("User")
//                .setLastName("Testovich")
//                .setAge(20)
//                .setSubjects(subjects);
//
//        Set<SubjectModel> subjectDaos = subjects.stream()
//                .map(subjectRequest -> new SubjectModel().setName(subjectRequest.getName()))
//                .collect(Collectors.toSet());
//
//
//        StudentModel studentDao = new StudentModel()
//                .setLogin(request.getLogin())
//                .setFirstName(request.getFirstName())
//                .setMiddleName(request.getMiddleName())
//                .setLastName(request.getLastName())
//                .setAge(request.getAge())
//                        .setSubjectDao(subjectDaos);
//
//
//
//        when(studentRepository.save(any(StudentModel.class))).thenReturn(studentDao);
//
//        StudentResponse createStudent = studentService.createStudent(request);
//
//        assertThat(createStudent).isNotNull();
//        assertThat(createStudent.getLogin()).isEqualTo(request.getLogin());
//        assertThat(createStudent.getFirstName()).isEqualTo(request.getFirstName());
//        assertThat(createStudent.getMiddleName()).isEqualTo(request.getMiddleName());
//        assertThat(createStudent.getLastName()).isEqualTo(request.getLastName());
//        assertThat(createStudent.getAge()).isEqualTo(request.getAge());
//        assertThat(createStudent.getSubjectResponses().size()).isEqualTo(subjects.size());
//
//        verify(studentRepository,times(1)).save(any(StudentModel.class));
//    }
//}
