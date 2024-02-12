package org.openapitools.client.service;

import lombok.NonNull;
import org.openapitools.swaggerapi.model.Student;

import java.util.Optional;

public interface StudentService {

    @NonNull
    Optional<Student> findAll();

    @NonNull
    Optional<Student> findById(@NonNull Long studentId);

    void create(@NonNull Student request);

    @NonNull
    Student update(@NonNull Long studentId, @NonNull Student request);

    void delete(@NonNull Long studentId);

}
