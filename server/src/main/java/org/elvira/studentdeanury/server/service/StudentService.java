package org.elvira.studentdeanury.server.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elvira.studentdeanury.codegen.model.StudentDto;
import org.elvira.studentdeanury.codegen.model.SubjectDto;
import org.elvira.studentdeanury.server.repository.StudentRepository;
import org.elvira.studentdeanury.server.repository.SubjectRepository;
import org.elvira.studentdeanury.server.repository.dao.StudentModel;
import org.elvira.studentdeanury.server.repository.dao.SubjectModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    private final SubjectRepository subjectRepository;

    @Transactional
    public void createStudent(@NonNull StudentDto studentDto) {
        log.info("Начало создания студента: {}", studentDto);

        if (studentDto.getSubjects() != null) {
            for (SubjectDto subjectDto : studentDto.getSubjects()) {
                SubjectModel subjectModel = subjectRepository.findBySubjectName(subjectDto.getSubjectName()).orElse(null);
                if (subjectModel == null) {// todo не надо писать orElse(null). возвращаешь optional и тут проверяешь Optional.ifPresent
                    subjectModel = new SubjectModel().convertToSubjectModel(subjectDto);
                    subjectRepository.save(subjectModel);
                }
            }
        }

        StudentModel student = buildStudentRequest(studentDto);
        StudentDto studentResponse = buildStudentResponse(Objects.requireNonNull(studentRepository).save(student));// todo зачем нужен Objects.requireNonNull? убрать
        log.info("Студент успешно создан: {}", studentResponse);
    }
// todo не надо публичным делать такие методы
    public static SubjectDto converToSubjectDto(SubjectModel subjectModel) {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setSubjectName(subjectModel.getSubjectName());
        return subjectDto;
    }

    @NonNull
    private StudentDto buildStudentResponse(StudentModel student) {

        StudentDto studentDto = new StudentDto();
        studentDto.setLogin(student.getLogin());
        studentDto.setFirstName(student.getFirstName());
        studentDto.setMiddleName(student.getMiddleName());
        studentDto.setLastName(student.getLastName());
        studentDto.setAge(student.getAge());

        Set<SubjectDto> subjectsDto = new HashSet<>();
        for (SubjectModel subjectModel : student.getSubjectDao()) {
            SubjectDto subjectDto = converToSubjectDto(subjectModel);
            subjectsDto.add(subjectDto);
        }
        studentDto.setSubjects(subjectsDto);

        return studentDto;
    }

    @NonNull
    private StudentModel buildStudentRequest(StudentDto studentDto) {
        StudentModel student = new StudentModel()
                .setLogin(studentDto.getLogin())
                .setFirstName(studentDto.getFirstName())
                .setMiddleName(studentDto.getMiddleName())
                .setLastName(studentDto.getLastName())
                .setAge(studentDto.getAge());

        Set<SubjectDto> subjectsDto = student.getSubjectDao().stream()// todo тут список будет пустой, перепутала видимо. в модель студента добавляешь модели предметов, а не в дто
                .map(StudentService::converToSubjectDto)
                .collect(Collectors.toSet());
        studentDto.setSubjects(subjectsDto);

        if (studentDto.getSubjects() != null) {
            Set<SubjectModel> subjects = studentDto.getSubjects().stream()
                    .map(dto -> Objects.requireNonNull(subjectRepository).findBySubjectName(dto.getSubjectName()))// todo зачем нужен Objects.requireNonNull? убрать
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());
            student.setSubjectDao(subjects);
        }
        return student;
    }
}
// todo комментарии пора убрать

//    private void studentUpdate(StudentModel student, StudentDto studentDto) {
//        ofNullable(studentDto.getLogin()).map(student::setLogin);
//        ofNullable(studentDto.getFirstName()).map(student::setFirstName);
//        ofNullable(studentDto.getMiddleName()).map(student::setMiddleName);
//        ofNullable(studentDto.getLastName()).map(student::setLastName);
//        ofNullable(studentDto.getAge()).map(student::setAge);


//        Set<CreateSubjectRequest> subjectRequests = request.getSubjects();
//        if (subjectRequests != null) {
//            Set<SubjectModel> subjects = new HashSet<>();
//            for (CreateSubjectRequest subjectRequest: subjectRequests ) {
//                subjects.add((new SubjectModel().setName(subjectRequest.getName())));
//            }
//        }


//    @Transactional(readOnly = true)
//    public @NonNull List<CreateStudentResponse> findAll() {
//        log.info("Список всех студентов");
//        return Objects.requireNonNull(studentRepository)
//                .findAll()
//                .stream()
//                .map(this::buildStudentResponse)
//                .collect(Collectors.toList());
//    }
//
//    @Transactional(readOnly = true)
//    public CreateStudentResponse findById(@NonNull Long studentId) {
//        log.info("Данные студента под ID: " + studentId);
//        return Objects.requireNonNull(studentRepository)
//                .findById(studentId)
//                .map(this::buildStudentResponse)
//                .orElseThrow(() -> new EntityNotFoundException("Cтудент  " + studentId + " не найден"));
//    }
    //    @Transactional
//    public @NonNull CreateStudentResponse update(@NonNull Long studentId, @NonNull CreateStudentResponse response) {
//        log.info("Данные студента обновлены под ID: " + studentId);
//        StudentModel student = Objects.requireNonNull(studentRepository)
//                .findById(studentId)
//                .orElseThrow(() -> new EntityNotFoundException("Студент " + studentId + " не найден"));
//        studentUpdate(student, response);
//        return buildStudentResponse(studentRepository.save(student));
//    }
//
//    @Transactional
//    public void delete(@NonNull Long studentId) {
//        log.info("Студент удален под id: " + studentId);
//        Objects.requireNonNull(studentRepository)
//                .deleteById(studentId);
//    }

