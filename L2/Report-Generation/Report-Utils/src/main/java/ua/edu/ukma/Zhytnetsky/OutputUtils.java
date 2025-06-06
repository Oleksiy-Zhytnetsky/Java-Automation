package ua.edu.ukma.Zhytnetsky;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public final class OutputUtils {

    private static final Logger logger = LoggerFactory.getLogger(OutputUtils.class);

    public static void saveReport(final Report report) {
        final String fileName = report.getTitle() + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(formatReport(report));
            logger.info("Report saved to file: {}", fileName);
        }
        catch (IOException e) {
            logger.error("Failed to save report to file: {}", fileName, e);
        }
    }

    public static void displayReport(final Report report) {
        System.out.println(formatReport(report));
    }

    private static String formatReport(final Report report) {
        StringBuilder sb = new StringBuilder();
        sb.append("======== ").append(report.getTitle()).append(" ========\n");
        sb.append("Date: ").append(report.getDate()).append("\n");
        sb.append("Author: ").append(report.getAuthor().getName())
                .append(" (").append(report.getAuthor().getEmail());
        for (ReportSection section : report.getSections()) {
            sb.append("\n\n").append("## ").append(section.getHeading()).append("\n");
            sb.append(section.getBody());
        }
        sb.append('\n');
        return sb.toString();
    }
}
