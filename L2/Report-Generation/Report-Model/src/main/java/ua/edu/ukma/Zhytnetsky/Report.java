package ua.edu.ukma.Zhytnetsky;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;

@Data
@AllArgsConstructor
public final class Report {

    private Author author;
    private LocalDate date;
    private String title;
    private ArrayList<ReportSection> sections;

}
