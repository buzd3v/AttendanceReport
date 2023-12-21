module demo.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.jfoenix;

    requires de.jensd.fx.glyphs.fontawesome;
    requires de.jensd.fx.glyphs.commons;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.opencsv;
    requires java.sql;
//    requires poi.ooxml;
//    requires poi;

    opens demo.demo to javafx.fxml;
    exports demo.demo;
    opens demo.demo.utilities;
    opens demo.demo.ImportReport;
    opens demo.demo.ImportReport.Employee;
    opens demo.demo.ImportReport.Error;
    opens demo.demo.ImportReport.FileHandler;
    opens demo.demo.DB;


}