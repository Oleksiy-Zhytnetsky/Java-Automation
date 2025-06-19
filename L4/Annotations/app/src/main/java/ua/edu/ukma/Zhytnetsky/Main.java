package ua.edu.ukma.Zhytnetsky;

import ua.edu.ukma.Zhytnetsky.classes.Course;
import ua.edu.ukma.Zhytnetsky.classes.CourseBuilder;
import ua.edu.ukma.Zhytnetsky.classes.User;
import ua.edu.ukma.Zhytnetsky.classes.UserBuilder;
import ua.edu.ukma.Zhytnetsky.utils.ValidationUtils;

import java.time.LocalDate;

public final class Main {

    public static void main(String[] args) {
        final User user = new UserBuilder()
                .name("Oleksiy")
                .email("name.surname@ukma.edu.ua")
                .birthDate(LocalDate.now())
                .accountLevel(8)
                .build();
        ValidationUtils.instance().validateUser(user);
        System.out.println("User is: " + user);

        final Course course = new CourseBuilder()
                .title("Intro to programming")
                .description("Intro to programming in Java")
                .levelValue(2)
                .build();
        ValidationUtils.instance().validateCourse(course);
        System.out.println("Course is: " + course);
    }

}
