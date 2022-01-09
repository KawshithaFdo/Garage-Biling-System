package controller;

import com.jfoenix.controls.JFXButton;
import com.sun.scenario.effect.impl.sw.java.JSWBlend_BLUEPeer;
import controller.UserForms.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserFormController {
    public Label UserName;
    public Label Date;
    public Label Time;
    public StackPane stackRoot;
    public String id;
    public JFXButton btnmanageCustomer;
    public JFXButton btnManageVehicle;
    public JFXButton btnManageBooking;
    public JFXButton btnMakePayment;
    public JFXButton btnmanagePayment;
    public JFXButton btnReports;

    ArrayList<JFXButton> list=new ArrayList();

    public void initialize(){
        dateandtime();

        list.add(btnManageVehicle);
        list.add(btnmanageCustomer);
        list.add(btnManageBooking);
        list.add(btnMakePayment);
        list.add(btnmanagePayment);
        list.add(btnReports);
    }

    private void dateandtime() {
        java.util.Date date=new Date();
        SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd");
        Date.setText(f.format(date));

        Timeline time=new Timeline(new KeyFrame(Duration.ZERO, e->{
            LocalTime currentTime = LocalTime.now();
            Time.setText(
                    currentTime.getHour()+":"+currentTime.getMinute()+":"+
                            currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    public void GoHome(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/Login.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) stackRoot.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void MakePayment(ActionEvent actionEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/UserForms/MakePayment.fxml"));
        pane = fxmlLoader.load();
        MakePaymentController con=fxmlLoader.<MakePaymentController>getController();
        con.UserId=id;
        con.UserName=UserName.getText();
        stackRoot.getChildren().setAll(pane);
    }

    public void OpenReports(ActionEvent actionEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/UserForms/Reports.fxml"));
        pane = fxmlLoader.load();
        ReportsController con=fxmlLoader.<ReportsController>getController();
        con.username=UserName.getText();
        stackRoot.getChildren().setAll(pane);
    }

    public void ManageCustomer(ActionEvent actionEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/UserForms/ManageCustomer.fxml"));
        pane = fxmlLoader.load();
        ManageCustomerController con=fxmlLoader.<ManageCustomerController>getController();
        con.UserId=id;
        stackRoot.getChildren().setAll(pane);
    }

    public void ManageVehicle(ActionEvent actionEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/UserForms/ManageVehicle.fxml"));
        pane = fxmlLoader.load();
        stackRoot.getChildren().setAll(pane);
    }

    public void ManageBooking(ActionEvent actionEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/UserForms/ManageBooking.fxml"));
        pane = fxmlLoader.load();
        ManageBookingController con=fxmlLoader.<ManageBookingController>getController();
        con.U_Id=id;
        stackRoot.getChildren().setAll(pane);
    }

    public void UpdatePayments(ActionEvent actionEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/UserForms/managepayment.fxml"));
        pane = fxmlLoader.load();
        ManagePaymentController con=fxmlLoader.<ManagePaymentController>getController();
        con.UserName=UserName.getText();
        stackRoot.getChildren().setAll(pane);
    }

    public void mouseClick(MouseEvent mouseEvent) {

        for (int i=0;i<list.size();i++){
            if(mouseEvent.getTarget().equals(list.get(i))){
                list.get(i).setStyle("-fx-background-color:deepskyblue");
            }else {
                list.get(i).setStyle("-fx-background-color: transparent");
            }
        }
    }
}

