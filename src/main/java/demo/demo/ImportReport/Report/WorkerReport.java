package demo.demo.ImportReport.Report;

import demo.demo.ImportReport.Employee.Employee;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkerReport extends Report {

    private final int SHIFT1_START = 8;

    private final int SHIFT2_START = 13;
    private final int SHIFT3_START = 18;
    private final int SHIFT1_END = 12;
    private final int SHIFT2_END = 17;
    private final int SHIFT3_END = 22;

    public class ShiftData {
        public ShiftData() {
            shift1Time = 0;
            shift2Time = 0;
            shift3Time = 0;
        }

        public double shift1Time;
        public double shift2Time;
        public double shift3Time;

        public double getElement(int i) {
            if (i == 1) return shift1Time;
            else if (i == 2) return shift2Time;
            else if (i == 3) {
                return shift3Time;
            } else return 0;
        }

    }

    Map<Long, ShiftData> shiftReport;

    public WorkerReport(Employee employee, List<Long> inTime, List<Long> outTime) {
        super(employee, inTime, outTime);
        shiftReport = new HashMap<>();
        convertToShiftReport();
    }

    void convertToShiftReport() {
        List<Long> inTime = getInTime();
        List<Long> outTime = getOutTime();
        Long day_start = inTime.get(0);
        for (int i = 0; i < inTime.size(); i++) {
            ShiftData data = new ShiftData();
            while (i < inTime.size() && isSameDay(inTime.get(i), day_start)) {
                Long shift_start = inTime.get(i);
                Long shift_end = outTime.get(i);
                LocalDateTime tld1 = LocalDateTime.ofInstant(Instant.ofEpochSecond(shift_start), ZoneId.of("Asia/Ho_Chi_Minh"));
                LocalDateTime tld2 = LocalDateTime.ofInstant(Instant.ofEpochSecond(shift_end), ZoneId.of("Asia/Ho_Chi_Minh"));
                int shift_start_hour = tld1.getHour();
                double time;
                if (SHIFT1_START <= shift_start_hour && shift_start_hour <= SHIFT1_END) {
                    time = getDuration(shift_start, shift_end);
                    data.shift1Time = time;
                } else if (SHIFT2_START <= shift_start_hour && shift_start_hour <= SHIFT2_END) {
                    time = getDuration(shift_start, shift_end);
                    data.shift2Time = time;
                } else if (SHIFT3_START <= shift_start_hour && shift_start_hour <= SHIFT3_END) {
                    time = getDuration(shift_start, shift_end);
                    data.shift3Time = time;
                }
                i++;
                shiftReport.put(day_start, data);
                if (i < inTime.size())
                    day_start = inTime.get(i);
            }

        }
    }

    public Map<Long, ShiftData> getShiftReport() {
        return shiftReport;
    }

}
