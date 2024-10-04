package ru.custis.course_selection.service.course;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.custis.course_selection.dto.course.CourseDto;
import ru.custis.course_selection.dto.course.CourseInitDto;
import ru.custis.course_selection.dto.course.CourseMapping;
import ru.custis.course_selection.entity.Course;
import ru.custis.course_selection.exception.InvalidRequestException;
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
    private final CourseMapping courseMapping;

    @Override
    public List<CourseDto> getAllCourse() {
        List<Course> courses = courseRepository.findAll();
        log.info("Получены все курсы");
        return courses.stream().map(courseMapping::courseToCourseDto).collect(Collectors.toList());
    }

    @Override
    public CourseDto getCourseById(Long courseId) {
        Course course = validCourseId(courseId);
        log.info("Получен курс с id = {}", courseId);
        return courseMapping.courseToCourseDto(course);
    }

    @Override
    public Course getCourseByIdForStudent(Long courseId) {
        Course course = validCourseId(courseId);
        log.info("Получен курс с id = {}", courseId);
        return course;
    }

    @Override
    @Transactional
    public CourseDto createCourse(CourseInitDto courseIniDto) {
        Course course = courseMapping.courseInitDtoToCourse(courseIniDto);
        Course saveCourse = courseRepository.save(course);
        log.info("Новый курс успешно создан с id = {}", saveCourse.getId());
        return courseMapping.courseToCourseDto(saveCourse);
    }

    @Override
    @Transactional
    public CourseDto updateCourse(Long courseId, CourseInitDto courseIniDto) {
        Course course = validCourseId(courseId);
        if (!courseIniDto.getTitle().isBlank()) {
            course.setTitle(courseIniDto.getTitle());
        } else {
            throw new InvalidRequestException("Поле наименование не должно быть пустым");
        }
        if ((courseIniDto.getLimitPerson() != 0) &&
                (courseIniDto.getLimitPerson() >= course.getStudents().size())) {
            course.setLimitPerson(courseIniDto.getLimitPerson());
        } else {
            throw new InvalidRequestException("Поле должно быть больше 0 и не быть меньше чем студентов на курсе сейчас");
        }
        Course saveCourse = courseRepository.save(course);
        log.info("Курс с id = {} успешно обновлен", courseId);
        return courseMapping.courseToCourseDto(saveCourse);
    }

    @Override
    @Transactional
    public void deleteCourse(Long courseId) {
        validCourseId(courseId);
        courseRepository.deleteById(courseId);
        log.info("Курс id = {} удален успешно", courseId);
    }

    private Course validCourseId(Long courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        if (course.isPresent()) {
            return course.get();
        } else {
            throw new NotFoundException("Курс с id = " + courseId + " не найден");
        }
    }
}
