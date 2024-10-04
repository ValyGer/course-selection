package ru.custis.course_selection.dto.course;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CourseInitDto {
    @NotNull
    @Size(min = 5, max = 64, message = "Название должно быть не менее 5 и не более 64 символов")
    private String title;
    @Min(2)
    private Long limitPerson;

    private String startReg;
    private String finishReg;
}
