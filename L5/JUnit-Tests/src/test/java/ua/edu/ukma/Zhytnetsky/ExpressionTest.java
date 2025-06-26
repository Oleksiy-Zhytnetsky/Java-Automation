package ua.edu.ukma.Zhytnetsky;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.DayOfWeek;
import java.time.LocalDate;

public final class ExpressionTest {

    @Test
    @Tag("fast")
    public void assertCanEvaluateAddition() {
        Assumptions.assumeTrue(Runtime.version().feature() >= 17);

        final Expression<Integer> expr = new Expression<>(2, 3, MathUtils::add);
        final Integer expectedResult = 5;
        final Integer actualResult = expr.evaluate();
        Assertions.assertEquals(actualResult, expectedResult);
    }

    @ParameterizedTest
    @Tag("parametrised")
    @CsvSource({
            "6, 2, 3",
            "5, 0, -1",
            "26, 3, 8"
    })
    public void assertCanEvaluateDivision(
            final Integer dividend,
            final Integer divisor,
            final Integer expectedResult
    ) {
        Assumptions.assumeFalse(divisor.equals(0));

        final Expression<Integer> expr = new Expression<>(dividend, divisor, MathUtils::div);
        final Integer actualResult = expr.evaluate();
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    @Tag("fast")
    public void assertCanCorrectlyDisplayExpressionString() {
        Assumptions.assumeFalse(LocalDate.now().getDayOfWeek().equals(DayOfWeek.WEDNESDAY));

        final Expression<Integer> expr = new Expression<>(12, 7, MathUtils::sub);
        final String expectedResult = "12-7";
        final String actualResult = expr.display();
        Assertions.assertEquals(actualResult, expectedResult);
    }

}
