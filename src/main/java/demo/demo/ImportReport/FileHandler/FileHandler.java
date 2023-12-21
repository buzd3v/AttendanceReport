package demo.demo.ImportReport.FileHandler;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import demo.demo.ImportReport.Employee.Kind;
import demo.demo.ImportReport.Error.Error;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileHandler {
    private File file;
    private final List<Error> status;
    private CSVReader csvReader;
    private String extension;
    private final Map<String, List<Long>> data;

    public FileHandler(File file, Kind kind) throws CsvValidationException, IOException {
        this.file = file;
        status = new ArrayList<>();
        data = new HashMap<>();
        checkExtension();
        if (status.isEmpty()) {
            checkFormat();
        }
        if (status.isEmpty()) {
            setData();
        }
    }

    private void checkExtension() {
        boolean isRightExtension = file.toString().endsWith(".xlsx") || file.toString().endsWith(".csv");
        if (!isRightExtension) {
            status.add(new Error("ERR001", file.getName() + " is not the right extension: .xlsx, .csv"));
        } else {
            if (file.toString().endsWith(".xlsx"))
                extension = ".xlsx";
            else extension = ".csv";
        }
    }

    private void checkFormat() {
        if (extension.equals(".csv")) {
            try {
                if (!checkCSVFormat()) {
                    status.add(new Error("ERR002", "File do not have the right format"));
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean checkCSVFormat() throws FileNotFoundException {
        boolean isRightFormat = true;
        FileInputStream fis = new FileInputStream(file);
        csvReader = new CSVReader(new InputStreamReader(fis));
        try {
            String[] header = csvReader.readNext();
            if (header != null && header.length == 2 && header[0].equalsIgnoreCase("ID") && header[1].equalsIgnoreCase("timestamp")) {
                System.out.println("The CSV file has the expected format.");
            } else {
                System.out.println("The CSV file does not have the expected format.");
                isRightFormat = false;
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return isRightFormat;
    }

    private void setDataCSV() throws CsvValidationException, IOException {
        String[] line = csvReader.readNext();
        while (line != null) {
            if (data.containsKey(line[0])) {
                data.get(line[0]).add(Long.parseLong(line[1]));
            } else {
                data.put(line[0], new ArrayList<>());
                data.get(line[0]).add(Long.parseLong(line[1]));
            }
            line = csvReader.readNext();
        }
    }

    private void setDataXLSX() throws CsvValidationException, IOException {
        String[] line = csvReader.readNext();
        while (line != null) {
            if (data.containsKey(line[0])) {
                data.get(line[0]).add(Long.parseLong(line[1]));
            }
            line = csvReader.readNext();
        }
    }

    private void setData() throws CsvValidationException, IOException {
        if (extension.equals(".csv")) {
            setDataCSV();
        } else {
            setDataXLSX();
        }
    }


    public void setFile(File file) {
        this.file = file;
    }

    public List<Error> getStatus() {
        return status;
    }

    public Map<String, List<Long>> getData() {
        return this.data;
    }


}
