package org.elvira.studentdeanury.client.kafka;

import lombok.RequiredArgsConstructor;
import org.elvira.studentdeanury.client.service.StudentService;

import org.openapitools.api.StudentApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.test.StudentDto;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/kafka")
@RequiredArgsConstructor
public class KafkaController implements StudentApi {// todo rename to StudentsController

    @Value("${app.kafka.kafkaMessageTopic}")
    private String topicName;

    private final StudentService studentService;

    @Override
    public ResponseEntity<StudentDto> create(StudentDto studentDto) {
        StudentDto result = studentService.create(studentDto);
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<Optional<StudentDto>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDto> update(@PathVariable Long id, @RequestBody StudentDto student) {
        StudentDto updateStudent = studentService.update(id, student);
//        kafkaTemplate.send("student-updated", updateStudent);

        return ResponseEntity.ok(updateStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id, @RequestBody StudentDto student) {
        studentService.delete(id);
//        kafkaTemplate.send("student-deleted",student);
        return ResponseEntity.noContent().build();
    }
}
