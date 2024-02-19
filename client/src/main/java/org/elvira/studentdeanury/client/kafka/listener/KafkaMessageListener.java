package org.elvira.studentdeanury.client.kafka.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.studentdeanery.api.StudentApi;
import org.openapitools.studentdeanery.model.StudentDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaMessageListener {

    private final StudentApi studentService;

    @KafkaListener(topics = "${app.kafka.kafkaMessageTopic}",
        groupId = "${app.kafka.kafkaMessageGroupId}",
        containerFactory = "kafkaListenerContainerFactory")
    public void listen(@Payload StudentDto student,
                       @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) UUID key,
                       @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                       @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                       @Header(KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp) {
        log.info("Received message: {}", student);
        log.info("Key: {}; Patition: {}; Topic: {}; TimeStamp: {};", key, partition, topic, timestamp);

        studentService.create(student);

    }
}
