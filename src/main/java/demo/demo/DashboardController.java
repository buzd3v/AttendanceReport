package demo.demo;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    public StackPane mainStackPane;
    @FXML
    public BorderPane mainBorderPane;
    @FXML
    public AnchorPane paneContent;
    @FXML
    public AnchorPane paneHome;
    @FXML
    public ImageView imgHome;
    @FXML
    public AnchorPane paneReport;
    @FXML
    public AnchorPane paneImport;
    @FXML
    public AnchorPane paneChangeReq;

    //for future use
    @FXML
    public AnchorPane paneAccount;
    @FXML
    public AnchorPane paneSetting;
    @FXML
    public AnchorPane paneShortNav;
    @FXML
    public JFXButton btnHome;
    @FXML
    public JFXButton btnReport;
    @FXML
    public JFXButton btnImport;

    //for future use
    @FXML
    public JFXButton btnAccount;
    @FXML
    public JFXButton btnSetting;

    //short nav button
    @FXML
    public JFXButton btnhamburger;
    public AnchorPane paneChangeAttendanceData;

    FXMLLoader monthlyReportUILoader;
    FXMLLoader importReportUILoader;

    boolean isOpen = false;
    private FXMLLoader changeReqLoader;
    public void actionDrawer(ActionEvent actionEvent) {
        if (!isOpen) {
            paneShortNav.setPrefWidth(250);
            paneContent.setEffect(new BoxBlur(2, 2, 1));
            paneContent.setDisable(true);
            isOpen = true;
        } else {
            paneShortNav.setPrefWidth(60);
            paneContent.setEffect(null);
            paneContent.setDisable(false);
            isOpen = false;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initPanes();
        paneHome.toFront();
    }

    private <T> void setAnchorConstraint(T pane) {
        Double v = 0.0;
        AnchorPane.setTopAnchor((Node) pane, v);
        AnchorPane.setBottomAnchor((Node) pane, v);
        AnchorPane.setLeftAnchor((Node) pane, v);
        AnchorPane.setRightAnchor((Node) pane, v);
    }

//    private void setAnchorConstraint(AnchorPane borderPane) {
//
//        Double v = 0.0;
//        AnchorPane.setTopAnchor(borderPane,v);
//        AnchorPane.setBottomAnchor(borderPane,v);
//        AnchorPane.setLeftAnchor(borderPane,v);
//        AnchorPane.setRightAnchor(borderPane,v);
//
//    }
    private void setAnchorConstraint(AnchorPane pane) {
        Double v = 0.0;
        AnchorPane.setTopAnchor(pane, v);
        AnchorPane.setBottomAnchor(pane, v);
        AnchorPane.setLeftAnchor(pane, v);
        AnchorPane.setRightAnchor(pane, v);
    }
    private void initPanes(){
        try{
            changeReqLoader = new FXMLLoader(getClass().getResource("ChangeReq.fxml"));
            AnchorPane anchorPane= (AnchorPane) changeReqLoader.load();
            paneChangeReq.getChildren().setAll(anchorPane);
            setAnchorConstraint(anchorPane);

            importReportUILoader = new FXMLLoader(getClass().getResource("/ui/ImportReport_View.fxml"));
            AnchorPane Pane = importReportUILoader.load();
            paneImport.getChildren().setAll(Pane);
            this.setAnchorConstraint(Pane);

            monthlyReportUILoader = new FXMLLoader(getClass().getResource("/ui/Report_View.fxml"));
            StackPane stackPane = monthlyReportUILoader.load();
            paneReport.getChildren().setAll(stackPane);
            setAnchorConstraint(stackPane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void openMonthlyReportUI(ActionEvent actionEvent) {
        paneReport.toFront();

    }

    public void openImportReportUI(ActionEvent actionEvent) {
        paneImport.toFront();
    }

    public void ChangeType(ActionEvent actionEvent) {
        paneChangeReq.toFront();
    }
}