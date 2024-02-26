package org.elvira.studentdeanury.server.kafka.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.elvira.studentdeanury.codegen.model.StudentDto;
import org.elvira.studentdeanury.server.service.StudentService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaMessageListener {

    private final StudentService studentService;
    @KafkaListener(topics = "${app.kafka.kafkaMessageTopic}",
            groupId = "${app.kafka.kafkaMessageGroupId}",
            containerFactory = "studentKafkaListenerContainerFactory")
    public void listen(@Payload StudentDto studentDto,
                       @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) String key,
                       @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                       @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                       @Header(KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp) {
        try {
            log.info("Received Kafka message: {} , key: {}, " +
                            "topic: {}, partition: {}, timestamp: {} ",
                    studentDto, key, topic, partition, timestamp);
            studentService.createStudent(studentDto);
        } catch (Exception e) {
            log.error("Ошибка при обработке сообщения Kafka", e);
        }

    }
}
