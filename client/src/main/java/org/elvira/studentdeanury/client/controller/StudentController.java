package org.elvira.studentdeanury.client.controller;

import lombok.RequiredArgsConstructor;
import org.elvira.studentdeanury.client.service.StudentService;
import org.elvira.studentdeanury.codegen.api.StudentApi;
import org.elvira.studentdeanury.codegen.model.CreateStudentResponse;
import org.elvira.studentdeanury.codegen.model.StudentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class StudentController implements StudentApi {

    private final StudentService studentService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> adminGet(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                MessageFormat.format("Method by admin: {0}. Role is: {1}", userDetails.getUsername(),
                        userDetails.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.joining(",")))
        );
    }

    @Override
    public ResponseEntity<CreateStudentResponse> create(StudentDto studentDto) {
        studentService.create(studentDto);
        return ResponseEntity.ok().body(new CreateStudentResponse().message(
                """
                        The message flew to Kafka,
                        and Dto to the server to create a student"""));
    }

    @Override
    public ResponseEntity<CreateStudentResponse> update(UUID studentId, StudentDto studentDto) {
        studentService.update(studentId, studentDto);
        return ResponseEntity.ok().body(new CreateStudentResponse().message(
                """
                        Message flew to Kafka
                        for student update"""));
    }

    @Override
    public ResponseEntity<CreateStudentResponse> delete(UUID studentId) {
        studentService.delete(studentId);
        return ResponseEntity.ok().body(new CreateStudentResponse().message(
                """
                        The message was sent to Kafka,
                        Id: %s to the server for deletion""".formatted(studentId)));
    }

    @Override
    public ResponseEntity<CreateStudentResponse> findAll() {
        studentService.findAll();
        return ResponseEntity.ok().body(new CreateStudentResponse().message(
                """
                        The message flew to Kafka
                         to search for all students"""));
    }

    @Override
    public ResponseEntity<CreateStudentResponse> findById(UUID studentId) {
        studentService.findById(studentId);
        return ResponseEntity.ok().body(new CreateStudentResponse().message(
                """
                        The message was sent to Kafka
                        with id: %s to search for a student""".formatted(studentId)));
    }
}
