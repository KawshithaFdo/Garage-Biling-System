package controller.AdminForms;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import controller.DataController.MechanicController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.Mechanic;
import util.ValidationUtil;
import view.TM.MechanicTm;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class ManageMechanicController {

    public AnchorPane ManageMechanicContext;
    public Label lblMechanicId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtContact;
    public TextField txtNic;
    public TextField txtsalary;
    public JFXButton btnAddMechanic;
    public TableView<MechanicTm> tblMechanic;
    public TableColumn colMechanicId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colContact;
    public TableColumn colNic;
    public TableColumn colSalary;
    public TableColumn colAdmin_Id;
    public TextField EdittxtName;
    public TextField EdittxtAddress;
    public TextField EdittxtContact;
    public TextField EdittxtNic;
    public TextField Edittxtsalary;
    public JFXButton btnDeleteMechanic;
    public JFXButton btnUpdateMechanic;
    public String AdminId;
    public TextField EditM_Id;
    public String EditAdmin;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern namePattern = Pattern.compile("^[[A-z]{1,30}[ ]?]+$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9/ ]{6,30}$");
    Pattern contactPattern=Pattern.compile("^[0][0-9]{2,3}[-]?[]0-9]{6,7}?$");
    Pattern nicpattern=Pattern.compile("^[0-9]{9,12}[vV]?$");
    Pattern salaryPattern = Pattern.compile("^[1-9][0-9]*([.][0-9]{1,2})?$");
    Pattern idPattern = Pattern.compile("^[M](-00)[0-9]{1,4}$");

    public void initialize() {
        btnAddMechanic.setDisable(true);
        ValidateData();
        btnUpdateMechanic.setDisable(true);
        btnDeleteMechanic.setDisable(true);

        try {
            lblMechanicId.setText(new MechanicController().getMechanicId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            loadTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        tblMechanic.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDeleteMechanic.setDisable(false);
        });
    }

    private void ValidateData() {
        map.put(txtName,namePattern);
        map.put(txtAddress,addressPattern);
        map.put(txtContact,contactPattern);
        map.put(txtNic,nicpattern);
        map.put(txtsalary,salaryPattern);
    }

    private void loadTable() throws SQLException, ClassNotFoundException {
        List<Mechanic> allmechanics=new MechanicController().getAllMechanics();
        ObservableList<MechanicTm> mechanics= FXCollections.observableArrayList();
        for (Mechanic m:allmechanics){
            mechanics.add(new MechanicTm(
               m.getM_Id(),
               m.getName(),
               m.getAddress(),
               m.getContact(),
               m.getNic(),
               m.getSalary(),
               m.getAdmin_id()
            ));
        }
        colMechanicId.setCellValueFactory(new PropertyValueFactory<>("m_Id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colAdmin_Id.setCellValueFactory(new PropertyValueFactory<>("admin_Id"));

        tblMechanic.getItems().setAll(mechanics);
    }

    public void addMechanicOnaction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Mechanic m=new MechanicController().getMechanicUsingNic(txtNic.getText());
        if (m!=null){
            new Alert(Alert.AlertType.WARNING,"This Nic is Exists.").show();
            return;
        }
        Mechanic m1=new Mechanic(lblMechanicId.getText(),txtName.getText(),txtAddress.getText(),txtContact.getText(),
                                txtNic.getText(),Double.parseDouble(txtsalary.getText()),AdminId);
        if (new MechanicController().saveMechanic(m1)){
            new Alert(Alert.AlertType.CONFIRMATION,"Successful.").show();
            loadTable();
            clear();
        }else{
            new Alert(Alert.AlertType.ERROR,"Try Again.").show();
        }
    }

    public void openEditData(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Mechanic m1=new MechanicController().getMechanic(EditM_Id.getText());
        if (m1==null){
            new Alert(Alert.AlertType.ERROR,"Invalid Mechanic Id.").show();
            return;
        }else{
            EdittxtName.setText(m1.getName());
            EdittxtAddress.setText(m1.getAddress());
            EdittxtContact.setText(m1.getContact());
            EdittxtNic.setText(m1.getNic());
            Edittxtsalary.setText(String.valueOf(m1.getSalary()));
            btnUpdateMechanic.setDisable(false);
            EditAdmin=m1.getAdmin_id();
        }
    }

    public void UpdateMechanicOnaction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Mechanic m1=new Mechanic(EditM_Id.getText(),EdittxtName.getText(),EdittxtAddress.getText(),EdittxtContact.getText(),
                EdittxtNic.getText(),Double.parseDouble(Edittxtsalary.getText()),EditAdmin);
        if (new MechanicController().updateMechanic(m1)){
            new Alert(Alert.AlertType.CONFIRMATION,"Updated.").show();
            loadTable();
            clear();
            btnUpdateMechanic.setDisable(true);
        }else{
            new Alert(Alert.AlertType.ERROR,"Try Again.").show();
        }
    }

    public void DeleteMechanicOnaction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String D_id=tblMechanic.getSelectionModel().getSelectedItem().getM_Id();
        if (new MechanicController().deleteMechanic(D_id)){
            new Alert(Alert.AlertType.CONFIRMATION,"Deleted.").show();
            loadTable();
            clear();
            btnDeleteMechanic.setDisable(true);
        }else{
            new Alert(Alert.AlertType.ERROR,"Try Again.").show();
        }

    }

    public void clear() throws SQLException, ClassNotFoundException {
        lblMechanicId.setText(new MechanicController().getMechanicId());
        txtName.setText("");
        txtAddress.setText("");
        txtContact.setText("");
        txtNic.setText("");
        txtsalary.setText("");
        EdittxtName.setText("");
        EdittxtAddress.setText("");
        EdittxtContact.setText("");
        EdittxtNic.setText("");
        Edittxtsalary.setText("");
    }

    public void OnKeyReleased(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnAddMechanic);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                btnAddMechanic.requestFocus();
            }
        }
    }

    public void EditKeyreleased(KeyEvent keyEvent) {
            EditValidateData();
        Object response = ValidationUtil.validate(map,btnUpdateMechanic);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                btnUpdateMechanic.requestFocus();
            }
        }
    }

    private void EditValidateData() {
        map.clear();
        map.put(EditM_Id,idPattern);
        map.put(EdittxtName,namePattern);
        map.put(EdittxtAddress,addressPattern);
        map.put(EdittxtContact,contactPattern);
        map.put(EdittxtNic,nicpattern);
        map.put(Edittxtsalary,salaryPattern);
    }
}
