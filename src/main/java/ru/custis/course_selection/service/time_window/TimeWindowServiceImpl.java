package ru.custis.course_selection.service.time_window;

import org.springframework.stereotype.Service;
import ru.custis.course_selection.entity.Course;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Service
public class TimeWindowServiceImpl implements TimeWindowService {

    private final Clock clock = Clock.systemUTC();

    @Override
    public boolean isUnavailableTime(Course course) {

        LocalDateTime now = LocalDateTime.now(clock);

        if (course.getStartReg() != null) {
            if (course.getFinishReg() != null) {
                return !(now.isAfter(convertTime(course.getStartReg())) && now.isBefore(convertTime(course.getFinishReg())));
            }
            return !now.isAfter(convertTime(course.getStartReg()));
        } else if (course.getFinishReg() != null) {
            return !now.isBefore(convertTime(course.getFinishReg()));
        }
        return false;
    }

    private LocalDateTime convertTime(ZonedDateTime zonedDate) {
        ZonedDateTime zonedDateUTC = zonedDate.withZoneSameInstant(ZoneOffset.UTC);
        return zonedDateUTC.toLocalDateTime();
    }
}
