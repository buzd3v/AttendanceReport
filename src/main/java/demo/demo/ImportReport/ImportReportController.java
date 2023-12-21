package demo.demo.ImportReport;

import com.opencsv.exceptions.CsvValidationException;
import demo.demo.ImportReport.Employee.Kind;
import demo.demo.ImportReport.EmployeeTableView.DayShiftData;
import demo.demo.ImportReport.EmployeeTableView.OfficerShiftData;
import demo.demo.ImportReport.Error.Error;
import demo.demo.ImportReport.FileHandler.FileHandler;
import demo.demo.ImportReport.Report.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class ImportReportController implements Initializable {
    public TextField inTextField;
    public TextField outTextField;
    public TableColumn<Error, String> ErrorCol;
    public TableColumn<Error, String> DetailCol;
    public StackPane viewStackPane;
    private ObservableList<Error> errors;
    public TableView<DayShiftData> workersResultView;
    public TableColumn<DayShiftData, String> workerIDCol;
    public TableColumn<DayShiftData, String> dayWorkerCol;
    public TableView<OfficerShiftData> officersResultView;
    public TableColumn<OfficerShiftData, String> officerIDCol;
    public TableColumn<OfficerShiftData, String> dayOfficerCol;
    public Button cancelBtn;
    public Button acceptBtn;
    public Button convertBtn;
    public DatePicker startDatePicker;
    public DatePicker endDatePicker;
    @FXML
    private TableView<Error> logView;

    @FXML
    private VBox importPane;
    @FXML
    private AnchorPane logPane;
    @FXML
    private AnchorPane resultPane;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private ComboBox empType;

    private File inFile;
    private File outFile;

    private ReportBuilder rbuilder;
    ObservableList<DayShiftData> workerShiftDataTableView;
    ObservableList<OfficerShiftData> officerShiftDataTableView;
    List<TableColumn<DayShiftData, Double>> listWorkerColumn;
    List<TableColumn<OfficerShiftData, String>> listOfficerColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainPane.getStylesheets().setAll(BootstrapFX.bootstrapFXStylesheet(), "/style/import_report.css");
        empType.getItems().addAll("Officer", "Workers");

        //logview init
        ErrorCol.setCellValueFactory(new PropertyValueFactory<Error, String>("type"));
        DetailCol.setCellValueFactory(new PropertyValueFactory<Error, String>("detail"));
        errors = FXCollections.observableArrayList();
        logView.setItems(errors);

        mainPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            updatePaneAnchor();
        });
        mainPane.heightProperty().addListener((observable, oldValue, newValue) -> {
            updatePaneAnchor();
        });
        updatePaneAnchor();

        listWorkerColumn = new ArrayList<>();
        listOfficerColumn = new ArrayList<>();
    }

    private void updatePaneAnchor() {
        double paneWidth = mainPane.getWidth();
        double paneHeight = mainPane.getHeight();

        System.out.println("Pane width " + paneWidth);
        if (paneWidth > 0 && paneHeight > 0) {

            AnchorPane.setLeftAnchor(logPane, paneWidth / 2 + 10);
            AnchorPane.setRightAnchor(logPane, 0.0 + 10);
            AnchorPane.setTopAnchor(logPane, 0.0 + 10);
            AnchorPane.setBottomAnchor(logPane, paneHeight / 2 + 10);

            AnchorPane.setTopAnchor(resultPane, paneHeight / 2);
            AnchorPane.setBottomAnchor(resultPane, 0.0);
            AnchorPane.setLeftAnchor(resultPane, 0.0);
            AnchorPane.setRightAnchor(resultPane, 0.0);

            AnchorPane.setRightAnchor(importPane, paneWidth / 2);
            AnchorPane.setLeftAnchor(importPane, 0.0);
            AnchorPane.setTopAnchor(importPane, 0.0);
            AnchorPane.setBottomAnchor(importPane, paneHeight / 2);

        }
    }

    @FXML
    public void chooseInFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) importPane.getScene().getWindow();
        inFile = fileChooser.showOpenDialog(stage);
        inTextField.setText(inFile.toString());

    }

    public void chooseOutFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) importPane.getScene().getWindow();
        outFile = fileChooser.showOpenDialog(stage);
        outTextField.setText(outFile.toString());
    }

    public void convert(ActionEvent actionEvent) throws CsvValidationException, IOException {
        errors.clear();
        logView.refresh();
        boolean isFileNotNull = true;
        if (startDatePicker.getValue() == null || endDatePicker.getValue() == null) {
            errors.add(new Error("ERR005", "You haven't choose the date"));
            isFileNotNull = false;
        } else if (startDatePicker.getValue().isAfter(endDatePicker.getValue())) {
            errors.add(new Error("ERR006", "Start date is after end date"));
            isFileNotNull = false;
        }
        if (empType.getValue() == null) {
            errors.add(new Error("ERR004", "You haven't choose employee type"));
            isFileNotNull = false;
        }
        if (inFile == null) {
            errors.add(new Error("ERR000", "inFile isn't been provided"));
            isFileNotNull = false;
        }
        if (outFile == null) {
            errors.add(new Error("ERR000", "outFile isn't been provided"));
            isFileNotNull = false;
        }
        if (isFileNotNull) {
            FileHandler inFileHandler = new FileHandler(inFile, Kind.Worker);
            FileHandler outFileHandler = new FileHandler(outFile, Kind.Worker);


            if (!inFileHandler.getStatus().isEmpty() || !outFileHandler.getStatus().isEmpty()) {
                inFileHandler.getStatus().forEach((value) -> {
                    errors.add(value);
                });
                outFileHandler.getStatus().forEach((value) -> {
                    errors.add(value);
                });
            } else {
                List<Report> result;
                if (empType.getValue() == "Workers") {
                    rbuilder = new WorkerReportBuilder(Kind.Worker, inFileHandler, outFileHandler);
                    result = rbuilder.getReports();
                } else {
                    rbuilder = new OfficerReportBuilder(Kind.Officer, inFileHandler, outFileHandler);
                    result = rbuilder.getReports();
                }
                setResultPane(result);
            }
        }
        logView.refresh();
        convertBtn.setDisable(true);
    }

    private void setOfficerResultPane(List<Report> result) {
        officerShiftDataTableView = FXCollections.observableArrayList();
        LocalDate startDay = startDatePicker.getValue();
        LocalDate endDay = endDatePicker.getValue();
        int duration = endDay.getDayOfYear() - startDay.getDayOfYear() + 1;

        for (var report : rbuilder.getReports()) {
            var shiftReport = ((OfficerReport) report).getShiftReport();
            for (int i = 0; i < 4; i++) {
                String ID = report.getEmployee().getId();
                String type = "null";
                if (i == 0) {
                    type = "Morning session";
                } else if (i == 1) {
                    type = " Afternoon session";
                } else if (i == 2) {
                    type = "Hour come late";
                } else if (i == 3) {
                    type = "Hours early leave";
                }
                List<String> tempData = new ArrayList<>(Collections.nCopies(duration, "Null"));
                for (var daydata : shiftReport.entrySet()) {
                    LocalDate tempDay = Instant.ofEpochSecond(daydata.getKey())
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    int pos = tempDay.getDayOfYear() - startDay.getDayOfYear();
                    if (pos >= 0 && pos < duration)
                        tempData.set(pos, daydata.getValue().getDataElement(i + 1));
                }
                OfficerShiftData officerShiftData = new OfficerShiftData(ID, type, tempData);
                officerShiftDataTableView.add(officerShiftData);
            }
        }

        // Create columns for id and type
        officerIDCol.setMinWidth(100);
        officerIDCol.setMaxWidth(100);
        officerIDCol.setCellValueFactory(cellData -> cellData.getValue().idProperty());

        dayOfficerCol.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        dayOfficerCol.setMinWidth(100);
        dayOfficerCol.setMaxWidth(100);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate currentDate = startDatePicker.getValue();
        for (int i = 0; i < duration; i++) {
            int index = i;
            TableColumn<OfficerShiftData, String> dataColumn = new TableColumn<>(currentDate.getDayOfWeek().toString() + " " + currentDate.format(formatter));
            dataColumn.setMinWidth(180);
            dataColumn.setMaxWidth(180);
            dataColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getData().get(index)));
            officersResultView.getColumns().add(dataColumn);
            currentDate = currentDate.plusDays(1);
            listOfficerColumn.add(dataColumn);
        }

        officersResultView.setItems(officerShiftDataTableView);
        officersResultView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
    }

    private void setWorkerResultPane(List<Report> result) {

        workerShiftDataTableView = FXCollections.observableArrayList();
        LocalDate startDay = startDatePicker.getValue();
        LocalDate endDay = endDatePicker.getValue();
        int duration = endDay.getDayOfYear() - startDay.getDayOfYear() + 1;

        for (var report : rbuilder.getReports()) {
            var shiftReport = ((WorkerReport) report).getShiftReport();
            for (int i = 0; i < 3; i++) {
                String ID = report.getEmployee().getId();
                String type = "Shift " + (i + 1);
                List<Double> tempData = new ArrayList<>(Collections.nCopies(duration, 0.0));
                for (var daydata : shiftReport.entrySet()) {
                    LocalDate tempDay = Instant.ofEpochSecond(daydata.getKey())
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    int pos = tempDay.getDayOfYear() - startDay.getDayOfYear();
                    if (pos >= 0 && pos < duration)
                        tempData.set(pos, daydata.getValue().getElement(i + 1));
                }
                DayShiftData dayShiftData = new DayShiftData(ID, type, tempData);
                workerShiftDataTableView.add(dayShiftData);
            }
        }

        // Create columns for id and type
        workerIDCol.setMinWidth(100);
        workerIDCol.setMaxWidth(100);
        workerIDCol.setCellValueFactory(cellData -> cellData.getValue().idProperty());

        dayWorkerCol.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        dayWorkerCol.setMinWidth(200);
        dayWorkerCol.setMaxWidth(200);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate currentDate = startDatePicker.getValue();
        for (int i = 0; i < duration; i++) {
            int index = i;
            TableColumn<DayShiftData, Double> dataColumn = new TableColumn<>(currentDate.getDayOfWeek().toString() + " " + currentDate.format(formatter));
            dataColumn.setMinWidth(180);
            dataColumn.setMaxWidth(180);
            dataColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getData().get(index)));
            workersResultView.getColumns().add(dataColumn);
            currentDate = currentDate.plusDays(1);
            listWorkerColumn.add(dataColumn);
        }

        workersResultView.setItems(workerShiftDataTableView);
        workersResultView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

    }

    private void setResultPane(List<Report> result) {
        if (empType.getValue() == "Workers") {
            setWorkerResultPane(result);
            workersResultView.toFront();
        } else {
            setOfficerResultPane(result);
            officersResultView.toFront();
        }
    }

    public void accept(ActionEvent actionEvent) {
        if (empType.getValue() == "Workers") {

        }
    }

    public void cancel(ActionEvent actionEvent) {
        errors.clear();
        for (var col : listWorkerColumn) {
            workersResultView.getColumns().remove(col);
        }
        for (var col : listOfficerColumn) {
            officersResultView.getColumns().remove(col);
        }
        if (empType.getValue() == "Workers") {
            if (workersResultView != null && !workersResultView.getItems().isEmpty()) {
                workerShiftDataTableView.clear();
            }
        } else if (empType.getValue() == "Officer") {
            if (officersResultView != null && !officersResultView.getItems().isEmpty()) {
                officerShiftDataTableView.clear();
            }
        }
        listOfficerColumn.clear();
        listWorkerColumn.clear();
        logView.refresh();
        officersResultView.refresh();
        workersResultView.refresh();
        convertBtn.setDisable(false);
    }
}
