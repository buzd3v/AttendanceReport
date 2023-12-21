package demo.demo.ImportReport.Report;

import demo.demo.ImportReport.Employee.Employee;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OfficerReport extends Report {

    public class ArriveData {
        public ArriveData() {
            hoursComeLate = 0;
            hoursEarlyLeave = 0;
            isInAfterNoon = true;
            isInMorning = true;
        }

        public double hoursComeLate;
        public double hoursEarlyLeave;
        boolean isInAfterNoon;
        boolean isInMorning;

        public String getDataElement(int i) {
            if (i == 1) {
                if (isInMorning) return "Yes";
                else return "No";
            } else if (i == 2) {
                if (isInAfterNoon) return "Yes";
                else return "No";
            } else if (i == 3) {
                return Double.toString(hoursComeLate);
            } else if (i == 4) {
                return Double.toString(hoursComeLate);
            }
            return "Null variable";
        }
    }

    private final Map<Long, ArriveData> shiftReport;
    private final int START_TIME = 8;
    private final int END_TIME = 17;
    private final int LUNCH_END_TIME = 13;
    private final int LUNCH_START_TIME = 12;

    public OfficerReport(Employee employee, List<Long> inTime, List<Long> outTime) {
        super(employee, inTime, outTime);
        shiftReport = new HashMap<>();
        convertToShiftReport();
    }

    void convertToShiftReport() {
        List<Long> inTime = getInTime();
        List<Long> outTime = getOutTime();
        for (int i = 0; i < inTime.size(); i++) {
            ArriveData arriveData = new ArriveData();
            LocalDateTime tld1 = LocalDateTime.ofInstant(Instant.ofEpochSecond(inTime.get(i)), ZoneId.of("Asia/Ho_Chi_Minh"));
            LocalDateTime tld2 = LocalDateTime.ofInstant(Instant.ofEpochSecond(outTime.get(i)), ZoneId.of("Asia/Ho_Chi_Minh"));

            double timeCome = tld1.getHour() + tld1.getMinute() / 60.0;
            if (timeCome >= START_TIME) {
                if (timeCome >= LUNCH_START_TIME && timeCome <= LUNCH_END_TIME) {
                    arriveData.isInMorning = false;
                    arriveData.hoursComeLate = 0;
                } else if (timeCome >= LUNCH_END_TIME) {
                    arriveData.isInMorning = false;
                    arriveData.hoursComeLate = timeCome - LUNCH_END_TIME;
                } else {
                    arriveData.hoursComeLate = timeCome - START_TIME;
                }
            } else {
                arriveData.hoursComeLate = 0.0;
            }

            double timeGoHome = tld2.getHour() + tld2.getMinute() / 60.0;
            if (timeGoHome < END_TIME) {
                if (timeGoHome >= LUNCH_START_TIME && timeGoHome <= LUNCH_END_TIME) {
                    arriveData.isInAfterNoon = false;
                    arriveData.hoursEarlyLeave = 0;
                } else if (timeGoHome <= LUNCH_START_TIME) {
                    arriveData.isInAfterNoon = false;
                    arriveData.hoursEarlyLeave = LUNCH_START_TIME - timeGoHome;
                } else {
                    arriveData.hoursEarlyLeave = END_TIME - timeGoHome;
                }
            } else {
                arriveData.hoursEarlyLeave = 0.0;
            }
            shiftReport.put(inTime.get(i), arriveData);
        }
    }

    public Map<Long, ArriveData> getShiftReport() {
        return this.shiftReport;
    }
}
