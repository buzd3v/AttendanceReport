package demo.demo.ImportReport.Report;

import demo.demo.ImportReport.Employee.Employee;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public class Report {
    private Employee employee;
    private List<Long> inTime;
    private List<Long> outTime;

    public Report(Employee employee, List<Long> inTime, List<Long> outTime) {
        this.employee = employee;
        this.inTime = inTime;
        this.outTime = outTime;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }


    public List<Long> getInTime() {
        return inTime;
    }

    public void setInTime(List<Long> inTime) {
        this.inTime = inTime;
    }

    public List<Long> getOutTime() {
        return outTime;
    }

    public void setOutTime(List<Long> outTime) {
        this.outTime = outTime;
    }

    boolean isSameDay(Long t1, Long t2) {
        LocalDateTime tld1 = LocalDateTime.ofInstant(Instant.ofEpochSecond(t1), ZoneId.of("Asia/Ho_Chi_Minh"));
        LocalDateTime tld2 = LocalDateTime.ofInstant(Instant.ofEpochSecond(t2), ZoneId.of("Asia/Ho_Chi_Minh"));

        return tld1.toLocalDate().isEqual(tld2.toLocalDate());
    }

    double getDuration(Long t1, Long t2) {

        LocalDateTime tld1 = LocalDateTime.ofInstant(Instant.ofEpochSecond(t1), ZoneId.of("Asia/Ho_Chi_Minh"));
        LocalDateTime tld2 = LocalDateTime.ofInstant(Instant.ofEpochSecond(t2), ZoneId.of("Asia/Ho_Chi_Minh"));

        Duration duration = Duration.between(tld1, tld2);

        return duration.toNanos() / (double) Duration.ofHours(1).toNanos();
    }
}
