package demo.demo.ImportReport.Report;

import demo.demo.ImportReport.Employee.Kind;
import demo.demo.ImportReport.FileHandler.FileHandler;

import java.util.List;

public class ReportBuilder {

    Kind kind;
    FileHandler inFile, outFile;

    List<Report> reports;

    public ReportBuilder(Kind kind, FileHandler inFile, FileHandler outFile) {
        this.inFile = inFile;
        this.outFile = outFile;
        this.kind = kind;
    }

    public List<Report> getReports() {
        return this.reports;
    }

}
