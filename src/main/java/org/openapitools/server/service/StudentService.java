package org.openapitools.server.service;

import lombok.NonNull;
import org.openapitools.server.model.Student;

import java.util.Optional;

public interface StudentService {

    @NonNull
    Optional<Student> findAll();

    @NonNull
    Optional<Student> findById(@NonNull Long studentId);

    @NonNull
    void create(@NonNull Student request);

    @NonNull
    Student update(@NonNull Long studentId, @NonNull Student request);

    void delete(@NonNull Long studentId);

}
