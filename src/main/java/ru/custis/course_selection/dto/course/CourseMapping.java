package ru.custis.course_selection.dto.course;

import org.springframework.stereotype.Component;
import ru.custis.course_selection.entity.Course;

@Component
public interface CourseMapping {

    CourseDto courseToCourseDto(Course course);

    Course courseInitDtoToCourse(CourseInitDto courseInitDto);
}
