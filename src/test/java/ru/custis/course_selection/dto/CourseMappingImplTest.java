package ru.custis.course_selection.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.custis.course_selection.entity.Course;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CourseMappingImplTest {

    @InjectMocks
    private CourseMappingImpl courseMappingImpl;

    @Test
    void courseToCourseDto_Successful() {
        Course course = new Course(0L, "title", 10L, new ArrayList<>());
        CourseDto courseDtoWait = new CourseDto("title", 0L, 10L, new ArrayList<>());

        CourseDto courseDto = courseMappingImpl.courseToCourseDto(course);

        assertThat(courseDtoWait.getTitle(), equalTo(courseDto.getTitle()));
        assertThat(courseDtoWait.getNumberOccupiedPlaces(), equalTo(courseDto.getNumberOccupiedPlaces()));
        assertThat(courseDtoWait.getNumberAvailablePlaces(), equalTo(courseDto.getNumberAvailablePlaces()));
        assertThat(courseDtoWait.getStudentNames().size(), equalTo(courseDto.getStudentNames().size()));
    }


    @Test
    void courseInitDtoToCourse_Successful() {
        CourseInitDto courseInitDto = new CourseInitDto("title", 10L);
        Course courseWait = new Course(0L, "title", 10L, new ArrayList<>());

        Course course = courseMappingImpl.courseInitDtoToCourse(courseInitDto);

        assertThat(course.getTitle(), equalTo(courseWait.getTitle()));
        assertThat(course.getLimitPerson(), equalTo(courseWait.getLimitPerson()));
        assertThat(course.getStudents().size(), equalTo(courseWait.getStudents().size()));
    }
}