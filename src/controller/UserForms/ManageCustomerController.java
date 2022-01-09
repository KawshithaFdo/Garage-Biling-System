package controller.UserForms;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import controller.DataController.AdminController;
import controller.DataController.CustomerController;
import controller.DataController.VehicleController;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Admin;
import model.Customer;
import model.Vehicle;
import util.ValidationUtil;
import view.TM.AdminTm;
import view.TM.CustomerTm;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class ManageCustomerController{
    public TextField CustName;
    public TextField CustAddress;
    public TextField CustContact;
    public TextField CustNIC;
    public Label CustId;
    public JFXButton btnAdd;
    public AnchorPane ManageCustomerContext;
    public TableView<CustomerTm> tblCustomer;
    public TableColumn colCust_Id;
    public TableColumn colCust_Name;
    public TableColumn colAddress;
    public TableColumn colContact;
    public TableColumn colNic;
    public TableColumn colUserId;
    public TextField CustEditName;
    public TextField CustEditaddress;
    public TextField CustEditContact;
    public TextField CustEditNIC;
    public JFXButton btnDelete;
    public JFXButton btnUpdate;
    public String UserId;
    public String EditUser;
    public TextField CustEditId;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern idPattern = Pattern.compile("^[C](-00)[0-9]{1,4}$");
    Pattern namePattern = Pattern.compile("^[[A-z]{1,30}[ ]?]+$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9/ ]{6,30}$");
    Pattern contactPattern=Pattern.compile("^[0][0-9]{2,3}[-]?[]0-9]{6,7}?$");
    Pattern nicpattern=Pattern.compile("^[0-9]{9,12}[vV]?$");

    public void initialize() throws SQLException {
        CustName.requestFocus();
        btnAdd.setDisable(true);
        addValidations();
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        try {

        colCust_Id.setCellValueFactory(new PropertyValueFactory<>("cust_Id"));
        colCust_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nIC"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("user_Id"));

        tblCustomer.getColumns().setAll(colCust_Id,colCust_Name,colAddress,colContact,colNic,colUserId);
        tblCustomer.getItems().setAll(loadTableData());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            CustId.setText(new CustomerController().getCustomerId());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(false);
        });

    }

    private void addValidations() {
        map.put(CustName,namePattern);
        map.put(CustAddress,addressPattern);
        map.put(CustContact,contactPattern);
        map.put(CustNIC,nicpattern);
    }

    public ObservableList<CustomerTm> loadTableData() throws SQLException, ClassNotFoundException {

        List<Customer> allCustomers = new CustomerController().getAllCustomers();
        ObservableList<CustomerTm> custList = FXCollections.observableArrayList();
        for (Customer customer : allCustomers) {
            custList.add(new CustomerTm(
                    customer.getCust_ID(),
                    customer.getName(),
                    customer.getAddress(),
                    customer.getContact(),
                    customer.getnIC(),
                    customer.getUser_ID()
            ));
        }
        return custList;

    }

    public void AddCustomer(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String name=CustName.getText();
        String address=CustAddress.getText();
        String contact=CustContact.getText();
        String nic=CustNIC.getText();

        Customer c=new CustomerController().getCustomerUsingNIC(nic);
        if (c!=null){
            new Alert(Alert.AlertType.WARNING,"This NIC is Exists").show();
            clear();
            return;

        }

        Customer customer=new Customer(CustId.getText(),name,address,contact,nic,UserId);
        if(new CustomerController().saveCustomer(customer)){
            new Alert(Alert.AlertType.CONFIRMATION,"Successfully.").show();
            tblCustomer.getItems().setAll(loadTableData());
            clear();
        }else{
            new Alert(Alert.AlertType.ERROR,"Try Again.").show();
        }
    }

    public void OpenEditData(ActionEvent actionEvent) {
        try {
            loadUpdateData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loadUpdateData() throws SQLException, ClassNotFoundException {
        Customer c=new CustomerController().getCustomerUsingId(CustEditId.getText());
        if (c==null){
            new Alert(Alert.AlertType.ERROR,"Enter Valid Customer Id.").show();
        }else {
            CustEditName.setText(c.getName());
            CustEditaddress.setText(c.getAddress());
            CustEditContact.setText(c.getContact());
            CustEditNIC.setText(c.getnIC());
            btnUpdate.setDisable(false);
            EditUser=c.getUser_ID();
        }
    }

    public void UpdateCustomer(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
       Customer customer=new Customer(CustEditId.getText(),CustEditName.getText(),CustEditaddress.getText(),CustEditContact.getText(),
               CustEditNIC.getText(),EditUser);
       if (new CustomerController().updateCustomer(customer)){
           new Alert(Alert.AlertType.CONFIRMATION,"Successfully Updated.").show();
           tblCustomer.getItems().setAll(loadTableData());
           clear();
           btnUpdate.setDisable(true);
       }else{
           new Alert(Alert.AlertType.WARNING,"Try Again.").show();
       }
    }

    public void DeleteCustomer(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String id = tblCustomer.getSelectionModel().getSelectedItem().getCust_Id();
        if (new CustomerController().deleteCustomer(id)){
            new Alert(Alert.AlertType.CONFIRMATION,"Deleted.").show();
            tblCustomer.getItems().setAll(loadTableData());
            clear();
            id="";
            btnDelete.setDisable(true);
        }else{
            new Alert(Alert.AlertType.ERROR,"Try again.").show();
        }
    }

    public void clear() throws SQLException, ClassNotFoundException {
        CustId.setText(new CustomerController().getCustomerId());
        CustName.setText("");
        CustAddress.setText("");
        CustContact.setText("");
        CustNIC.setText("");
        CustEditId.setText("");
        CustEditName.setText("");
        CustEditaddress.setText("");
        CustEditContact.setText("");
        CustEditNIC.setText("");
    }

    public void OnEditKeyReleased(KeyEvent keyEvent) {
        editValidations();
        Object response = ValidationUtil.validate(map,btnUpdate);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                btnUpdate.requestFocus();
                CustName.requestFocus();
            }
        }

    }

    private void editValidations() {
        map.clear();
        map.put(CustEditId,idPattern);
        map.put(CustEditName,namePattern);
        map.put(CustEditaddress,addressPattern);
        map.put(CustEditContact,contactPattern);
        map.put(CustEditNIC,nicpattern);
    }

    public void OnKeyReleased(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnAdd);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                btnAdd.requestFocus();
            }
        }
    }
}
