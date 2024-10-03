package ru.custis.course_selection.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.custis.course_selection.dto.CourseDto;
import ru.custis.course_selection.dto.CourseInitDto;
import ru.custis.course_selection.dto.CourseMapping;
import ru.custis.course_selection.entity.Course;
import ru.custis.course_selection.repository.CourseRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapping courseMapping;

    @Override
    public List<CourseDto> getAllCourse() {
        List<Course> courses = courseRepository.findAll();
        log.info("Получены все курсы");
        return courses.stream().map(courseMapping::courseToCourseDto).collect(Collectors.toList());
    }

    @Override
    public CourseDto getCourse(Long courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isPresent()) {
            log.info("Получен курс с id = {}", courseId);
            return courseMapping.courseToCourseDto(courseOptional.get());
        }
        throw new RuntimeException("Курс " + courseId + " не найден");
    }

    @Override
    public CourseDto createCourse(CourseInitDto courseIniDto) {
        Course course = courseMapping.courseInitDtoToCourse(courseIniDto);
        log.info("Новый курс успешно создан с id = {}", course.getId());
        return courseMapping.courseToCourseDto(courseRepository.save(course));
    }

    @Override
    public CourseDto updateCourse(Long courseId, CourseInitDto courseIniDto) {
        Course course = validCourseId(courseId);
        if (!courseIniDto.getTitle().isBlank()){
            course.setTitle(courseIniDto.getTitle());
        }
        if ((courseIniDto.getLimitPerson() != 0) &&
                (courseIniDto.getLimitPerson() >= course.getStudents().size())){
            course.setLimitPerson(courseIniDto.getLimitPerson());
        }
        log.info("Курс с id = {} успешно обновлен", courseId);
        return courseMapping.courseToCourseDto(course);
    }

    @Override
    public void deleteCourse(Long courseId) {
        try {
            courseRepository.deleteById(courseId);
            log.info("Курс удален успешно id = {}", courseId);
        } catch (RuntimeException e) {
            System.out.println("Курс " + courseId + " не найден");
        }
    }

    private Course validCourseId(Long courseId){
        Optional<Course> course = courseRepository.findById(courseId);
        if (course.isPresent()) {
            return course.get();
        } else {
            throw new RuntimeException("Курс " + courseId + " не найден");
        }
    }
}
