package org.elvira.studentdeanury.server.service;

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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    private final SubjectRepository subjectRepository;

    @Transactional
    public void createStudent(StudentDto studentDto) {
        log.info("Starting to create a student: {}", studentDto);

        if (studentDto.getSubjects() != null) {
            studentDto.getSubjects().forEach(subjectDto -> {
                subjectRepository.findBySubjectName(subjectDto.getSubjectName())
                        .orElseGet(() -> {
                            SubjectModel newSubject = getExistingSubjectOrThrow(subjectDto);
                            return subjectRepository.save(newSubject);
                        });
            });
        }

        StudentModel student = mapStudentDtoToModel(studentDto);
        StudentModel savedStudentModel = studentRepository.save(student);
        StudentDto studentResponse = mapStudentModelToDto(savedStudentModel);
        log.info("Student successfully created: {}", studentResponse);
    }

    private void studentUpdate(StudentModel student, StudentDto studentDto) {
        Optional.ofNullable(studentDto.getLogin()).ifPresent(student::setLogin);
        Optional.ofNullable(studentDto.getFirstName()).ifPresent(student::setFirstName);
        Optional.ofNullable(studentDto.getMiddleName()).ifPresent(student::setMiddleName);
        Optional.ofNullable(studentDto.getLastName()).ifPresent(student::setLastName);
        Optional.ofNullable(studentDto.getAge()).ifPresent(student::setAge);

        if (studentDto.getSubjects() != null) {
            Set<SubjectModel> subjects = studentDto.getSubjects().stream()
                    .map(this::getExistingSubjectOrThrow)
                    .collect(Collectors.toSet());
            student.setSubjectModels(subjects);
        }
    }

    @Transactional(readOnly = true)
    public void findAll() {
        log.info("List of all students");
        List<StudentDto> collect = studentRepository
                .findAll()
                .stream()
                .map(this::mapStudentModelToDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public void findById(UUID studentId) {
        log.info("Student ID details: {} ", studentId);
        studentRepository
                .findById(studentId)
                .map(this::mapStudentModelToDto)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Student ID: %s not found", studentId)));
    }

    @Transactional
    public void update(UUID studentId, StudentDto studentDto) {
        log.info("Start updating student ID: {} ", studentId);
        StudentModel student = studentRepository
                .findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Student ID: %s not found", studentId)));
        studentUpdate(student, studentDto);
        mapStudentModelToDto(studentRepository.save(student));
    }

    @Transactional
    public void delete(UUID studentId) {
        log.info("Student removed under ID: {} ", studentId);
        studentRepository.deleteById(studentId);
    }

    private SubjectDto converToSubjectDto(SubjectModel subjectModel) {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setSubjectName(subjectModel.getSubjectName());
        return subjectDto;
    }

    private SubjectModel getExistingSubjectOrThrow(SubjectDto subjectDto) {
        return subjectRepository.findBySubjectName(subjectDto.getSubjectName())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Subject not found: %s ", subjectDto.getSubjectName())));
    }

    private StudentDto mapStudentModelToDto(StudentModel student) {
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

    private StudentModel mapStudentDtoToModel(StudentDto studentDto) {
        StudentModel student = new StudentModel()
                .setLogin(studentDto.getLogin())
                .setFirstName(studentDto.getFirstName())
                .setMiddleName(studentDto.getMiddleName())
                .setLastName(studentDto.getLastName())
                .setAge(studentDto.getAge());

        if (studentDto.getSubjects() != null) {
            Set<SubjectModel> subjects = studentDto.getSubjects().stream()
                    .map(this::getExistingSubjectOrThrow)// todo убрать слеши
                    .collect(Collectors.toSet());
            student.setSubjectModels(subjects);
        }
        return student;
    }
}
