package ua.edu.ukma.Zhytnetsky.classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import ua.edu.ukma.Zhytnetsky.annotations.Email;
import ua.edu.ukma.Zhytnetsky.annotations.InRange;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public final class User {

    private String name;

    @Email
    private String email;

    private LocalDate birthDate;

    @InRange(min = 1, max = 100)
    private Integer accountLevel;

}
