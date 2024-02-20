package org.openapitools;

import org.elvira.studentdeanury.server.dto.CreateStudentRequest;
import org.elvira.studentdeanury.server.dto.StudentResponse;
import org.elvira.studentdeanury.server.repository.StudentRepository;
import org.elvira.studentdeanury.server.repository.dao.StudentDao;
import org.elvira.studentdeanury.server.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test void testCreateStudent() {
        CreateStudentRequest request = new CreateStudentRequest()
                .setLogin("testLogin")
                .setFirstName("Test")
                .setMiddleName("User")
                .setLastName("Testovich")
                .setAge(20);

        StudentDao studentDao = new StudentDao()
                .setLogin(request.getLogin())
                .setFirstName(request.getFirstName())
                .setMiddleName(request.getMiddleName())
                .setLastName(request.getLastName())
                .setAge(request.getAge());

        when(studentRepository.save(any(StudentDao.class))).thenReturn(studentDao);

        StudentResponse createStudent = studentService.createStudent(request);

        assertThat(createStudent).isNotNull();
        assertThat(createStudent.getLogin()).isEqualTo(request.getLogin());
        assertThat(createStudent.getFirstName()).isEqualTo(request.getFirstName());
        assertThat(createStudent.getMiddleName()).isEqualTo(request.getMiddleName());
        assertThat(createStudent.getLastName()).isEqualTo(request.getLastName());
        assertThat(createStudent.getAge()).isEqualTo(request.getAge());

        verify(studentRepository,times(1)).save(any(StudentDao.class));
    }
}
