package org.elvira.studentdeanury.client.kafka.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.elvira.studentdeanury.codogen.model.StudentDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaMessageListener {

    @KafkaListener(topics = "${app.kafka.kafkaStudentStatusServiceTopic}",
        groupId = "${app.kafka.kafkaMessageGroupId}",
        containerFactory = "studentStatusServiceConcurrentKafkaListenerContainerFactory")
    public void listen(@Payload StudentDto student,
                       @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) String key,
                       @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                       @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                       @Header(KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp) {
        log.info("Received message: {}", student);
        log.info("Key: {}; Patition: {}; Topic: {}; TimeStamp: {};", key, partition, topic, timestamp);

    }
}
