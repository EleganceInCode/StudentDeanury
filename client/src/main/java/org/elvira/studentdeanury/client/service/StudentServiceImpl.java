package org.elvira.studentdeanury.client.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.test.StudentDto;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;


@Service
@AllArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {

    //    private final List<StudentDto> message = new CopyOnWriteArrayList<>();
    private final KafkaTemplate<String, StudentDto> kafkaTemplate;
    private final MessageListener<String, StudentDto> messageListener;

    @Override
    public @NonNull Optional<StudentDto> findAll() {
        log.info("find all method called");

        return Optional.of((StudentDto) message);
    }

    @Override
    public @NonNull Optional<StudentDto> findById(@NonNull Long studentId) {
        log.info("findById method called");

        return message.stream().filter(it -> it.getId().equals(studentId)).findFirst();
    }

    @Override
    public StudentDto create(@NonNull StudentDto student) {
        log.info("createStudent method called");
//        message.add(student);
        CompletableFuture<SendResult<String, StudentDto>> future = kafkaTemplate.send("topicName", student);
        future.get();

        messageListener.onMessage();


        return /// student;
    }

    @Override
    public @NonNull StudentDto update(@NonNull Long studentId, @NonNull StudentDto request) {
        log.info("update method called");
        StudentDto updateStudent = findById(studentId).orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));
        updateStudent.setFirstName(request.getFirstName());
        updateStudent.setLastName(request.getLastName());
        updateStudent.setAge(request.getAge());

        return updateStudent;
    }

    @Override
    public void delete(@NonNull Long studentId) {
        log.info("delete method called");
        message.removeIf(student -> student.getId().equals(studentId));
    }

}
