package ru.custis.course_selection.service.time_window;

import org.springframework.stereotype.Service;
import ru.custis.course_selection.entity.Course;

import java.time.LocalDateTime;

@Service
public class TimeWindowServiceImpl implements TimeWindowService {
    @Override
    public boolean isUnavailableTime(Course course, LocalDateTime now) {

        if (course.getStartReg() != null) {
            if (course.getFinishReg() != null) {
                return !(now.isAfter(course.getStartReg()) && now.isBefore(course.getFinishReg()));
            }
            return !now.isAfter(course.getStartReg());
        } else if (course.getFinishReg() != null) {
            return !now.isBefore(course.getFinishReg());
        }
        return false;
    }
}
