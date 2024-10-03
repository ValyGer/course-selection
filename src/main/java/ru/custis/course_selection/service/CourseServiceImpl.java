package ru.custis.course_selection.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.custis.course_selection.dto.CourseDto;
import ru.custis.course_selection.dto.CourseInitDto;
import ru.custis.course_selection.dto.CourseMappingImpl;
import ru.custis.course_selection.entity.Course;
import ru.custis.course_selection.exception.NotFoundException;
import ru.custis.course_selection.repository.CourseRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMappingImpl courseMappingImpl;

    @Override
    public List<CourseDto> getAllCourse() {
        List<Course> courses = courseRepository.findAll();
        log.info("Получены все курсы");
        return courses.stream().map(courseMappingImpl::courseToCourseDto).collect(Collectors.toList());
    }

    @Override
    public CourseDto getCourseById(Long courseId) {
        Course course = validCourseId(courseId);
        log.info("Получен курс с id = {}", courseId);
        return courseMappingImpl.courseToCourseDto(course);
    }

    @Override
    public CourseDto createCourse(CourseInitDto courseIniDto) {
        Course course = courseMappingImpl.courseInitDtoToCourse(courseIniDto);
        log.info("Новый курс успешно создан с id = {}", course.getId());
        return courseMappingImpl.courseToCourseDto(courseRepository.save(course));
    }

    @Override
    public CourseDto updateCourse(Long courseId, CourseInitDto courseIniDto) {
        Course course = validCourseId(courseId);
        if (!courseIniDto.getTitle().isBlank()) {
            course.setTitle(courseIniDto.getTitle());
        }
        if ((courseIniDto.getLimitPerson() != 0) &&
                (courseIniDto.getLimitPerson() >= course.getStudents().size())) {
            course.setLimitPerson(courseIniDto.getLimitPerson());
        }
        log.info("Курс с id = {} успешно обновлен", courseId);
        return courseMappingImpl.courseToCourseDto(course);
    }

    @Override
    public void deleteCourse(Long courseId) {
        validCourseId(courseId);
        courseRepository.deleteById(courseId);
        log.info("Курс удален успешно id = {}", courseId);
    }

    private Course validCourseId(Long courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        if (course.isPresent()) {
            return course.get();
        } else {
            throw new NotFoundException("Курс " + courseId + " не найден");
        }
    }
}
