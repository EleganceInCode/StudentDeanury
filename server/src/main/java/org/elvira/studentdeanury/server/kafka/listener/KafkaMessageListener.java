package org.elvira.studentdeanury.server.kafka.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.elvira.studentdeanury.codogen.model.CreateStudentResponse;
import org.elvira.studentdeanury.server.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaMessageListener {

    private final StudentService studentService;

    @Value("${app.kafka.kafkaStudentStatusServiceTopic}")
    private String kafkaStudentStatusServiceTopic;
    private final KafkaTemplate<String, CreateStudentResponse> template;
    @KafkaListener(topics = "${app.kafka.kafkaMessageTopic}",
            groupId = "${app.kafka.kafkaMessageGroupId}",
            containerFactory = "studentStatusServiceConcurrentKafkaListenerContainerFactory")



    public void listen(
            // todo тут нам приходит наш студент
            //StudentDto studentDto

    ) {
//studentService.createStudent();
        //todo сохранение студента в БД
    }
}
