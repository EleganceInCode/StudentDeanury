package org.elvira.studentdeanury.client.controller;

import lombok.RequiredArgsConstructor;
import org.openapitools.studentdeanery.api.StudentApi;
import org.openapitools.studentdeanery.model.StudentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentApi studentService;

    @GetMapping
    public ResponseEntity<List<StudentDto>> findAll() {
        return studentService.findAll();
    }

    @PostMapping
    public ResponseEntity<List<StudentDto>> create(@RequestBody StudentDto studentDto) {
        return studentService.create(studentDto);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentDto> findById(@PathVariable Long studentId) {
        return studentService.findById(studentId);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> delete(@PathVariable Long studentId) {
        return studentService.delete(studentId);
    }
}
