package ru.custis.course_selection.dto.student;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class StudentInitDto {
    @NotBlank(message = "Поле имя не должно быть пустым")
    @Size(max = 128, message = "Имя не должно превышать 128 символов")
    private String firstname;
    @NotBlank(message = "Поле фамилия не должно быть пустым")
    @Size(max = 128, message = "Фамилия не должна превышать 128 символов")
    private String lastname;
}
