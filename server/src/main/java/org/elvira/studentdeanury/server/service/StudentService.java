package org.elvira.studentdeanury.server.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elvira.studentdeanury.codegen.model.StudentDto;
import org.elvira.studentdeanury.codegen.model.SubjectDto;
import org.elvira.studentdeanury.server.repository.StudentRepository;
import org.elvira.studentdeanury.server.repository.SubjectRepository;
import org.elvira.studentdeanury.server.repository.dao.StudentModel;
import org.elvira.studentdeanury.server.repository.dao.SubjectModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    private final SubjectRepository subjectRepository;

    @Transactional
    public void createStudent(@NonNull StudentDto studentDto) {
        log.info("Начало создания студента: {}", studentDto);

        if (studentDto.getSubjects() != null) {
            studentDto.getSubjects().forEach(subjectDto ->
                    subjectRepository.findBySubjectName(subjectDto.getSubjectName())
                            .ifPresentOrElse(subjectModel -> {
                                    },
                                    () -> {
                                        SubjectModel newSubject = new SubjectModel().convertToSubjectModel(subjectDto);
                                        subjectRepository.save(newSubject);
                                    }
                            )
            );
        }

        StudentModel student = buildStudentRequest(studentDto);
        StudentDto studentResponse = buildStudentResponse(studentRepository.save(student));
        log.info("Студент успешно создан: {}", studentResponse);
    }

    private void studentUpdate(StudentModel student, StudentDto studentDto) {
        Optional.ofNullable(studentDto.getLogin()).ifPresent(student::setLogin);
        Optional.ofNullable(studentDto.getFirstName()).ifPresent(student::setFirstName);
        Optional.ofNullable(studentDto.getMiddleName()).ifPresent(student::setMiddleName);
        Optional.ofNullable(studentDto.getLastName()).ifPresent(student::setLastName);
        Optional.ofNullable(studentDto.getAge()).ifPresent(student::setAge);

        if (studentDto.getSubjects() != null) {
            Set<SubjectModel> subjects = studentDto.getSubjects().stream()
                    .map(this::convertToSubjectModel)
                    .collect(Collectors.toSet());
            student.setSubjectModels(subjects);
        }
    }

    @Transactional(readOnly = true)
    public @NonNull List<StudentDto> findAll() {
        log.info("Список всех студентов");
        return Objects.requireNonNull(studentRepository)
                .findAll()
                .stream()
                .map(this::buildStudentResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public StudentDto findById(@NonNull UUID studentId) {
        log.info("Данные студента под ID: " + studentId);
        return Objects.requireNonNull(studentRepository)
                .findById(studentId)
                .map(this::buildStudentResponse)
                .orElseThrow(() -> new EntityNotFoundException("Cтудент  " + studentId + " не найден"));
    }

    @Transactional
    public @NonNull StudentDto update(@NonNull UUID studentId, @NonNull StudentDto studentDto) {
        log.info("Данные студента обновлены под ID: " + studentId);
        StudentModel student = Objects.requireNonNull(studentRepository)
                .findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Студент " + studentId + " не найден"));
        studentUpdate(student, studentDto);
        return buildStudentResponse(studentRepository.save(student));
    }

    @Transactional
    public void delete(@NonNull UUID studentId) {
        log.info("Студент удален под id: " + studentId);
        Objects.requireNonNull(studentRepository)
                .deleteById(studentId);
    }

    private SubjectDto converToSubjectDto(SubjectModel subjectModel) {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setSubjectName(subjectModel.getSubjectName());
        return subjectDto;
    }

    private SubjectModel convertToSubjectModel(SubjectDto subjectDto) {
        SubjectModel subjectModel = new SubjectModel();
        subjectModel.setSubjectName(subjectDto.getSubjectName());
        return subjectRepository.findBySubjectName(subjectDto.getSubjectName())
                .orElseThrow(() -> new EntityNotFoundException("Subject not found: " + subjectDto.getSubjectName()));
    }

    @NonNull
    private StudentDto buildStudentResponse(StudentModel student) {

        StudentDto studentDto = new StudentDto();
        studentDto.setLogin(student.getLogin());
        studentDto.setFirstName(student.getFirstName());
        studentDto.setMiddleName(student.getMiddleName());
        studentDto.setLastName(student.getLastName());
        studentDto.setAge(student.getAge());

        Set<SubjectDto> subjectsDto = new HashSet<>();
        for (SubjectModel subjectModel : student.getSubjectModels()) {
            SubjectDto subjectDto = converToSubjectDto(subjectModel);
            subjectsDto.add(subjectDto);
        }
        studentDto.setSubjects(subjectsDto);

        return studentDto;
    }

    @NonNull
    private StudentModel buildStudentRequest(StudentDto studentDto) {
        StudentModel student = new StudentModel()
                .setLogin(studentDto.getLogin())
                .setFirstName(studentDto.getFirstName())
                .setMiddleName(studentDto.getMiddleName())
                .setLastName(studentDto.getLastName())
                .setAge(studentDto.getAge());

        if (studentDto.getSubjects() != null) {
            Set<SubjectModel> subjects = studentDto.getSubjects().stream()
                    .map(this::convertToSubjectModel)//
                    .collect(Collectors.toSet());
            student.setSubjectModels(subjects);
        }
        return student;
    }
}
