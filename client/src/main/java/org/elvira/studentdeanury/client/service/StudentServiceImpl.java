package org.elvira.studentdeanury.client.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import org.openapitools.studentdeanery.api.StudentApi;
import org.openapitools.studentdeanery.model.StudentDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;


import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Slf4j
public class StudentServiceImpl implements StudentApi {
    private final KafkaTemplate<String, StudentDto> kafkaTemplate;
    private final List<StudentDto> message = new CopyOnWriteArrayList<>();
    private final String topicName;

    public StudentServiceImpl(@Value("${app.kafka.kafkaMessageTopic}")String topicName, KafkaTemplate<String, StudentDto> kafkaTemplate) {
        this.topicName = topicName;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public ResponseEntity<List<StudentDto>> findAll() {
        log.info("find all method called");

        return ResponseEntity.ok(message);
    }


    @Override
    public ResponseEntity<StudentDto> findById(Long studentId) {
        return message.stream()
                .filter(st -> st.getId().equals(studentId))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }


    @Override
    public Optional<NativeWebRequest> getRequest() {
        return StudentApi.super.getRequest();
    }

    @Override
    public ResponseEntity<Void> delete(Long studentId) {
        log.info("delete method called");
        boolean isRemove = message.removeIf(student -> false);
        if(isRemove) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @Override
    public ResponseEntity<List<StudentDto>> create(@NonNull StudentDto student) {
        log.info("createStudent method called");
        message.add(student);
        kafkaTemplate.send(topicName, student);

        return ResponseEntity.ok(Collections.singletonList(student));
    }

//    @Override
//    public @NonNull StudentDto update(@NonNull Long studentId, @NonNull StudentDto request) {
//        log.info("update method called");
//        StudentDto updateStudent = findById(studentId).orElseThrow(()-> new RuntimeException("Student not found with id: " + studentId));
//        updateStudent.setFirstName(request.getFirstName());
//        updateStudent.setLastName(request.getLastName());
//        updateStudent.setAge(request.getAge());
//
//        return updateStudent;
//    }


}
