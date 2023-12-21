module demo.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.jfoenix;

    requires de.jensd.fx.glyphs.fontawesome;
    requires de.jensd.fx.glyphs.commons;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens demo.demo to javafx.fxml;
    exports demo.demo;
//    opens demo.demo.dashboard;
    opens demo.demo.utilities;
    opens demo.demo.changeReq;
}