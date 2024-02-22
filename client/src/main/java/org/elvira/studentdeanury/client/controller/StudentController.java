package org.elvira.studentdeanury.client.controller;

import lombok.RequiredArgsConstructor;
import org.elvira.studentdeanury.client.service.StudentService;

import org.elvira.studentdeanury.codogen.api.StudentApi;
import org.elvira.studentdeanury.codogen.model.CreateStudentResponse;
import org.elvira.studentdeanury.codogen.model.StudentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController implements StudentApi {

    private final StudentService studentService;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return StudentApi.super.getRequest();
    }

    @Override
    public ResponseEntity<CreateStudentResponse> create(StudentDto studentDto) {
        studentService.create(studentDto);
        return ResponseEntity.ok(new CreateStudentResponse().message("Message sent to kafka"));
    }

    @Override
    public ResponseEntity<Void> delete(UUID studentId) {
        return StudentApi.super.delete(studentId);
    }

    @Override
    public ResponseEntity<List<StudentDto>> findAll() {
        return StudentApi.super.findAll();
    }

    @Override
    public ResponseEntity<StudentDto> findById(UUID studentId) {
        return StudentApi.super.findById(studentId);
    }
}
