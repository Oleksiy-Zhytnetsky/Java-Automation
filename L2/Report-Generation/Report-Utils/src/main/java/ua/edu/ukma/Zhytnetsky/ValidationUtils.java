package ua.edu.ukma.Zhytnetsky;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public final class ValidationUtils {

    private static final Logger logger = LoggerFactory.getLogger(ValidationUtils.class);

    public static boolean validateReport(final Report report) {
        if (report.getTitle() == null || report.getTitle().isBlank()) {
            logger.error("Report title is missing.");
            return false;
        }
        if (report.getAuthor() == null || report.getAuthor().getName().isBlank()) {
            logger.error("Report author is missing.");
            return false;
        }
        if (report.getDate().isAfter(LocalDate.now())) {
            logger.error("Report date is in the future.");
            return false;
        }
        return true;
    }

}
