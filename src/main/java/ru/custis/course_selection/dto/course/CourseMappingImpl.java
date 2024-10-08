package ru.custis.course_selection.dto.course;

import org.springframework.stereotype.Component;
import ru.custis.course_selection.entity.Course;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class CourseMappingImpl implements CourseMapping {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm xxx", Locale.ENGLISH);


    @Override
    public CourseDto courseToCourseDto(Course course) {
        CourseDto courseDto = new CourseDto();
        courseDto.setTitle(course.getTitle());
        courseDto.setNumberAvailablePlaces(course.getLimitPerson() - course.getStudents().size());
        courseDto.setNumberOccupiedPlaces(course.getStudents().size());
        List<String> studentNames = course.getStudents().stream()
                .map(student -> student.getFirstname() + " " + student.getLastname()).toList();

        if (course.getStartReg() != null) {

            courseDto.setStartReg(formatter.format(course.getStartReg()));
        }
        if (course.getFinishReg() != null) {
            courseDto.setFinishReg(formatter.format(course.getFinishReg()));
        }
        courseDto.setStudentNames(studentNames);
        return courseDto;
    }

    @Override
    public Course courseInitDtoToCourse(CourseInitDto courseInitDto) {
        Course course = new Course();
        course.setTitle(courseInitDto.getTitle());
        course.setLimitPerson(courseInitDto.getLimitPerson());
        course.setStudents(new ArrayList<>());

        if (courseInitDto.getStartReg() != null) {
            course.setStartReg(ZonedDateTime.parse(courseInitDto.getStartReg(), formatter));
        }
        if (courseInitDto.getFinishReg() != null) {
            course.setFinishReg(ZonedDateTime.parse(courseInitDto.getFinishReg(), formatter));
        }
        return course;
    }
}
