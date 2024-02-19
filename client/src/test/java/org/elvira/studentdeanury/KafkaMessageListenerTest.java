package org.elvira.studentdeanury;

import org.elvira.studentdeanury.client.StudentDeaneryApplication;
import org.junit.jupiter.api.Test;
import org.openapitools.studentdeanery.api.StudentApi;
import org.openapitools.studentdeanery.model.StudentDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest(classes = StudentDeaneryApplication.class)
@Testcontainers
public class KafkaMessageListenerTest {

    @Container
    static final KafkaContainer kafka = new KafkaContainer(
            DockerImageName.parse("confluentinc/cp-kafka:7.3.3")
    );

    @DynamicPropertySource
    static void registryKafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
    }

    private KafkaTemplate<String, StudentDto> kafkaTemplate;

    private StudentApi studentApi;

    @Value("${app.kafka.kafkaMessageTopic}")
    private String topicName;

    @Test
    public void whenSendKafkaMessage_thenHandleMessageByListener() {
        StudentDto studentDto = new StudentDto();
        studentDto.setId(1L);
        studentDto.setLogin("elviraw");
        studentDto.setFirstName("Аннаева");
        studentDto.setMiddleName("Эльвира");
        studentDto.setLastName("Владимировна");
        studentDto.setAge(5);

        String key = UUID.randomUUID().toString();

        kafkaTemplate.send(topicName,key,studentDto);

        await()
                .pollInterval(Duration.ofSeconds(3))
                .atMost(10, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    ResponseEntity<StudentDto> mayBeKafkaMessage = studentApi.findById(1L);

                    assertThat(mayBeKafkaMessage).isIn();

                    StudentDto studentDto1 = mayBeKafkaMessage.getBody();

                    assertThat(studentDto1.getLogin().equals("elviraw"));
                    assertThat(studentDto1.getId().equals(1L));
                });
    }
}
