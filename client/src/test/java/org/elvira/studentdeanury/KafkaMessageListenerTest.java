package org.elvira.studentdeanury;

import org.elvira.studentdeanury.client.StudentDeaneryApplication;
import org.junit.jupiter.api.Test;
import org.openapitools.studentdeanery.api.StudentApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(classes = StudentDeaneryApplication.class)
@Testcontainers
public class KafkaMessageListenerTest {

    @Container
    static final KafkaContainer kafka = new KafkaContainer(
            DockerImageName.parse("confluentinc/cp-kafka:6.2.0")
    );

    @DynamicPropertySource
    static void registryKafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
    }

    @Autowired
    StudentApi studentApi;

    @Value("${app.kafka.kafkaMessageTopic}")
    private String topicName;

    @Test
    void contextLoads() {
        String bootstrapServers = kafka.getBootstrapServers();
        String topicName = "message-topic";

    }

}
