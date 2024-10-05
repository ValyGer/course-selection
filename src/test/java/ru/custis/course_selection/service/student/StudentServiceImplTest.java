package ru.custis.course_selection.service.student;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.custis.course_selection.dto.student.StudentDto;
import ru.custis.course_selection.dto.student.StudentInitDto;
import ru.custis.course_selection.dto.student.StudentMappingImpl;
import ru.custis.course_selection.entity.Course;
import ru.custis.course_selection.entity.Student;
import ru.custis.course_selection.exception.DataConflictRequest;
import ru.custis.course_selection.exception.NotFoundException;
import ru.custis.course_selection.repository.StudentRepository;
import ru.custis.course_selection.service.course.CourseService;
import ru.custis.course_selection.service.time_window.TimeWindowServiceImpl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @InjectMocks
    private StudentServiceImpl studentService;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseService courseService;

    @Mock
    private StudentMappingImpl studentMappingImpl;

    @Mock
    private TimeWindowServiceImpl timeWindowService;

    @Test
    void getAllStudents_Successful() {
        Student firstStudent = new Student(0L, "Иван", "Иванов", new ArrayList<>());
        Student secondStudent = new Student(1L, "Петр", "Петров", new ArrayList<>());

        StudentDto firstStudentDto = new StudentDto("Иван", "Иванов", new ArrayList<>());
        StudentDto secondStudentDto = new StudentDto("Петр", "Петров", new ArrayList<>());

        when(studentRepository.findAll()).thenReturn(List.of(firstStudent, secondStudent));
        when(studentMappingImpl.studentToStudentDto(firstStudent)).thenReturn(firstStudentDto);
        when(studentMappingImpl.studentToStudentDto(secondStudent)).thenReturn(secondStudentDto);


        List<StudentDto> saveListStudentDto = studentService.getAllStudents();

        verify(studentRepository, times(1)).findAll();
        assertThat(firstStudentDto, equalTo(saveListStudentDto.get(0)));
        assertThat(secondStudentDto, equalTo(saveListStudentDto.get(1)));
    }

    @Test
    void getStudentById_whenIdFound_ThenReturnStudent() {
        Long studentId = 0L;
        Student student = new Student();
        StudentDto studentDto = new StudentDto();
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));
        when(studentMappingImpl.studentToStudentDto(any(Student.class))).thenReturn(studentDto);

        StudentDto saveStudentDto = studentService.getStudentById(0L);

        verify(studentRepository, times(1)).findById(studentId);
        assertThat(studentDto, equalTo(saveStudentDto));
    }

    @Test
    void getStudentById_whenIdNotFound_ThenReturnException() {

        assertThrows(NotFoundException.class,
                () -> studentService.getStudentById(any(Long.class)));
    }

    @Test
    void createStudent_whenSuccessful_ThenReturnStudent() {
        Student student = new Student();
        StudentDto studentDto = new StudentDto();

        when(studentMappingImpl.studentInitDtoToStudent(any(StudentInitDto.class))).thenReturn(student);
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentMappingImpl.studentToStudentDto(any(Student.class))).thenReturn(studentDto);
        ;


        StudentDto saveStudentDto = studentService.createStudent(new StudentInitDto());

        assertThat(saveStudentDto, equalTo(studentDto));
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void updateStudent_whenSuccessful_ThenReturnCourse() {
        StudentInitDto studentInitDto = new StudentInitDto("Иван", "Иванов");
        Student student = new Student(0L, "Игорь", "Иванов", new ArrayList<>());
        Student studentNew = new Student(0L, "Иван", "Иванов", new ArrayList<>());
        StudentDto studentDtoNew = new StudentDto("Иван", "Иванов", new ArrayList<>());


        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(studentNew);
        when(studentMappingImpl.studentToStudentDto(any(Student.class))).thenReturn(studentDtoNew);
        ;

        StudentDto saveStudentDto = studentService.updateStudent(0L, studentInitDto);

        assertThat(saveStudentDto, equalTo(studentDtoNew));
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void deleteStudent_whenSuccessful() {
        Student student = new Student();
        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        studentService.deleteStudent((any(Long.class)));

        verify(studentRepository, times(1)).deleteById(any(Long.class));
    }

    @Test
    void deleteStudent_whenNotSuccessful_ThenReturnException() {

        assertThrows(NotFoundException.class,
                () -> studentService.deleteStudent(any(Long.class)));
    }

    @Test
    void registrationOnCourse_whenSuccessful() {
        Course course = new Course(0L, "title", 10L, null, null, new ArrayList<>());
        Student student = new Student(0L, "Иван", "Иванов", new ArrayList<>());
        Student studentNew = new Student(0L, "Иван", "Иванов", List.of(course));
        StudentDto studentDto = new StudentDto("Иван", "Иванов", List.of(course.getTitle()));

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));
        when(courseService.getCourseByIdForStudent(any(Long.class))).thenReturn(course);
        when(studentRepository.save(any(Student.class))).thenReturn(studentNew);
        when(studentMappingImpl.studentToStudentDto(any(Student.class))).thenReturn(studentDto);

        StudentDto saveStudentDto = studentService.registrationOnCourse(0L, 0L);

        assertThat(saveStudentDto, equalTo(studentDto));
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void registrationOnCourse_whenNotAvailablePlaces_ThenReturnException() {
        Student firstStudent = new Student(0L, "Иван", "Иванов", new ArrayList<>());
        Student secondStudent = new Student(1L, "Петр", "Петров", new ArrayList<>());
        Course course = new Course(0L, "title", 1L, null, null, List.of(secondStudent));
        secondStudent.setCourses(List.of(course));

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(firstStudent));
        when(courseService.getCourseByIdForStudent(any(Long.class))).thenReturn(course);

        assertThrows(DataConflictRequest.class,
                () -> studentService.registrationOnCourse(0L, 0L));
    }

    @Test
    void registrationOnCourse_whenResourceAvailable_ThenReturnException() {
        ZonedDateTime startReg = ZonedDateTime.of(LocalDateTime.now().minusDays(2), ZoneId.of("Europe/Moscow"));
        ZonedDateTime finishReg = ZonedDateTime.of(LocalDateTime.now().plusDays(5), ZoneId.of("Europe/Moscow"));

        Course course = new Course(0L, "title", 10L, startReg, finishReg, new ArrayList<>());
        Student student = new Student(0L, "Иван", "Иванов", new ArrayList<>());
        Student studentNew = new Student(0L, "Иван", "Иванов", List.of(course));
        StudentDto studentDto = new StudentDto("Иван", "Иванов", List.of(course.getTitle()));

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));
        when(courseService.getCourseByIdForStudent(any(Long.class))).thenReturn(course);
        when(studentRepository.save(any(Student.class))).thenReturn(studentNew);
        when(studentMappingImpl.studentToStudentDto(any(Student.class))).thenReturn(studentDto);

        StudentDto saveStudentDto = studentService.registrationOnCourse(0L, 0L);

        assertThat(saveStudentDto, equalTo(studentDto));
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void registrationOnCourse_whenHaveRegistration_ThenReturnException() {
        Student student = new Student(0L, "Иван", "Иванов", new ArrayList<>());
        Course course = new Course(0L, "title", 10L, null, null, List.of(student));
        student.setCourses(List.of(course));
        boolean isUnavailable = false;
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));
        when(courseService.getCourseByIdForStudent(any(Long.class))).thenReturn(course);
        when(timeWindowService.isUnavailableTime(any(Course.class))).thenReturn(isUnavailable);

        assertThrows(DataConflictRequest.class,
                () -> studentService.registrationOnCourse(0L, 0L));
    }

    @Test
    void leaveCourse_whenNotHaveRegistration_ThenReturnException() {
        Student student = new Student(0L, "Иван", "Иванов", new ArrayList<>());
        Student secondStudent = new Student(1L, "Петр", "Петров", new ArrayList<>());
        Course course = new Course(0L, "title", 10L, null, null, List.of(secondStudent));
        secondStudent.setCourses(List.of(course));

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));
        when(courseService.getCourseByIdForStudent(any(Long.class))).thenReturn(course);
        student.setCourses(List.of(course));

        assertThrows(DataConflictRequest.class,
                () -> studentService.leaveCourse(0L, 0L));
    }
}