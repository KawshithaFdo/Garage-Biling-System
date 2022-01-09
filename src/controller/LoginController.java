package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import controller.DataController.AdminController;
import controller.DataController.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Admin;
import model.User;
import util.ValidationUtil;


import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class LoginController {
    public AnchorPane UserLoginContext;
    public JFXButton userLogIn;
    public String Name;
    public TextField userPassword;
    public TextField userId;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern idPattern = Pattern.compile("^[AU](-00)[0-9]{1,4}$");
    Pattern passwordPattern = Pattern.compile("^[A-z0-9]{1,15}$");
    
    public void initialize(){
        userLogIn.setDisable(true);
        storeValidations();
    }

    private void storeValidations() {
        map.put(userId, idPattern);
        map.put(userPassword, passwordPattern);
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,userLogIn);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                userLogIn.requestFocus();
            }
        }
    }

    public void UserLogIn_OnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {

        if(userPassword.getText().equals("")) {
            new Alert(Alert.AlertType.WARNING, "Enter UserId.").show();
            return;
        }
        LoadActor();

    }

    public void LoadActor() throws IOException, SQLException, ClassNotFoundException {
        User u1= new UserController().getUser(userId.getText());
        String id=(userId.getText().split("-")[0]);

        if(id.equals("U")){
            if((userPassword.getText().equals(u1.getPassword()))){
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/UserForm.fxml"));
                Parent parent = loader.load();
                UserFormController controller = loader.<UserFormController>getController();
                controller.UserName.setText(Name);
                controller.id=userId.getText();
                Stage window = (Stage) UserLoginContext.getScene().getWindow();
                window.setScene(new Scene(parent));
               // window.setFullScreen(true);//
                window.show();
            }else{
                new Alert(Alert.AlertType.WARNING,"Enter Valid Password.").show();
                userPassword.setText("");
                userPassword.requestFocus();
            }
        }else if(id.equals("A")){
            Admin a2=new AdminController().getAdmin(userId.getText());
            if((userPassword.getText().equals(a2.getPassword()))){
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/AdminForm.fxml"));
                Parent parent = loader.load();
                AdminFormController controller = loader.<AdminFormController>getController();
                controller.AdminName.setText(Name);
                controller.id=userId.getText();
                Stage window = (Stage) UserLoginContext.getScene().getWindow();
                window.setScene(new Scene(parent));
           //     window.setFullScreen(true);//
                window.show();
            }else{
                new Alert(Alert.AlertType.WARNING,"Enter Valid Password.").show();
                userPassword.setText("");
            }
        }else{
            new Alert(Alert.AlertType.WARNING, "Enter Valid Id.").show();
        }
    }

    public void newUserAccount_OnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/AddNewAccount.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void OpenUserIdOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        User u2= new UserController().getUser(userId.getText());
        Admin a1=new AdminController().getAdmin(userId.getText());
        if (u2==null) {
            if (a1==null) {
                new Alert(Alert.AlertType.WARNING, "Wrong ID.").show();
            } else {
                Name=a1.getName();
            }
        } else {
            Name=u2.getName();
        }

    }

}
