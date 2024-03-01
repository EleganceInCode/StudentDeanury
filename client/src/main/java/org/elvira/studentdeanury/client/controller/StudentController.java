package org.elvira.studentdeanury.client.controller;

import lombok.RequiredArgsConstructor;
import org.elvira.studentdeanury.client.service.StudentService;
import org.elvira.studentdeanury.codegen.api.StudentApi;
import org.elvira.studentdeanury.codegen.model.CreateStudentResponse;
import org.elvira.studentdeanury.codegen.model.StudentDto;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class StudentController implements StudentApi {

    private final StudentService studentService;

    @Override
    public ResponseEntity<CreateStudentResponse> create(StudentDto studentDto) {
        studentService.create(studentDto);
        return ResponseEntity.ok().body(new CreateStudentResponse().message("The message flew to Kafka, and Dto to the server to create a student"));
    }

    @Override
    public ResponseEntity<CreateStudentResponse> update(UUID studentId, StudentDto studentDto) {
        studentService.update(studentId, studentDto);
        return ResponseEntity.ok().body(new CreateStudentResponse().message("Message flew to Kafka for student update"));
    }

    @Override
    public ResponseEntity<CreateStudentResponse> delete(UUID studentId) {
        studentService.delete(studentId);
        return ResponseEntity.ok().body(new CreateStudentResponse().message(String.format("The message was sent to Kafka, Id: %s to the server for deletion", studentId)));
    }

    @Override
    public ResponseEntity<CreateStudentResponse> findAll() {
        studentService.findAll();
        return ResponseEntity.ok().body(new CreateStudentResponse().message("The message flew to Kafka to search for all students"));
    }

    @Override
    public ResponseEntity<CreateStudentResponse> findById(UUID studentId) {
        studentService.findById(studentId);
        return ResponseEntity.ok().body(new CreateStudentResponse().message(String.format("The message was sent to Kafka with id: %s to search for a student", studentId)));
    }
}
