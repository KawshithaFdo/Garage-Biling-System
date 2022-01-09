package controller;

import com.jfoenix.controls.JFXButton;
import controller.DataController.AdminController;
import controller.DataController.UserController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.Admin;
import model.User;
import util.ValidationUtil;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class AddNewAccountController {
    public AnchorPane CreateUserAccount_onAction;
    public TextField UserName;
    public TextField UserAddress;
    public TextField UserContact;
    public TextField UserNIC;
    public TextField UserPassword;
    public Label UserId;
    public JFXButton btncreate;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern namePattern = Pattern.compile("^[[A-z]{1,30}[ ]?]+$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9/ ]{6,30}$");
    Pattern contactPattern=Pattern.compile("^[0][0-9]{2,3}[-]?[]0-9]{6,7}?$");
    Pattern nicpattern=Pattern.compile("^[0-9]{9,12}[vV]?$");
    Pattern passwordPattern = Pattern.compile("^[A-z0-9]{1,15}$");


    public void initialize(){
            btncreate.setDisable(true);
            storeValidations();
        try {
            UserId.setText(new UserController().getUserId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void storeValidations() {
        map.put(UserName, namePattern);
        map.put(UserAddress, addressPattern);
        map.put(UserContact, contactPattern);
        map.put(UserNIC, nicpattern);
        map.put(UserPassword, passwordPattern);
    }

    public void CreateNewUser_OnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
            SaveUser();
    }

    private void SaveUser() throws SQLException, ClassNotFoundException {
        User user = new User(UserId.getText(), UserName.getText(), UserAddress.getText(), UserContact.getText(),
                UserNIC.getText(), UserPassword.getText());
        if (new UserController().saveUser(user)) {
            new Alert(Alert.AlertType.CONFIRMATION, "User Account Successfully Created.").show();
            clear();
        } else {
            new Alert(Alert.AlertType.ERROR, "Try Again.").show();
        }
    }

     void clear(){
        UserId.setText("");
        UserName.setText("");
        UserAddress.setText("");
        UserContact.setText("");
        UserNIC.setText("");
        UserPassword.setText("");
    }

    public void keyReleased(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btncreate);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                btncreate.requestFocus();
            }
        }
    }
}
