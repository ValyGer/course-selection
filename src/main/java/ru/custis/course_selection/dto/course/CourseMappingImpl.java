package ru.custis.course_selection.dto.course;

import org.springframework.stereotype.Component;
import ru.custis.course_selection.entity.Course;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class CourseMappingImpl implements CourseMapping {

    @Override
    public CourseDto courseToCourseDto(Course course) {
        CourseDto courseDto = new CourseDto();
        courseDto.setTitle(course.getTitle());
        courseDto.setNumberAvailablePlaces(course.getLimitPerson() - course.getStudents().size());
        courseDto.setNumberOccupiedPlaces(course.getStudents().size());
        List<String> studentNames = course.getStudents().stream()
                .map(student -> student.getFirstname() + " " + student.getLastname()).toList();

        if (course.getStartReg() != null) {
            courseDto.setStartReg(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(course.getStartReg()));
        }
        if (course.getFinishReg() != null) {
            courseDto.setFinishReg(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(course.getFinishReg()));
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
            course.setStartReg(LocalDateTime.parse(courseInitDto.getStartReg(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        if (courseInitDto.getFinishReg() != null) {
            course.setFinishReg(LocalDateTime.parse(courseInitDto.getFinishReg(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        return course;
    }
}
