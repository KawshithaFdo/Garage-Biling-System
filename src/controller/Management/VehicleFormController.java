package controller.Management;

import controller.DataController.CustomerController;
import controller.DataController.VehicleController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import model.Vehicle;
import view.TM.CustomerTm;
import view.TM.VehicleTm;

import java.sql.SQLException;
import java.util.List;

public class VehicleFormController {
    public TableView<VehicleTm> tblVehicle_Management;
    public TableColumn colRegNo;
    public TableColumn colName;
    public TableColumn colBrand;
    public TableColumn colYom;
    public TableColumn colFueltype;
    public TableColumn colCustId;
    public Button btnDelete;

    public void initialize(){
        try {
            btnDelete.setDisable(true);

            colRegNo.setCellValueFactory(new PropertyValueFactory<>("reg_No"));
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
            colYom.setCellValueFactory(new PropertyValueFactory<>("yom"));
            colFueltype.setCellValueFactory(new PropertyValueFactory<>("fuel"));
            colCustId.setCellValueFactory(new PropertyValueFactory<>("cust_Id"));

            tblVehicle_Management.getColumns().setAll(colRegNo, colName, colBrand, colYom,colFueltype,colCustId);

            tblVehicle_Management.getItems().setAll(loaddata());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        tblVehicle_Management.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(false);
        });
    }

    public ObservableList<VehicleTm> loaddata() throws SQLException, ClassNotFoundException {
        List<Vehicle> allvehicles= new VehicleController().getAllVehicles();
        ObservableList<VehicleTm> tableData = FXCollections.observableArrayList();
        for (Vehicle vehicle:allvehicles){
            tableData.add(new VehicleTm(
                    vehicle.getReg_No(),
                    vehicle.getName(),
                    vehicle.getBrand(),
                    vehicle.getYom(),
                    vehicle.getFuel(),
                    vehicle.getCust_Id()
            ));
        }
        return tableData;
    }


    public void DeleteAdmin(ActionEvent actionEvent) {
        try {
            String id = tblVehicle_Management.getSelectionModel().getSelectedItem().getReg_No();
            boolean isDeleted = VehicleController.deleteVehicle(id);
            if (isDeleted){
                new Alert(Alert.AlertType.INFORMATION,"Success.").show();
                tblVehicle_Management.getItems().setAll(loaddata());
            }else {
                new Alert(Alert.AlertType.ERROR,"Error.").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
}
