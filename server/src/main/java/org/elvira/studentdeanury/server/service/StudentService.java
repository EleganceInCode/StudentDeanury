package org.elvira.studentdeanury.server.service;

import lombok.NonNull;
import org.elvira.studentdeanury.server.dto.CreateStudentRequest;
import org.elvira.studentdeanury.server.dto.StudentResponse;

import java.util.List;

public interface StudentService {// удалить интерфейсы с единственной реализацией

    @NonNull
    List<StudentResponse> findAll();

    StudentResponse findById(@NonNull Long studentId);

    @NonNull
    StudentResponse createStudent(@NonNull CreateStudentRequest request);

    @NonNull
    StudentResponse update(@NonNull Long studentId, @NonNull CreateStudentRequest request);

    void delete(@NonNull Long studentId);
}
