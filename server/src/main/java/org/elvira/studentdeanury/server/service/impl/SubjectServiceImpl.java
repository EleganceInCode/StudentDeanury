package org.elvira.studentdeanury.server.service.impl;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elvira.studentdeanury.server.service.SubjectService;
import org.elvira.studentdeanury.server.dto.CreateSubjectRequest;
import org.elvira.studentdeanury.server.dto.StudentResponse;
import org.elvira.studentdeanury.server.dto.SubjectResponse;
import org.elvira.studentdeanury.server.repository.StudentRepository;
import org.elvira.studentdeanury.server.repository.SubjectRepository;
import org.elvira.studentdeanury.server.repository.dao.StudentDao;
import org.elvira.studentdeanury.server.repository.dao.SubjectDao;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;

    @Override
    public List<SubjectDao> findAll() {
        log.info("Найдены все предметы");
        return Objects.requireNonNull(subjectRepository).findAll();
    }

    @Override
    public SubjectResponse findById(@NonNull Long id) {
        SubjectDao subject = Objects
                .requireNonNull(subjectRepository)
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                        "Предмет по id {0} не найден", id
                )));
        return mapToSubjectResponse(subject);
    }

    @Override
    public @NonNull SubjectResponse createSubject(@NonNull CreateSubjectRequest request) {
        log.info("Начало создания предмета с именем: {}", request.getName());
        SubjectDao subject = new SubjectDao();
        SubjectResponse subjectResponse = getSubjectResponse(request,subject);
        log.info("Предмет успешно создан с ID: {}", subjectResponse.getId());
        return subjectResponse;
    }

    @Override
    public @NonNull SubjectResponse update(@NonNull CreateSubjectRequest request) {
        SubjectDao subject = new SubjectDao();
        return getSubjectResponse(request, subject);
    }

    @NonNull
    private SubjectResponse getSubjectResponse(@NonNull CreateSubjectRequest request, SubjectDao existingSubject) {
        log.info("Обновление/Создание предмета: {}", request.getName());
        existingSubject.setName(request.getName());

        if (request.getStudents() != null && !request.getStudents().isEmpty()) {
            Set<StudentDao> students = new HashSet<>();
            for (Long studentId : request.getStudents()) {
                Objects
                        .requireNonNull(studentRepository)
                        .findById(studentId)
                        .ifPresentOrElse(students::add,
                                () -> log.warn("Студент с ID {} не найден и не может быть добавлен к предмету", studentId)
                        );
            }
            existingSubject.setStudents(students);
        }

        existingSubject = Objects.requireNonNull(subjectRepository).save(existingSubject);
        log.info("Предмет {} успешно сохранен с ID: {}", existingSubject.getName(),existingSubject.getId());

        return mapToSubjectResponse(existingSubject);
    }

    @Override
    public void delete(@NonNull Long id) {
        Objects.requireNonNull(subjectRepository).deleteById(id);
    }

    @Override
    public void deleteByIdIn(List<Long> ids) {
        Objects.requireNonNull(subjectRepository).deleteAllById(ids);
    }
    private SubjectResponse mapToSubjectResponse(@NonNull SubjectDao subject) {
        log.info("Преобразование предмета в SubjectResponse c ID: {}", subject.getId());
        SubjectResponse subjectResponse = new SubjectResponse();
        subjectResponse.setId(subject.getId());
        subjectResponse.setName(subject.getName());
        subjectResponse.setStudents(mapStudentsToStudentResponses(subject.getId()));
        return subjectResponse;
    }

    private Set<StudentResponse> mapStudentsToStudentResponses(@NonNull Long subjectId) {
        Set<StudentResponse> studentResponses = new HashSet<>();

        SubjectResponse subject = findById(subjectId);
        Set<StudentResponse> students = subject.getStudents();
        for (StudentResponse student : students) {
            studentResponses.add(mapStudentToStudentResponse(student));
        }
        return studentResponses;
    }

    private StudentResponse mapStudentToStudentResponse(StudentResponse student) {
        StudentResponse studentResponse = new StudentResponse();
        
        studentResponse.setId(student.getId());
        studentResponse.setLogin(student.getLogin());
        studentResponse.setFirstName(student.getFirstName());
        studentResponse.setMiddleName(student.getMiddleName());
        studentResponse.setLastName(student.getLastName());
        studentResponse.setAge(student.getAge());
        studentResponse.setSubjectResponses(student.getSubjectResponses());

        return studentResponse;
    }

}
