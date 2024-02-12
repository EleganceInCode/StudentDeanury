package org.openapitools.server.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.server.controller.dto.CreateSubjectRequest;
import org.openapitools.server.controller.dto.StudentResponse;
import org.openapitools.server.controller.dto.SubjectResponse;
import org.openapitools.server.repository.SubjectRepository;
import org.openapitools.server.repository.dao.SubjectDao;
import org.openapitools.server.service.StudentService;
import org.openapitools.server.service.SubjectService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final StudentService studentService;

    @Override
    public List<SubjectDao> findAll() {
        log.info("Найдены все предметы");
        return subjectRepository.findAll();
    }

    @Override
    public SubjectResponse findById(@NonNull Long id) {
        SubjectDao subject = subjectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                        "Предмет по id {0} не найден", id
                )));
        return mapToSubjectResponse(subject.getId());
    }

    @Override
    public @NonNull SubjectResponse createSubject(@NonNull CreateSubjectRequest request) {
        SubjectDao subject = new SubjectDao();
        return getSubjectResponse(request, subject);
    }

    @Override
    public @NonNull SubjectResponse update(@NonNull CreateSubjectRequest request) {
        SubjectDao subject = new SubjectDao();
        return getSubjectResponse(request, subject);
    }

    @NonNull
    private SubjectResponse getSubjectResponse(@NonNull CreateSubjectRequest request, SubjectDao existingSubject) {
        existingSubject.setName(request.getName());

        Set<StudentResponse> students = new HashSet<>();
        if (request.getStudents() != null) {
            for (Long studentId : request.getStudents()) {
                StudentResponse student = studentService.findById(studentId);
                students.add(student);
            }
        }
        existingSubject.setStudents(students);

        existingSubject = subjectRepository.save(existingSubject);

        return mapToSubjectResponse(existingSubject.getId());
    }

    @Override
    public void delete(@NonNull Long id) {
        subjectRepository.deleteById(id);
    }

    @Override
    public void deleteByIdIn(List<Long> ids) {
        subjectRepository.deleteAllById(ids);
    }
    private SubjectResponse mapToSubjectResponse(@NonNull Long subjectId) {
        SubjectResponse subjectResponse = new SubjectResponse();
        subjectResponse.setName(String.valueOf(subjectId));
        subjectResponse.setStudents(mapStudentsToStudentResponses(subjectId));
        return subjectResponse;
    }

    private Set<StudentResponse> mapStudentsToStudentResponses(@NonNull Long subjectId) {
        Set<StudentResponse> studentResponses = new HashSet<>();

        SubjectResponse subject = findById(subjectId);
        List<StudentResponse> students = subject.getStudents();
        for (StudentResponse student : students) {
            studentResponses.add(mapStudentToStudentResponse(student));
        }
        return studentResponses;
    }

    private StudentResponse mapStudentToStudentResponse(StudentResponse student) {
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setId(student.getId());
        studentResponse.setLogin(student.getLogin());
        studentResponse.setName(student.getName());
        studentResponse.setSurname(student.getSurname());
        studentResponse.setAge(student.getAge());

        return studentResponse;
    }

}
