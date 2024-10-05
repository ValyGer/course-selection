package ru.custis.course_selection.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.custis.course_selection.dto.course.CourseDto;
import ru.custis.course_selection.dto.course.CourseInitDto;
import ru.custis.course_selection.dto.course.CourseMappingImpl;
import ru.custis.course_selection.entity.Course;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(MockitoExtension.class)
class CourseMappingImplTest {

    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm xxx", Locale.ENGLISH);

    @InjectMocks
    private CourseMappingImpl courseMappingImpl;

    @Test
    void courseToCourseDto_Successful() {
        Course course = new Course(0L, "title", 10L, null, null, new ArrayList<>());
        CourseDto courseDtoWait = new CourseDto("title", 0L,
                10L, null, null, new ArrayList<>());

        CourseDto courseDto = courseMappingImpl.courseToCourseDto(course);

        assertThat(courseDtoWait.getTitle(), equalTo(courseDto.getTitle()));
        assertThat(courseDtoWait.getNumberOccupiedPlaces(), equalTo(courseDto.getNumberOccupiedPlaces()));
        assertThat(courseDtoWait.getNumberAvailablePlaces(), equalTo(courseDto.getNumberAvailablePlaces()));
        assertThat(courseDtoWait.getStudentNames().size(), equalTo(courseDto.getStudentNames().size()));
    }


    @Test
    void courseInitDtoToCourse_Successful() {
        CourseInitDto courseInitDto = new CourseInitDto("title", 10L, null, null);
        Course courseWait = new Course(0L, "title", 10L, null, null, new ArrayList<>());

        Course course = courseMappingImpl.courseInitDtoToCourse(courseInitDto);

        assertThat(course.getTitle(), equalTo(courseWait.getTitle()));
        assertThat(course.getLimitPerson(), equalTo(courseWait.getLimitPerson()));
        assertThat(course.getStudents().size(), equalTo(courseWait.getStudents().size()));
    }


    @Test
    void courseInitDtoToCourse_SuccessfulWithRegWindow() {
        String startTime = "2024-09-30 16:30 +03:00";
        String finishTime = "2024-10-10 16:30 +03:00";
        ZonedDateTime startReg = ZonedDateTime.parse(startTime, formatter);
        ZonedDateTime finishReg = ZonedDateTime.parse(finishTime, formatter);
        ;

        CourseInitDto courseInitDto = new CourseInitDto("title", 10L, startTime, finishTime);
        Course courseWait = new Course(0L, "title", 10L, startReg, finishReg, new ArrayList<>());

        Course course = courseMappingImpl.courseInitDtoToCourse(courseInitDto);

        assertThat(course.getTitle(), equalTo(courseWait.getTitle()));
        assertThat(course.getLimitPerson(), equalTo(courseWait.getLimitPerson()));
        assertThat(course.getStartReg(), equalTo(startReg));
        assertThat(course.getFinishReg(), equalTo(finishReg));
        assertThat(course.getStudents().size(), equalTo(courseWait.getStudents().size()));
    }
}