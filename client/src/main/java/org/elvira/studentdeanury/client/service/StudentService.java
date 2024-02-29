package org.elvira.studentdeanury.client.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.elvira.studentdeanury.codegen.model.ActionEnum;
import org.elvira.studentdeanury.codegen.model.StudentDto;
import org.elvira.studentdeanury.codegen.model.StudentModificationActionDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class StudentService {

    @Value("${app.kafka.kafkaMessageTopic}")
    private final String topicName;
    private final KafkaTemplate<String, StudentModificationActionDto> kafkaTemplate;

    public void create(@NonNull StudentDto student) {
        log.info("createStudent method called");
        kafkaTemplate.send(topicName, new StudentModificationActionDto(student, ActionEnum.CREATE));
    }

    public void update(@NonNull UUID studentId, @NonNull StudentDto student) {
        log.info("updateStudent method called");
        if(studentId.equals(student.getId())) {
            kafkaTemplate.send(topicName, new StudentModificationActionDto(student, ActionEnum.UPDATE));
        } else {
            log.error("Student ID from path and body do not mach");
            throw new IllegalArgumentException("Student ID from path and body do not mach");
        }
    }

    public void delete(UUID studentId) {
        log.info("deleteStudent method called");
        StudentDto saveId = new StudentDto();
        saveId.setId(studentId);
        kafkaTemplate.send(topicName, new StudentModificationActionDto(saveId, ActionEnum.DELETE));
    }

    public void findAll() {
        log.info("findAll method called");
        kafkaTemplate.send(topicName, new StudentModificationActionDto(new StudentDto(),ActionEnum.FIND_ALL));
    }

    public void findById(UUID studentId) {
        log.info("findByIdStudent method called");
        StudentDto saveId = new StudentDto();
        saveId.setId(studentId);
        kafkaTemplate.send(topicName, new StudentModificationActionDto(saveId, ActionEnum.DELETE));
    }
}
