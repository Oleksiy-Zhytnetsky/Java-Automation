package ua.edu.ukma.Zhytnetsky.classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import ua.edu.ukma.Zhytnetsky.annotations.GenerateBuilder;
import ua.edu.ukma.Zhytnetsky.annotations.GenerateFieldConstants;
import ua.edu.ukma.Zhytnetsky.annotations.InRange;

@Data
@AllArgsConstructor
@GenerateBuilder
@GenerateFieldConstants
public final class Course {

    private String title;
    private String description;

    @InRange(min = 1, max = 5)
    private Integer levelValue;

}
