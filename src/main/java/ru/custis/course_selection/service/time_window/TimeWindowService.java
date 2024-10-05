package ru.custis.course_selection.service.time_window;

import ru.custis.course_selection.entity.Course;

public interface TimeWindowService {

    boolean isUnavailableTime(Course course);
}
