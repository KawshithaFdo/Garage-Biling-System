package controller.Management;

import controller.DataController.SupplierController;
import controller.DataController.VehicleController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Supplier;
import model.Vehicle;
import view.TM.SupplierTm;
import view.TM.VehicleTm;

import java.sql.SQLException;
import java.util.List;

public class SupplierFormController {
    public TableView<SupplierTm> tblSupplier_Management;
    public TableColumn colSupplierId;
    public TableColumn colName;
    public TableColumn colCompany;
    public TableColumn colContact;
    public TableColumn colNIC;
    public Button btnDelete;
    public String Admin_Id;

    public void initialize(){
        try {
            btnDelete.setDisable(true);

            colSupplierId.setCellValueFactory(new PropertyValueFactory<>("sup_Id"));
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
            colNIC.setCellValueFactory(new PropertyValueFactory<>("nic"));
            colCompany.setCellValueFactory(new PropertyValueFactory<>("company"));

            tblSupplier_Management.getColumns().setAll(colSupplierId, colName, colContact, colNIC,colCompany);

            tblSupplier_Management.getItems().setAll(loaddata());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        tblSupplier_Management.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(false);
        });

    }

    public ObservableList<SupplierTm> loaddata() throws SQLException, ClassNotFoundException {
        List<Supplier> allsuppliers= new SupplierController().getAllSuppliers();
        ObservableList<SupplierTm> tableData = FXCollections.observableArrayList();
        for (Supplier supplier:allsuppliers){
            tableData.add(new SupplierTm(
                    supplier.getSup_Id(),
                    supplier.getName(),
                    supplier.getContact(),
                    supplier.getNic(),
                    supplier.getCompany(),
                    Admin_Id
            ));
        }
        return tableData;
    }


    public void DeleteSupplier(ActionEvent actionEvent) {
        try {
            String id = tblSupplier_Management.getSelectionModel().getSelectedItem().getSup_Id();
            boolean isDeleted = SupplierController.deleteSupplier(id);
            if (isDeleted){
                new Alert(Alert.AlertType.INFORMATION,"Success.").show();
                tblSupplier_Management.getItems().setAll(loaddata());
            }else {
                new Alert(Alert.AlertType.ERROR,"Error.").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
}
