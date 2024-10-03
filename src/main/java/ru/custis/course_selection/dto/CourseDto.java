package ru.custis.course_selection.dto;

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
    private List<String> studentNames;
}
