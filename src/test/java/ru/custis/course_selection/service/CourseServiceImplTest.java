package ru.custis.course_selection.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.custis.course_selection.dto.CourseDto;
import ru.custis.course_selection.dto.CourseMappingImpl;
import ru.custis.course_selection.entity.Course;
import ru.custis.course_selection.exception.NotFoundException;
import ru.custis.course_selection.repository.CourseRepository;

import java.util.ArrayList;
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

    }

    @Test
    void getCourse_whenIdFound_ThenReturnCourse() {
        Long courseId = 0L;
        Course course = new Course(0L, "title", 0, new ArrayList<>());
        CourseDto courseDto = new CourseDto("title", 0L, 0L, new ArrayList<>());
        when(courseRepository.findById(any(Long.class))).thenReturn(Optional.of(course));
        when(courseMappingImpl.courseToCourseDto(any(Course.class))).thenReturn(courseDto);

        CourseDto saveCourseDto = courseService.getCourseById(0L);

        verify(courseRepository, times(1)).findById(courseId);
        assertThat(courseDto, equalTo(saveCourseDto));
    }

    @Test
    void getCourse_whenIdNotFound_ThenReturnException() {
        Long courseId = 0L;

        assertThrows(NotFoundException.class,
                () -> courseService.getCourseById(courseId));
    }

    @Test
    void createCourse() {
    }

    @Test
    void updateCourse() {
    }

    @Test
    void deleteCourse() {
    }
}