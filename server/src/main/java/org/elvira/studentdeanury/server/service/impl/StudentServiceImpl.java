package org.elvira.studentdeanury.server.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elvira.studentdeanury.server.controller.dto.CreateStudentRequest;
import org.elvira.studentdeanury.server.controller.dto.CreateSubjectRequest;
import org.elvira.studentdeanury.server.controller.dto.StudentResponse;
import org.elvira.studentdeanury.server.controller.dto.SubjectResponse;
import org.elvira.studentdeanury.server.repository.StudentRepository;
import org.elvira.studentdeanury.server.repository.dao.StudentDao;
import org.elvira.studentdeanury.server.repository.dao.SubjectDao;
import org.elvira.studentdeanury.server.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    @Override
    @Transactional(readOnly = true)
    public @NonNull List<StudentResponse> findAll() {
        log.info("Список всех студентов");
        return studentRepository.findAll()
                .stream()
                .map(this::buildStudentResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public StudentResponse findById(@NonNull Long studentId) {
        log.info("Данные студента под ID: " + studentId);
        return studentRepository.findById(studentId)
                .map(this::buildStudentResponse)
                .orElseThrow(() -> new EntityNotFoundException("Cтудент  " + studentId + " не найден"));
    }

    @Override
    @Transactional
    public @NonNull StudentResponse createStudent(@NonNull CreateStudentRequest request) {
        log.info("Студент успешно создан");
        StudentDao student = buildStudentRequest(request);
        return buildStudentResponse(studentRepository.save(student));
    }

    @Override
    @Transactional
    public @NonNull StudentResponse update(@NonNull Long studentId, @NonNull CreateStudentRequest request) {
        log.info("Данные студента обновлены под ID: " + studentId);
        StudentDao student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Студент " + studentId + " не найден"));
        studentUpdate(student, request);
        return buildStudentResponse(studentRepository.save(student));
    }

    @Override
    @Transactional
    public void delete(@NonNull Long studentId) {
        log.info("Студент удален под id: " + studentId);
        studentRepository.deleteById(studentId);
    }

    @NonNull
    private StudentResponse buildStudentResponse(StudentDao student) {
        return new StudentResponse()
                .setLogin(student.getLogin())
                .setAge(student.getAge())
                .setName(student.getName())
                .setSurname(student.getSurname())
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
        if (request.getSubject() != null) {
            subjects.add(new SubjectDao().setName(request.getSubject().getName()));
        }

        return new StudentDao()
                .setLogin(request.getLogin())
                .setAge(request.getAge())
                .setName(request.getName())
                .setSurname(request.getSurname())
                .setSubjectDao(subjects);
    }

    private void studentUpdate(StudentDao student, CreateStudentRequest request) {
        ofNullable(request.getLogin()).map(student::setLogin);
        ofNullable(request.getName()).map(student::setName);
        ofNullable(request.getSurname()).map(student::setSurname);
        ofNullable(request.getAge()).map(student::setAge);

        CreateSubjectRequest subjectRequest = request.getSubject();
        if (subjectRequest != null) {
            Set<SubjectDao> subjects = new HashSet<>();
            subjects.add(new SubjectDao().setName(subjectRequest.getName()));
            student.setSubjectDao(subjects);
        }
    }
}
