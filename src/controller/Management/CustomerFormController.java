package controller.Management;

import controller.DataController.AdminController;
import controller.DataController.CustomerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Admin;
import model.Customer;
import view.TM.AdminTm;
import view.TM.CustomerTm;

import java.sql.SQLException;
import java.util.List;

public class CustomerFormController {
    public TableView<CustomerTm> tblCustomer_Management;
    public TableColumn colCustId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colContact;
    public TableColumn colNIC;
    public Button btnDelete;

    public void initialize(){
        try {
            btnDelete.setDisable(true);

            colCustId.setCellValueFactory(new PropertyValueFactory<>("Cust_Id"));
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
            colNIC.setCellValueFactory(new PropertyValueFactory<>("nIC"));

            tblCustomer_Management.getColumns().setAll(colCustId, colName, colAddress, colContact,colNIC);

            tblCustomer_Management.getItems().setAll(loaddata());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        tblCustomer_Management.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(false);
        });

    }

    public ObservableList<CustomerTm> loaddata() throws SQLException, ClassNotFoundException {
        List<Customer> allcustomers= new CustomerController().getAllCustomers();
        ObservableList<CustomerTm> tableData = FXCollections.observableArrayList();
        for (Customer customer:allcustomers){
            tableData.add(new CustomerTm(
                    customer.getCust_ID(),
                    customer.getName(),
                    customer.getAddress(),
                    customer.getContact(),
                    customer.getnIC(),
                    customer.getUser_ID()
            ));
        }
        return tableData;
    }

    public void DeleteAdmin(ActionEvent actionEvent) {
        try {
            String id = tblCustomer_Management.getSelectionModel().getSelectedItem().getCust_Id();
            boolean isDeleted = CustomerController.deleteCustomer(id);
            if (isDeleted){
                new Alert(Alert.AlertType.INFORMATION,"Success.").show();
                tblCustomer_Management.getItems().setAll(loaddata());
            }else {
                new Alert(Alert.AlertType.ERROR,"Error.").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
}
