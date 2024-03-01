package org.elvira.studentdeanury.server.service.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elvira.studentdeanury.codegen.model.StudentModificationActionDto;
import org.elvira.studentdeanury.server.service.StudentService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaMessageListener {

    private final StudentService studentService;
    @KafkaListener(topics = "${app.kafka.kafkaMessageTopic}",
            groupId = "${app.kafka.kafkaMessageGroupId}",
            containerFactory = "studentKafkaListenerContainerFactory")
    public void listen(@Payload StudentModificationActionDto student,
                       @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) String key,
                       @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                       @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                       @Header(KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp) {
        try {
            log.info("Received Kafka message: {} , key: {}, topic: {}, partition: {}, timestamp: {} ",
                    student, key, topic, partition, timestamp);
            switch (student.getAction()) {
                case CREATE -> studentService.createStudent(student.getStudentDto());
                case UPDATE -> studentService.update(student.getStudentDto().getId(), student.getStudentDto());
                case DELETE -> studentService.delete(student.getStudentDto().getId());
                case FIND_ALL -> studentService.findAll();
                case FIND_BY_ID -> studentService.findById(student.getStudentDto().getId());
            }

        } catch (Exception e) {
            log.error("Error processing Kafka message", e);
        }
    }
}
