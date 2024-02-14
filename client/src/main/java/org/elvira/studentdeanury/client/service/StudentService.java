package org.elvira.studentdeanury.client.service;

import lombok.NonNull;
import org.test.StudentDto;

import java.util.Optional;

public interface StudentService {

    @NonNull
    Optional<StudentDto> findAll();

    @NonNull
    Optional<StudentDto> findById(@NonNull Long studentId);

    StudentDto create(@NonNull StudentDto request);

    @NonNull
    StudentDto update(@NonNull Long studentId, @NonNull StudentDto request);

    void delete(@NonNull Long studentId);

}
