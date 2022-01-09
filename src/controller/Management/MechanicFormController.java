package controller.Management;

import controller.DataController.AdminController;
import controller.DataController.MechanicController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Admin;
import model.Mechanic;
import view.TM.AdminTm;
import view.TM.MechanicTm;

import java.sql.SQLException;
import java.util.List;

public class MechanicFormController {
    public TableView<MechanicTm> tblMechanic_Management;
    public TableColumn colMechanicId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colContact;
    public TableColumn colNIC;
    public TableColumn colSalary;
    public Button btnDelete;


    public void initialize(){
        try {
            btnDelete.setDisable(true);

            colMechanicId.setCellValueFactory(new PropertyValueFactory<>("m_Id"));
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
            colNIC.setCellValueFactory(new PropertyValueFactory<>("nic"));
            colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));

            tblMechanic_Management.getColumns().setAll(colMechanicId, colName, colAddress, colContact,colNIC,colSalary);

            tblMechanic_Management.getItems().setAll(loaddata());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        tblMechanic_Management.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(false);
        });

    }

    public ObservableList<MechanicTm> loaddata() throws SQLException, ClassNotFoundException {
        List<Mechanic> allmechanics= new MechanicController().getAllMechanics();
        ObservableList<MechanicTm> tableData = FXCollections.observableArrayList();
        for (Mechanic mechanic:allmechanics){
            tableData.add(new MechanicTm(
                    mechanic.getM_Id(),
                    mechanic.getName(),
                    mechanic.getAddress(),
                    mechanic.getContact(),
                    mechanic.getNic(),
                    mechanic.getSalary(),
                    mechanic.getAdmin_id()

            ));
        }
        return tableData;
    }


    public void DeleteMechanic(ActionEvent actionEvent) {
        try {
            String id = tblMechanic_Management.getSelectionModel().getSelectedItem().getM_Id();
            boolean isDeleted = MechanicController.deleteMechanic(id);
            if (isDeleted){
                new Alert(Alert.AlertType.INFORMATION,"Success.").show();
                tblMechanic_Management.getItems().setAll(loaddata());
            }else {
                new Alert(Alert.AlertType.ERROR,"Error.").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
}
