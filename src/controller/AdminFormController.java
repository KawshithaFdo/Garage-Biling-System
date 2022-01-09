package controller;

import com.jfoenix.controls.JFXButton;
import controller.AdminForms.*;
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
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class AdminFormController{
    public StackPane StackRootContext;
    public Label Time;
    public Label Date;
    public Label AdminName;
    public JFXButton btnlogout;
    public String id;
    public JFXButton btn_Management;
    public JFXButton btnmanagemechanic;
    public JFXButton btnmanagesupplier;
    public JFXButton btnmanagespareparts;
    public JFXButton btnreports;

    ArrayList<JFXButton> list=new ArrayList();

    public void initialize(){
        dateandtime();

        list.add(btn_Management);
        list.add(btnmanagemechanic);
        list.add(btnmanagesupplier);
        list.add(btnmanagespareparts);
        list.add(btnreports);
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

    public void LogOut(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/Login.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) StackRootContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void OpenReports(ActionEvent actionEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/AdminForms/Reports.fxml"));
        pane = fxmlLoader.load();
        StackRootContext.getChildren().setAll(pane);
    }

    public void ManageMechanic(ActionEvent actionEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/AdminForms/ManageMechanic.fxml"));
        pane = fxmlLoader.load();
        ManageMechanicController con=fxmlLoader.<ManageMechanicController>getController();
        con.AdminId=id;
        StackRootContext.getChildren().setAll(pane);
    }

    public void ManageSupplier() throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/AdminForms/ManageSupplier.fxml"));
        pane = fxmlLoader.load();
        ManageSupplierController con=fxmlLoader.<ManageSupplierController>getController();
        con.AdminId=id;
        StackRootContext.getChildren().setAll(pane);
    }

    public void ManageSpareParts(ActionEvent actionEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/AdminForms/ManageSpareParts.fxml"));
        pane = fxmlLoader.load();
        StackRootContext.getChildren().setAll(pane);
    }

    public void manegement(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/Management.fxml"));
        Parent parent = loader.load();
        ManagementController con=loader.<ManagementController>getController();
        con.id=id;
        Stage window = (Stage) StackRootContext.getScene().getWindow();
        window.setScene(new Scene(parent));
        // window.setFullScreen(true);//
        window.show();
    }

    public void mouseclicked(MouseEvent mouseEvent) {

        for (int i=0;i<list.size();i++){
            if(mouseEvent.getTarget().equals(list.get(i))){
                list.get(i).setStyle("-fx-background-color:deepskyblue");
            }else {
                list.get(i).setStyle("-fx-background-color: transparent");
            }
        }
    }
}
