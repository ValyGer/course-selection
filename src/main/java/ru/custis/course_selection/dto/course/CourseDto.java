package ru.custis.course_selection.dto.course;

import lombok.*;

import java.util.List;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {

    private String title;
    private long numberOccupiedPlaces;
    private long numberAvailablePlaces;
    private String startReg;
    private String finishReg;
    private List<String> studentNames;
}
