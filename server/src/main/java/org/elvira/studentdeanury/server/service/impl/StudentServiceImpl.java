package org.elvira.studentdeanury.server.service.impl;


import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elvira.studentdeanury.server.dto.CreateStudentRequest;
import org.elvira.studentdeanury.server.dto.CreateSubjectRequest;
import org.elvira.studentdeanury.server.dto.StudentResponse;
import org.elvira.studentdeanury.server.dto.SubjectResponse;
import org.elvira.studentdeanury.server.repository.StudentRepository;
import org.elvira.studentdeanury.server.repository.dao.StudentDao;
import org.elvira.studentdeanury.server.repository.dao.SubjectDao;
import org.elvira.studentdeanury.server.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Service
@Slf4j
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    @Transactional(readOnly = true)
    public @NonNull List<StudentResponse> findAll() {
        log.info("Список всех студентов");
        return Objects.requireNonNull(studentRepository)
                .findAll()
                .stream()
                .map(this::buildStudentResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public StudentResponse findById(@NonNull Long studentId) {
        log.info("Данные студента под ID: " + studentId);
        return Objects.requireNonNull(studentRepository)
                .findById(studentId)
                .map(this::buildStudentResponse)
                .orElseThrow(() -> new EntityNotFoundException("Cтудент  " + studentId + " не найден"));
    }

    @Override
    @Transactional
    public @NonNull StudentResponse createStudent(@NonNull CreateStudentRequest request) {
        log.info("Начало создания студента: {}", request);
        StudentDao student = buildStudentRequest(request);
        StudentResponse studentResponse = buildStudentResponse(Objects.requireNonNull(studentRepository).save(student));
        log.info("Студент успешно создан: {}", studentResponse);
        return studentResponse;
    }

    @Override
    @Transactional
    public @NonNull StudentResponse update(@NonNull Long studentId, @NonNull CreateStudentRequest request) {
        log.info("Данные студента обновлены под ID: " + studentId);
        StudentDao student = Objects.requireNonNull(studentRepository)
                .findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Студент " + studentId + " не найден"));
        studentUpdate(student, request);
        return buildStudentResponse(studentRepository.save(student));
    }

    @Override
    @Transactional
    public void delete(@NonNull Long studentId) {
        log.info("Студент удален под id: " + studentId);
        Objects.requireNonNull(studentRepository)
                .deleteById(studentId);
    }

    @NonNull
    private StudentResponse buildStudentResponse(StudentDao student) {
        return new StudentResponse()
                .setLogin(student.getLogin())
                .setAge(student.getAge())
                .setFirstName(student.getFirstName())
                .setMiddleName(student.getMiddleName())
                .setLastName(student.getLastName())
                .setSubjectResponses(buildSubjectResponses(student.getSubjectDao()));
    }

    private Set<SubjectResponse> buildSubjectResponses(Set<SubjectDao> subjects) {
        if (subjects == null || subjects.isEmpty()) {
            return Collections.emptySet();
        }

        Set<SubjectResponse> subjectResponses = new HashSet<>();
        for (SubjectDao subject : subjects) {
            SubjectResponse subjectResponse = new SubjectResponse()
                    .setName(subject.getName());
            subjectResponses.add(subjectResponse);
        }
        return subjectResponses;
    }
    @NonNull
    private StudentDao buildStudentRequest(CreateStudentRequest request) {
        Set<SubjectDao> subjects = new HashSet<>();
        if (request.getSubjects() != null && !request.getSubjects().isEmpty()) {
            for(CreateSubjectRequest subjectRequest : request.getSubjects()) {
                subjects.add(new SubjectDao().setName(subjectRequest.getName()));
            }
        }

        return new StudentDao()
                .setLogin(request.getLogin())
                .setAge(request.getAge())
                .setFirstName(request.getFirstName())
                .setMiddleName(request.getMiddleName())
                .setLastName(request.getLastName())
                .setSubjectDao(subjects);
    }

    private void studentUpdate(StudentDao student, CreateStudentRequest request) {
        ofNullable(request.getLogin()).map(student::setLogin);
        ofNullable(request.getFirstName()).map(student::setFirstName);
        ofNullable(request.getMiddleName()).map(student::setMiddleName);
        ofNullable(request.getLastName()).map(student::setLastName);
        ofNullable(request.getAge()).map(student::setAge);


        Set<CreateSubjectRequest> subjectRequests = request.getSubjects();
        if (subjectRequests != null) {
            Set<SubjectDao> subjects = new HashSet<>();
            for (CreateSubjectRequest subjectRequest: subjectRequests ) {
                subjects.add((new SubjectDao().setName(subjectRequest.getName())));
            }
        }
    }
}
