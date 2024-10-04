package ru.custis.course_selection.service.course;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.custis.course_selection.dto.course.CourseDto;
import ru.custis.course_selection.dto.course.CourseInitDto;
import ru.custis.course_selection.dto.course.CourseMappingImpl;
import ru.custis.course_selection.entity.Course;
import ru.custis.course_selection.exception.NotFoundException;
import ru.custis.course_selection.repository.CourseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @InjectMocks
    private CourseServiceImpl courseService;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseMappingImpl courseMappingImpl;


    @Test
    void getAllCourse_Successful() {
        Course firstCurse = new Course(0L, "titleOne", 0, null, null, new ArrayList<>());
        Course secondCurse = new Course(1L, "titleTwo", 0, null, null,  new ArrayList<>());

        CourseDto firstCourseDto = new CourseDto("title", 0L, 0L, null, null,  new ArrayList<>());
        CourseDto secondCourseDto = new CourseDto("titleTwo", 0L, 0L, null, null,  new ArrayList<>());

        when(courseRepository.findAll()).thenReturn(List.of(firstCurse, secondCurse));
        when(courseMappingImpl.courseToCourseDto(firstCurse)).thenReturn(firstCourseDto);
        when(courseMappingImpl.courseToCourseDto(secondCurse)).thenReturn(secondCourseDto);


        List<CourseDto> saveListCourseDto = courseService.getAllCourse();

        verify(courseRepository, times(1)).findAll();
        assertThat(firstCourseDto, equalTo(saveListCourseDto.get(0)));
        assertThat(secondCourseDto, equalTo(saveListCourseDto.get(1)));
    }

    @Test
    void getCourse_whenIdFound_ThenReturnCourse() {
        Long courseId = 0L;
        Course course = new Course();
        CourseDto courseDto = new CourseDto();
        when(courseRepository.findById(any(Long.class))).thenReturn(Optional.of(course));
        when(courseMappingImpl.courseToCourseDto(any(Course.class))).thenReturn(courseDto);

        CourseDto saveCourseDto = courseService.getCourseById(0L);

        verify(courseRepository, times(1)).findById(courseId);
        assertThat(courseDto, equalTo(saveCourseDto));
    }

    @Test
    void getCourse_whenIdNotFound_ThenReturnException() {

        assertThrows(NotFoundException.class,
                () -> courseService.getCourseById(any(Long.class)));
    }

    @Test
    void createCourse_whenSuccessful_ThenReturnCourse() {
        Course course = new Course();
        CourseDto courseDto = new CourseDto();

        when(courseMappingImpl.courseInitDtoToCourse(any(CourseInitDto.class))).thenReturn(course);
        when(courseRepository.save(any(Course.class))).thenReturn(course);
        when(courseMappingImpl.courseToCourseDto(any(Course.class))).thenReturn(courseDto);

        CourseDto saveCourseDto = courseService.createCourse(new CourseInitDto());

        assertThat(saveCourseDto, equalTo(courseDto));
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void updateCourse_whenSuccessful_ThenReturnCourse() {
        CourseInitDto courseInitDto = new CourseInitDto("title New", 20L, null, null);
        Course courseNew = new Course(0L, "title New", 20L, null, null, new ArrayList<>());
        Course course = new Course(0L, "title", 10L, null, null,  new ArrayList<>());
        CourseDto courseDtoNew = new CourseDto("title New", 0L, 20L, null, null,  new ArrayList<>());

        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
        when(courseRepository.save(any(Course.class))).thenReturn(courseNew);
        when(courseMappingImpl.courseToCourseDto(any(Course.class))).thenReturn(courseDtoNew);


        CourseDto saveCourseDto = courseService.updateCourse(0L, courseInitDto);

        assertThat(saveCourseDto, equalTo(courseDtoNew));
    }

    @Test
    void deleteCourse_whenSuccessful() {
        Course course = new Course();
        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
        courseService.deleteCourse(any(Long.class));

        verify(courseRepository, times(1)).deleteById(any(Long.class));
    }

    @Test
    void deleteCourse_whenNotSuccessful_ThenReturnException() {

        assertThrows(NotFoundException.class,
                () -> courseService.deleteCourse(any(Long.class)));
    }
}