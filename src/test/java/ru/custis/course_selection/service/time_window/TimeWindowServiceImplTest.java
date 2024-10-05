package ru.custis.course_selection.service.time_window;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.custis.course_selection.entity.Course;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class TimeWindowServiceImplTest {

    @InjectMocks
    private TimeWindowServiceImpl timeWindowService;

    @Test
    void isUnavailableTime_whenTwoBorders_RequestWithinInterval() {
        ZonedDateTime startReg = ZonedDateTime.of(LocalDateTime.now().minusDays(2), ZoneId.of("Europe/Moscow"));
        ZonedDateTime finishReg = ZonedDateTime.of(LocalDateTime.now().plusDays(5), ZoneId.of("Europe/Moscow"));

        Course course = new Course(0L, "title", 10L, startReg, finishReg, new ArrayList<>());

        assertFalse(timeWindowService.isUnavailableTime(course));
    }

    @Test
    void isUnavailableTime_whenTwoBorders_RequestWithoutInterval() {
        ZonedDateTime startReg = ZonedDateTime.of(LocalDateTime.now().minusDays(5), ZoneId.of("Europe/Moscow"));
        ZonedDateTime finishReg = ZonedDateTime.of(LocalDateTime.now().minusDays(2), ZoneId.of("Europe/Moscow"));

        Course course = new Course(0L, "title", 10L, startReg, finishReg, new ArrayList<>());

        assertTrue(timeWindowService.isUnavailableTime(course));
    }

    @Test
    void isUnavailableTime_whenRequestBeforeStart_ResultTrue() {
        ZonedDateTime startReg = ZonedDateTime.of(LocalDateTime.now().plusDays(5), ZoneId.of("Europe/Moscow"));

        Course course = new Course(0L, "title", 10L, startReg, null, new ArrayList<>());

        assertTrue(timeWindowService.isUnavailableTime(course));
    }

    @Test
    void isUnavailableTime_whenRequestAfterFinish_ResultTrue() {
        ZonedDateTime finishReg = ZonedDateTime.of(LocalDateTime.now().minusDays(5), ZoneId.of("Europe/Moscow"));

        Course course = new Course(0L, "title", 10L, null, finishReg, new ArrayList<>());

        assertTrue(timeWindowService.isUnavailableTime(course));
    }
}