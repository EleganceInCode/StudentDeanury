package org.openapitools.server.service;

import lombok.NonNull;
import org.openapitools.server.controller.dto.CreateStudentRequest;
import org.openapitools.server.controller.dto.StudentResponse;

import java.util.List;

public interface StudentService {

    @NonNull
    List<StudentResponse> findAll();

    StudentResponse findById(@NonNull Long studentId);

    @NonNull
    StudentResponse createStudent(@NonNull CreateStudentRequest request);

    @NonNull
    StudentResponse update(@NonNull Long studentId, @NonNull CreateStudentRequest request);

    void delete(@NonNull Long studentId);
}
