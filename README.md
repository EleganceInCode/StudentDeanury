# Описание проекта "StudentDeanury"

## Общая информация

Проект "StudentDeanury" — это микросервисное приложение, состоящее из клиентской и серверной частей, которые взаимодействуют друг с другом через брокер сообщений Kafka и развертываются с помощью Docker. Главная цель системы — автоматизация процессов студенческого деканата.

## Архитектура

- Клиентское приложение: Предоставляет API для работы с пользовательским интерфейсом и отправляет сообщения в Kafka.
- Серверное приложение: Обрабатывает сообщения из Kafka, взаимодействует с базой данных и предоставляет данные через REST API.
- Kafka: Используется как центральный брокер сообщений для асинхронного обмена данными между клиентом и сервером.
- Docker: Обеспечивает контейнеризацию и изоляцию приложений, упрощая их развертывание и масштабирование.

## Настройки

### Клиентское приложение

```yaml
server.port=8080
spring.kafka.bootstrap-servers=localhost:9092
app.kafka.kafkaMessageGroupId="kafka-message-group-id"
app.kafka.kafkaMessageTopic="message-topic"

spring:
  autoconfigure:
    exclude: org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
  jackson:
    date-format: org.openapitools.swaggerapi.utils.RFC3339DateFormat
    serialization:
      write-dates-as-timestamps: false

logging:
  level:
    org: ERROR
    org.springdoc: DEBUG
```  
# Серверное приложение

```yaml
server.port=8081

spring.jpa.hibernate.ddl-auto=update
spring.jpa.generate-ddl=true
spring.jpa.show-sql=true
spring.datasource.url=jdbc:postgresql://localhost:5433/postgres_db
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.username=postgres
spring.datasource.password=deanery-server

spring.kafka.bootstrap-servers=localhost:9092
app.kafka.kafkaMessageGroupId="kafka-message-group-id"
app.kafka.kafkaMessageTopic="message-topic"

spring:
  autoconfigure:
    exclude: org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
  jackson:
    serialization:
      write-dates-as-timestamps: false
```
## Кодогенерация

В проекте реализована автоматизация создания контрактов API с использованием Swagger-UI на основе спецификаций OpenAPI. Это позволяет генерировать клиентский и серверный код автоматически, облегчая разработку и поддержку.

## Развертывание

Использование Docker позволяет легко развертывать и масштабировать приложения, а также обеспечивает их изоляцию и безопасность. Kafka, запущенный в контейнере Docker, упрощает настройку и интеграцию брокера сообщений в архитектуру системы.

## Запуск проекта

Для запуска необходимо выполнить следующие шаги:

1. Установить и настроить Docker.
2. Клонировать репозиторий проекта.
3. Запустить Kafka с использованием Docker.
4. Собрать и запустить клиентское и серверное приложения с помощью Docker-compose или вручную.

## Взаимодействие с приложением

После запуска приложений доступ к ним можно получить через REST API, документация к которому доступна через Swagger-UI. Это интерфейс упрощает тестирование API и интеграцию с другими системами.

## Обзор
Клиентское приложение создано на основе проекта [OpenAPI Generator](https://openapi-generator.tech), который позволяет автоматически генерировать сервер на основе OpenAPI спецификаций. Проект демонстрирует, как можно легко создать сервер, поддерживающий OpenAPI, на Java с использованием фреймворка Spring Boot.

Используется библиотека [springdoc](https://springdoc.org) для интеграции OpenAPI с Spring Boot. Благодаря этому, на основе созданных классов контроллеров и моделей, генерируется спецификация OpenAPI версии 3.

Спецификацию можно скачать или просмотреть, перейдя по ссылке:
- Для скачивания спецификации: http://localhost:8080/v3/api-docs/
- Для просмотра в UI: http://localhost:8080/swagger-ui.html
