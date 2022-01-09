package controller;

import com.jfoenix.controls.JFXButton;
import controller.Management.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ManagementController {
    public StackPane panelRoot;
    public String id;
    public JFXButton btnBill;
    public JFXButton btnSupplier;
    public JFXButton btnMechanic;
    public JFXButton btnVehicle;
    public JFXButton btncustomer;
    public JFXButton btnUser;

    ArrayList<JFXButton> list=new ArrayList();

    public void initialize(){
        list.add(btnBill);
        list.add(btnSupplier);
        list.add(btnMechanic);
        list.add(btnVehicle);
        list.add(btncustomer);
        list.add(btnUser);
    }

    public void goHome(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/AdminForm.fxml"));
        Parent parent = loader.load();
        Stage window = (Stage) panelRoot.getScene().getWindow();
        window.setScene(new Scene(parent));
        // window.setFullScreen(true);//
        window.show();
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        for (int i=0;i<list.size();i++){
            if(mouseEvent.getTarget().equals(list.get(i))){
                list.get(i).setStyle("-fx-background-color:deepskyblue");
            }else {
                list.get(i).setStyle("-fx-background-color: transparent");
            }
        }
    }

    public void openUser(ActionEvent actionEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/management/User.fxml"));
        pane = fxmlLoader.load();
        panelRoot.getChildren().setAll(pane);
    }

    public void openCustomer(ActionEvent actionEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/management/Customer.fxml"));
        pane = fxmlLoader.load();
        panelRoot.getChildren().setAll(pane);
    }

    public void openVehicle(ActionEvent actionEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/management/Vehicle.fxml"));
        pane = fxmlLoader.load();
        panelRoot.getChildren().setAll(pane);
    }

    public void openMechanic(ActionEvent actionEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/management/Mechanic.fxml"));
        pane = fxmlLoader.load();
        panelRoot.getChildren().setAll(pane);
    }

    public void opensupplier(ActionEvent actionEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/management/Supplier.fxml"));
        pane = fxmlLoader.load();
        SupplierFormController con =fxmlLoader.<SupplierFormController>getController();
        con.Admin_Id=id;
        panelRoot.getChildren().setAll(pane);
    }

    public void openBill(ActionEvent actionEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/management/Bill.fxml"));
        pane = fxmlLoader.load();
        panelRoot.getChildren().setAll(pane);
    }
}
