package org.elvira.studentdeanury.client.controller;

import lombok.RequiredArgsConstructor;
import org.elvira.studentdeanury.client.service.StudentService;

import org.elvira.studentdeanury.codegen.api.StudentApi;
import org.elvira.studentdeanury.codegen.model.CreateStudentResponse;
import org.elvira.studentdeanury.codegen.model.StudentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; // todo звездочку нельзя импортить
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController implements StudentApi {

    private final StudentService studentService;

    @Override
    public Optional<NativeWebRequest> getRequest() { // todo и тут что-то странное. зачем этот метод?
        return StudentApi.super.getRequest();
    }

    @Override
    public ResponseEntity<CreateStudentResponse> create(StudentDto studentDto) {
        studentService.create(studentDto);
        return ResponseEntity.ok().body(new CreateStudentResponse().message("Сообщение улетело в кафку")); // todo такой же ответ можно сделать и для удаления и изменения
    }

    @Override
    public ResponseEntity<Void> delete(UUID studentId) {
        StudentDto studentToDelete = new StudentDto();
        studentToDelete.setId(studentId);
        studentService.delete(studentToDelete); // todo ну такооое себе. ну ладно, можно оставить
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<StudentDto>> findAll() {
        return StudentApi.super.findAll();
    } // todo что-то странное

    @Override
    public ResponseEntity<StudentDto> findById(UUID studentId) {
        return StudentApi.super.findById(studentId);
    } // todo и тут
}
