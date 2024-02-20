package org.elvira.studentdeanury.server.kafka.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elvira.studentdeanury.server.dto.CreateStudentRequest;
import org.elvira.studentdeanury.server.dto.StudentResponse;
import org.elvira.studentdeanury.server.enums.Status;
import org.elvira.studentdeanury.server.service.StudentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaMessageListener {
    @Value("${app.kafka.kafkaStudentStatusServiceTopic}")
    private String kafkaStudentStatusServiceTopic;
    private final KafkaTemplate<String, StudentResponse> template;
    @KafkaListener(topics = "${app.kafka.kafkaMessageTopic}",
        groupId = "${app.kafka.kafkaMessageGroupId}",
        containerFactory = "studentStatusServiceConcurrentKafkaListenerContainerFactory")
    public void listen() {
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setStatus(Status.values()[new Random().nextInt(3)].toString());
        studentResponse.setDate(Instant.now().truncatedTo(ChronoUnit.SECONDS));
        template.send(kafkaStudentStatusServiceTopic,studentResponse);

    }
}
