package org.openapitools.client.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.swaggerapi.model.Student;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;


@Service
@AllArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {

    private final List<Student> message = new CopyOnWriteArrayList<>();

    @Override
    public @NonNull Optional<Student> findAll() {
        log.info("find all method called");

        return Optional.of((Student) message);
    }

    @Override
    public @NonNull Optional<Student> findById(@NonNull Long studentId) {
        log.info("findById method called");

        return message.stream().filter(it -> it.getId().equals(studentId)).findFirst();
    }

    @Override
    public void create(@NonNull Student student) {
        log.info("createStudent method called");
        message.add(student);

    }

    @Override
    public @NonNull Student update(@NonNull Long studentId, @NonNull Student request) {
        log.info("update method called");
        Student updateStudent = findById(studentId).orElseThrow(()-> new RuntimeException("Student not found with id: " + studentId));
        updateStudent.setFirstName(request.getFirstName());
        updateStudent.setLastName(request.getLastName());
        updateStudent.setAge(request.getAge());

        return updateStudent;
    }

    @Override
    public void delete(@NonNull Long studentId) {
        log.info("delete method called");
        message.removeIf(student -> student.getId().equals(studentId));
    }

}