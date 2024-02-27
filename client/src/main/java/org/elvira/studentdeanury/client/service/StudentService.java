package org.elvira.studentdeanury.client.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import org.elvira.studentdeanury.codegen.model.StudentDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StudentService {
    private final String topicName;
    private final KafkaTemplate<String, StudentDto> kafkaTemplate;
    // верни AllArgsConstructor и положи в корень проекта lombok.config, чтобы @Value прокидывалось
    public StudentService(@Value("${app.kafka.kafkaMessageTopic}")String topicName, KafkaTemplate<String, StudentDto> kafkaTemplate) {
        this.topicName = topicName;
        this.kafkaTemplate = kafkaTemplate;
    }

    private void sendMessage(StudentDto student) {
        log.info("Sending message to Kafka topic: {}", topicName);
        kafkaTemplate.send(topicName,student);
    }

    public void create(@NonNull StudentDto student) {
        log.info("createStudent method called");
        sendMessage(student);
    }

    public void update(@NonNull StudentDto student) {
        log.info("updateStudent method called");
        sendMessage(student);
    }

    public void delete(@NonNull StudentDto student) {
        log.info("deleteStudent method called");
        sendMessage(student);
    }
// todo чем создание отличается от удаления и изменения? можно передавать тип действия вместе со студентом
    // создай в кодгене StudentModificationActionDto. в нем будет студент и тип действия (енум: CREATE, UPDATE, DELETE)
    // передавай его в топик, а на принимающей стороне (на сервере) по типу действия будем понимать что делать
    // в итоге 24-я строка у тебя будет kafkaTemplate.send(topicName, new StudentModificationActionDto(ActionType.CREATE, student));
}
