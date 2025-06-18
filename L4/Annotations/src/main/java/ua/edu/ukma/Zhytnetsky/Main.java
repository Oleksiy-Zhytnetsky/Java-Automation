package ua.edu.ukma.Zhytnetsky;

import ua.edu.ukma.Zhytnetsky.classes.Course;
import ua.edu.ukma.Zhytnetsky.classes.User;
import ua.edu.ukma.Zhytnetsky.utils.ValidationUtils;

import java.time.LocalDate;


public final class Main {

    public static void main(String[] args) {
        final User user = new User(
                "Oleksiy",
                "name.surname@ukma.edu.ua",
                LocalDate.now(),
                8
        );
        ValidationUtils.instance().validateUser(user);
        System.out.println("User is: " + user);

        final Course course = new Course(
                "Intro to programming",
                "Intro to programming in Java",
                2
        );
        ValidationUtils.instance().validateCourse(course);
        System.out.println("Course is: " + course);
    }
}
