package ru.custis.course_selection.service.time_window;

import ru.custis.course_selection.entity.Course;

import java.time.LocalDateTime;

public interface TimeWindowService {
    boolean isUnavailableTime(Course course, LocalDateTime now);
}
