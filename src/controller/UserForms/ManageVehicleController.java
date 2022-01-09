package controller.UserForms;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import controller.DataController.AdminController;
import controller.DataController.CustomerController;
import controller.DataController.VehicleController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Admin;
import model.Customer;
import model.Vehicle;
import util.ValidationUtil;
import view.TM.AdminTm;
import view.TM.CustomerTm;
import view.TM.VehicleTm;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class ManageVehicleController {
    public AnchorPane ManageVehicleContext;
    public JFXButton btnAddVehicle;
    public TableView<VehicleTm> tblVehicle;
    public TableColumn colCustId;
    public TableColumn colRegNo;
    public TableColumn colVName;
    public TableColumn colBrand;
    public TableColumn colYom;
    public TableColumn colFuelType;
    public JFXButton btnEditVehicle1;
    public JFXButton btnDeleteVehicle;
    public TextField Reg_No;
    public TextField FuelType;
    public TextField EditName;
    public TextField EditBrand;
    public TextField EditYom;
    public TextField EditFuelType;
    public TextField CustId;
    public TextField CustName;
    public TextField Name;
    public TextField Brand;
    public TextField Yom;
    public TextField EditRegNo;
    public String C_id;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern custId = Pattern.compile("^[C](-00)[0-9]{1,4}$");
    Pattern regNo = Pattern.compile("^[0-9]{2}[-][0-9]{4}|[A-Z]{2,3}[-][0-9]{4}$");
    Pattern namePattern = Pattern.compile("^[A-Z][a-z]{1,30}$");
    Pattern brandPattern = Pattern.compile("^[A-Z][a-z]{1,30}$");
    Pattern yomPattern = Pattern.compile("^[1-2]{1}[0-9]{3}$");
    Pattern fueltypePattern=Pattern.compile("^[A-z]{1,}$");

    public void initialize(){
        btnAddVehicle.setDisable(true);
        ValidateData();
        btnDeleteVehicle.setDisable(true);
        btnEditVehicle1.setDisable(true);
        try {
            loadTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
     tblVehicle.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
         btnDeleteVehicle.setDisable(false);
     });

    }

    private void ValidateData() {
        map.put(CustId,custId);
        map.put(Reg_No,regNo);
        map.put(Name,namePattern);
        map.put(Brand,brandPattern);
        map.put(Yom,yomPattern);
        map.put(FuelType,fueltypePattern);
    }

    public void OpenData(ActionEvent actionEvent) {
        try {
            loadUpdatedata();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loadUpdatedata() throws SQLException, ClassNotFoundException {
       Vehicle vehicle=new VehicleController().getVehicleUsingId(EditRegNo.getText());
       if (vehicle==null){
           new Alert(Alert.AlertType.ERROR,"Enter Valid Registration No.").show();
       }else {
           EditName.setText(vehicle.getName());
           EditBrand.setText(vehicle.getBrand());
           EditYom.setText(vehicle.getYom());
           EditFuelType.setText(vehicle.getFuel());
           C_id = vehicle.getCust_Id();
           btnEditVehicle1.setDisable(false);
       }
    }

    public void loadTable() throws SQLException, ClassNotFoundException {
        List<Vehicle> allvehicles=new VehicleController().getAllVehicles();
        ObservableList<VehicleTm> vehicle=FXCollections.observableArrayList();
            for (Vehicle v:allvehicles) {
                vehicle.add(new VehicleTm(
                        v.getReg_No(),
                        v.getName(),
                        v.getBrand(),
                        v.getYom(),
                        v.getFuel(),
                        v.getCust_Id()
                ));

            }

        colRegNo.setCellValueFactory(new PropertyValueFactory<>("reg_No"));
        colVName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colYom.setCellValueFactory(new PropertyValueFactory<>("yom"));
        colFuelType.setCellValueFactory(new PropertyValueFactory<>("fuel"));
        colCustId.setCellValueFactory(new PropertyValueFactory<>("cust_Id"));

        //tblVehicle.getColumns().setAll(colRegNo, colVName, colBrand, colYom,colFuelType,colCustId);

        tblVehicle.getItems().setAll(vehicle);

    }

    public void OpenCustomer(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Customer customer=new CustomerController().getCustomerUsingId(CustId.getText());
        if (customer!=null){
            CustName.setText(customer.getName());
            btnAddVehicle.setDisable(false);
        }else{
            new Alert(Alert.AlertType.ERROR,"Customer Id is Incorrect.").show();
            return;
        }
    }

    public void EditVehicle(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        Vehicle vehicle=new Vehicle(EditRegNo.getText(),EditName.getText(),EditBrand.getText(),EditYom.getText(),EditFuelType.getText(),C_id);
        if(new VehicleController().updateVehicle(vehicle)){
            new Alert(Alert.AlertType.CONFIRMATION,"Edited Successfully.").show();
            loadTable();
            clear();
            btnEditVehicle1.setDisable(true);
        }else{
            new Alert(Alert.AlertType.ERROR,"Try Again.").show();
        }
    }

    public void DeleteVehicle(ActionEvent actionEvent) {
        try {
            String id=tblVehicle.getSelectionModel().getSelectedItem().getReg_No();
            boolean isDeleted = new VehicleController().deleteVehicle(id);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Deleted.").show();
                loadTable();
                id="";
                btnDeleteVehicle.setDisable(true);
            }else{
                new Alert(Alert.AlertType.ERROR,"Try Again").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void AddVehicle(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Vehicle v=new VehicleController().getVehicleUsingId(Reg_No.getText());
        if (v!=null){
            new Alert(Alert.AlertType.WARNING,"This Vehicle is Exists").show();
            clear();
            return;
        }
        Vehicle vehicle=new Vehicle(Reg_No.getText(),Name.getText(),Brand.getText(),Yom.getText(),FuelType.getText(),CustId.getText());

        if (new VehicleController().saveVehicle(vehicle)){
            new Alert(Alert.AlertType.CONFIRMATION,"Saved Successfully.").show();
            loadTable();
            clear();
        }else{
            new Alert(Alert.AlertType.ERROR,"Try Again.").show();
        }
    }

    public void clear(){
        CustId.setText("");
        CustName.setText("");
        Reg_No.setText("");
        Name.setText("");
        Brand.setText("");
        Yom.setText("");
        FuelType.setText("");
        EditRegNo.setText("");
        EditName.setText("");
        EditBrand.setText("");
        EditYom.setText("");
        EditFuelType.setText("");
    }

    public void CheckVehicle(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Vehicle vehicle=new VehicleController().getVehicleUsingId(Reg_No.getText());
        if (vehicle!=null){
            new Alert(Alert.AlertType.WARNING,"This is Exists Vehicle.").show();
            btnAddVehicle.setDisable(true);
            return;
        }else{
            btnAddVehicle.setDisable(false);
        }
    }

    public void OnKeyReleased(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnAddVehicle);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                btnAddVehicle.requestFocus();
            }
        }
    }

    public void OnEditKeyReleased(KeyEvent keyEvent) {
            EditValidateData();

        Object response = ValidationUtil.validate(map,btnEditVehicle1);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                btnEditVehicle1.requestFocus();
            }
        }
    }

    private void EditValidateData() {
        map.clear();

        map.put(EditRegNo,regNo);
        map.put(EditName,namePattern);
        map.put(EditBrand,brandPattern);
        map.put(EditYom,yomPattern);
        map.put(EditFuelType,fueltypePattern);

    }
}
