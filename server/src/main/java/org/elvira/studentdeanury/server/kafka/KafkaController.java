package org.elvira.studentdeanury.server.kafka;

import lombok.RequiredArgsConstructor;
import org.elvira.studentdeanury.server.dto.CreateStudentRequest;
import org.elvira.studentdeanury.server.dto.StudentResponse;
import org.elvira.studentdeanury.server.service.StudentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api/v1/kafka")
@RequiredArgsConstructor
public class KafkaController {

    @Value("${app.kafka.kafkaMessageTopic}")
    private String topicName;

    private final KafkaTemplate<String, StudentResponse> kafkaTemplate;

    private final StudentService studentService;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody StudentResponse student) {
        kafkaTemplate.send(topicName,student);

        return ResponseEntity.ok("Message sent to kafka");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<StudentResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(Optional.ofNullable(studentService.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponse> update(@PathVariable Long id, @RequestBody CreateStudentRequest student) {
        StudentResponse updateStudent = studentService.update(id, student);
        kafkaTemplate.send("student-updated", updateStudent);

        return ResponseEntity.ok(updateStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id, @RequestBody StudentResponse student) {
        studentService.delete(id);
        kafkaTemplate.send("student-deleted",student);
        return ResponseEntity.noContent().build();
    }
}
