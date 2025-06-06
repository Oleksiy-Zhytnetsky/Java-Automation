package ua.edu.ukma.Zhytnetsky;

import java.time.LocalDate;
import java.util.ArrayList;

public final class Main {

    public static void main(String[] args) {
        final Author author = new Author("Mike Smith", "mike_smith@gmail.com");
        final ReportSection section1 = new ReportSection("Q1",
                "Q1 earnings detailed description");
        final ReportSection section2 = new ReportSection("Q2",
                "Q2 earnings detailed description");
        final ArrayList<ReportSection> sections = new ArrayList<>();
        sections.add(section1);
        sections.add(section2);

        final Report report = new Report(author, LocalDate.now(),
                "Earnings report", sections);
        ValidationUtils.validateReport(report);

        OutputUtils.displayReport(report);
        OutputUtils.saveReport(report);
    }

}
