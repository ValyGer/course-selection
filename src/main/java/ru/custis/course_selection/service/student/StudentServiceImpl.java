package ru.custis.course_selection.service.student;

import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import ru.custis.course_selection.dto.student.StudentDto;
import ru.custis.course_selection.dto.student.StudentInitDto;
import ru.custis.course_selection.dto.student.StudentMapping;
import ru.custis.course_selection.entity.Course;
import ru.custis.course_selection.entity.Student;
import ru.custis.course_selection.exception.DataConflictRequest;
import ru.custis.course_selection.exception.InvalidRequestException;
import ru.custis.course_selection.exception.NotFoundException;
import ru.custis.course_selection.exception.ResourceCurrentlyUnavailable;
import ru.custis.course_selection.repository.StudentRepository;
import ru.custis.course_selection.service.course.CourseService;
import ru.custis.course_selection.service.time_window.TimeWindowService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentMapping studentMapping;
    private final StudentRepository studentRepository;
    private final CourseService courseService;
    private final TimeWindowService timeWindowService;


    @Override
    public List<StudentDto> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        log.info("Получены все студент");
        return students.stream().map(studentMapping::studentToStudentDto).collect(Collectors.toList());
    }

    @Override
    public StudentDto getStudentById(Long studentId) {
        Student student = validStudentId(studentId);
        log.info("Студент с id = {} успешно найден", studentId);
        return studentMapping.studentToStudentDto(student);
    }

    @Override
    @Transactional
    public StudentDto createStudent(StudentInitDto studentInitDto) {
        Student student = studentMapping.studentInitDtoToStudent(studentInitDto);
        student.setCourses(new ArrayList<>());
        Student saveStudent = studentRepository.save(student);
        log.info("Новый студент добавлен, ему присвоено id = {}", saveStudent.getId());
        return studentMapping.studentToStudentDto(saveStudent);
    }

    @Override
    @Transactional
    public StudentDto updateStudent(Long studentId, StudentInitDto studentInitDto) {

        Student student = validStudentId(studentId);
        if (!studentInitDto.getFirstname().isBlank()) {
            student.setFirstname(studentInitDto.getFirstname());
        } else {
            throw new InvalidRequestException("Поле имя не должно быть пустым");
        }
        if (!studentInitDto.getLastname().isBlank()) {
            student.setLastname(studentInitDto.getLastname());
        } else {
            throw new InvalidRequestException("Поле фамилия не должно быть пустым");
        }
        Student saveStudent = studentRepository.save(student);
        log.info("Студент с id = {} успешно обновлен", studentId);
        return studentMapping.studentToStudentDto(saveStudent);
    }

    @Transactional
    @Override
    public void deleteStudent(Long studentId) {
        validStudentId(studentId);
        studentRepository.deleteById(studentId);
        log.info("Студент id = {} удален успешно", studentId);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.PESSIMISTIC_READ)
    public StudentDto registrationOnCourse(Long studentId, Long courseId) {
        Student student = validStudentId(studentId);
        Course course = courseService.getCourseByIdForStudent(courseId);

        if (timeWindowService.isUnavailableTime(course, LocalDateTime.now())) {
            throw new ResourceCurrentlyUnavailable("В настоящее время запись на данный курс недоступна.");
        }
        if (course.getLimitPerson() == course.getStudents().size()) {
            throw new DataConflictRequest("Достигнут предел по числу слушателей. Мест на курсе больше нет.");
        }
        if (course.getStudents().contains(student)) {
            throw new DataConflictRequest("Студент не может записаться на один и тот же курс дважды.");
        }

        student.getCourses().add(course);
        Student saveStudent = studentRepository.save(student);

        return studentMapping.studentToStudentDto(saveStudent);
    }

    @Override
    @Transactional()
    public StudentDto leaveCourse(Long studentId, Long courseId) {
        Student student = validStudentId(studentId);
        Course course = courseService.getCourseByIdForStudent(courseId);

        if (!course.getStudents().contains(student)) {
            throw new DataConflictRequest("Студент не может отписаться от курса на который не записан");
        }
        student.getCourses().remove(course);
        Student saveStudent = studentRepository.save(student);

        return studentMapping.studentToStudentDto(saveStudent);
    }

    private Student validStudentId(Long studentId) {
        Optional<Student> student = studentRepository.findById(studentId);
        if (student.isPresent()) {
            return student.get();
        } else {
            throw new NotFoundException("Студент с id = " + studentId + " не найден");
        }
    }
}