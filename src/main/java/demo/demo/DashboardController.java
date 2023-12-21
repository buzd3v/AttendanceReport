package demo.demo;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
    public AnchorPane paneBaoCaoThang;

    @FXML
    public AnchorPane paneChangeReq;

    @FXML
    public AnchorPane paneBaoCaoChiTiet;

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

    @FXML
    public JFXButton btnBaoCaoThang;
    @FXML
    public JFXButton btnBaoCaoChiTiet;

    //for future use
    @FXML
    public JFXButton btnAccount;
    @FXML
    public JFXButton btnSetting;

    //short nav button
    @FXML
    public JFXButton btnhamburger;

    FXMLLoader monthlyReportUILoader;
    FXMLLoader importReportUILoader;

    boolean isOpen = false;
    private FXMLLoader changeReqLoader;
    private FXMLLoader changeBaoCaoChiTietLoader;

    public void actionDrawer(ActionEvent actionEvent) {
        if(!isOpen)
        {
            paneShortNav.setPrefWidth(250);
            paneContent.setEffect(new BoxBlur(2,2,1));
            paneContent.setDisable(true);
            isOpen = true;
        }
        else{
            paneShortNav.setPrefWidth(60);
            paneContent.setEffect(null);
            paneContent.setDisable(false);
            isOpen = false;
        }
//        FontAwesomeIcon.QUESTION
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initPanes();
        paneHome.toFront();
    }
    private void setAnchorConstraint(BorderPane borderPane) {

        Double v = 0.0;
        AnchorPane.setTopAnchor(borderPane,v);
        AnchorPane.setBottomAnchor(borderPane,v);
        AnchorPane.setLeftAnchor(borderPane,v);
        AnchorPane.setRightAnchor(borderPane,v);

    }

    private void setAnchorConstraint(AnchorPane borderPane) {

        Double v = 0.0;
        AnchorPane.setTopAnchor(borderPane,v);
        AnchorPane.setBottomAnchor(borderPane,v);
        AnchorPane.setLeftAnchor(borderPane,v);
        AnchorPane.setRightAnchor(borderPane,v);

    }
    private void initPanes(){
        try{
//            monthlyReportUILoader = new FXMLLoader(getClass().getResource("Report_View.fxml"));
//            BorderPane borderPane = (BorderPane) monthlyReportUILoader.load();
//            paneReport.getChildren().setAll(borderPane);
//            setAnchorConstraint(borderPane);

            changeReqLoader = new FXMLLoader(getClass().getResource("ChangeReq.fxml"));
            AnchorPane anchorPane= (AnchorPane) changeReqLoader.load();
            paneChangeReq.getChildren().setAll(anchorPane);
            setAnchorConstraint(anchorPane);

            changeBaoCaoChiTietLoader = new FXMLLoader(getClass().getResource("BaocaoChiTiet.fxml"));
            AnchorPane anchorPane1= (AnchorPane) changeBaoCaoChiTietLoader.load();
            paneBaoCaoChiTiet.getChildren().setAll(anchorPane1);
            setAnchorConstraint(anchorPane1);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public void openBaoCaoChiTiet(ActionEvent actionEvent) {
        paneBaoCaoChiTiet.toFront();
    }
    public void openBaoCaoThang(ActionEvent actionEvent){

    }

    public void ChangeType(ActionEvent actionEvent) {
        paneChangeReq.toFront();
    }
}