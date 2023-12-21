package demo.demo.ImportReport.Report;

import demo.demo.ImportReport.Employee.Employee;
import demo.demo.ImportReport.Employee.Kind;
import demo.demo.ImportReport.FileHandler.FileHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WorkerReportBuilder extends ReportBuilder {

    public WorkerReportBuilder(Kind kind, FileHandler inFile, FileHandler outFile) {
        super(kind, inFile, outFile);
        reports = new ArrayList<>();
        convert();
    }

    private void convert() {
        Map<String, List<Long>> inData = inFile.getData();
        Map<String, List<Long>> outData = outFile.getData();

        inData.forEach((key, value) -> {
            List<Long> outValue = outData.get(key);
            WorkerReport workerReport = new WorkerReport(new Employee(key, kind), value, outValue);
            reports.add(workerReport);
        });
    }


}
