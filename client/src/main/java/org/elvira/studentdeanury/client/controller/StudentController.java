package org.elvira.studentdeanury.client.controller;

import lombok.RequiredArgsConstructor;
import org.elvira.studentdeanury.client.service.StudentService;
import org.elvira.studentdeanury.codegen.api.StudentApi;
import org.elvira.studentdeanury.codegen.model.CreateStudentResponse;
import org.elvira.studentdeanury.codegen.model.StudentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;//todo опять импорты

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController implements StudentApi {

    private final StudentService studentService;

    @Override
    public ResponseEntity<CreateStudentResponse> create(StudentDto studentDto) {
        studentService.create(studentDto);
        return ResponseEntity.ok().body(new CreateStudentResponse().message("Сообщение улетело в кафку," +
                " а Dto на сервер для создания студента"));//todo используй либо multiline string, либо "".formatted()
    }

    @Override
    public ResponseEntity<CreateStudentResponse> update(UUID studentId, StudentDto studentDto) {
        studentService.update(studentId, studentDto);
        return ResponseEntity.ok().body(new CreateStudentResponse().message("Сообщение улетело в кафку для обновления студента"));
    }

    @Override
    public ResponseEntity<CreateStudentResponse> delete(UUID studentId) {
        studentService.delete(studentId);
        return ResponseEntity.ok().body(new CreateStudentResponse().message("Сообщение улетело в кафку, " +
                "а Id на сервер для удаления id: "+ studentId));
    }

    @Override
    public ResponseEntity<CreateStudentResponse> findAll() {
        studentService.findAll();
        return ResponseEntity.ok().body(new CreateStudentResponse().message("Сообщение улетело в кафку для поиска всех студентов"));
    }

    @Override
    public ResponseEntity<CreateStudentResponse> findById(UUID studentId) {
        studentService.findById(studentId);
        return ResponseEntity.ok().body(new CreateStudentResponse().message("Сообщение улетело в кафку с id: " +
                studentId + " для поиска студента по id"));
    }
}
