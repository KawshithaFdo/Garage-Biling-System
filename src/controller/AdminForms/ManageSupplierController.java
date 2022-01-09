package controller.AdminForms;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import controller.DataController.MechanicController;
import controller.DataController.SupplierController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.Supplier;
import util.ValidationUtil;
import view.TM.SupplierTm;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class ManageSupplierController {
  public String AdminId;
  public AnchorPane ManageSupplierContext;
  public Label lblSupplierId;
  public TextField txtName;
  public TextField txtContact;
  public TextField txtNic;
  public TextField txtCompany;
  public JFXButton btnAddSupplier;
  public TableView<SupplierTm> tblSupplier;
  public TableColumn colSupId;
  public TableColumn colName;
  public TableColumn colContact;
  public TableColumn colNic;
  public TableColumn colCompany;
  public TableColumn colAdminId;
  public TextField txtEditName;
  public TextField txtEditContact;
  public TextField txtEditNic;
  public TextField txtEditCompany;
  public JFXButton btnDeleteSupplier1;
  public JFXButton btnUpdateSupplier1;
  public TextField txtEditSup_Id;

  LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
  Pattern namePattern = Pattern.compile("^[[A-z]{1,30}[ ]?]+$");
  Pattern contactPattern=Pattern.compile("^[0][0-9]{2,3}[-]?[]0-9]{6,7}?$");
  Pattern nicpattern=Pattern.compile("^[0-9]{9,12}[vV]?$");
  Pattern companyPattern = Pattern.compile("^[A-z]{1,30}$");
  Pattern idPattern = Pattern.compile("^[S](-00)[0-9]{1,4}$");


  public void initialize(){
    btnAddSupplier.setDisable(true);
    Validatedata();
    btnUpdateSupplier1.setDisable(true);
    btnDeleteSupplier1.setDisable(true);

    try {
      lblSupplierId.setText(new SupplierController().getSupplierid());
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

    tblSupplier.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      btnDeleteSupplier1.setDisable(false);
    });

    try {
      loadTableData();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  private void Validatedata() {
      map.put(txtName,namePattern);
      map.put(txtContact,contactPattern);
      map.put(txtNic,nicpattern);
      map.put(txtCompany,companyPattern);
  }

  public void loadTableData() throws SQLException, ClassNotFoundException {
    List<Supplier> allsuppliers=new SupplierController().getAllSuppliers();
    ObservableList<SupplierTm> table= FXCollections.observableArrayList();
    for(Supplier s:allsuppliers){
      table.add(new SupplierTm(
              s.getSup_Id(),
              s.getName(),
              s.getContact(),
              s.getNic(),
              s.getCompany(),
              s.getAdmin_Id()
      ));
    }

    colSupId.setCellValueFactory(new PropertyValueFactory<>("sup_Id"));
    colName.setCellValueFactory(new PropertyValueFactory<>("name"));
    colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
    colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
    colCompany.setCellValueFactory(new PropertyValueFactory<>("company"));
    colAdminId.setCellValueFactory(new PropertyValueFactory<>("admin_Id"));

    tblSupplier.getItems().setAll(table);
  }

  public void addSupplierOnaction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
    Supplier s=new SupplierController().getSupplierUsingNic(txtNic.getText());
    if (s!=null){
      new Alert(Alert.AlertType.WARNING,"This Nic is Exists.").show();
      return;
    }
      Supplier s1=new Supplier(lblSupplierId.getText(),txtName.getText(),txtContact.getText(),txtNic.getText(),
              txtCompany.getText(),AdminId);
      if (new SupplierController().saveSupplier(s1)){
          new Alert(Alert.AlertType.CONFIRMATION,"Successful.").show();
          loadTableData();
          clear();
      }else{
        new Alert(Alert.AlertType.ERROR,"Try Again.").show();
      }

  }

  public void clear() throws SQLException, ClassNotFoundException {
    lblSupplierId.setText(new SupplierController().getSupplierid());
    txtName.setText("");
    txtContact.setText("");
    txtNic.setText("");
    txtCompany.setText("");
    txtEditName.setText("");
    txtEditContact.setText("");
    txtEditCompany.setText("");
    txtEditNic.setText("");
  }

  public void openSupplierDetails(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
    Supplier s1=new SupplierController().getSupplier(txtEditSup_Id.getText());
    if (s1==null){
      new Alert(Alert.AlertType.ERROR,"Invalid Supplier ID.").show();
      return;
    }else{
      txtEditName.setText(s1.getName());
      txtEditContact.setText(s1.getContact());
      txtEditCompany.setText(s1.getCompany());
      txtEditNic.setText(s1.getNic());
      btnUpdateSupplier1.setDisable(false);
    }
  }

  public void UpdateSupplierOnaction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
    Supplier supplier=new Supplier(txtEditSup_Id.getText(),txtEditName.getText(),txtEditContact.getText(),txtEditNic.getText(),
            txtEditCompany.getText(),AdminId);
    if (new SupplierController().updateSupplier(supplier)){
      new Alert(Alert.AlertType.CONFIRMATION,"Updated.").show();
      loadTableData();
      clear();
      btnUpdateSupplier1.setDisable(true);
    }else{
      new Alert(Alert.AlertType.ERROR,"Try Again.").show();
    }
  }

  public void DeleteSupplierOnaction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
    String id=tblSupplier.getSelectionModel().getSelectedItem().getSup_Id();
    if (new SupplierController().deleteSupplier(id)){
      new Alert(Alert.AlertType.CONFIRMATION,"Deleted.").show();
      loadTableData();
      clear();
      btnDeleteSupplier1.setDisable(true);
    }else{
      new Alert(Alert.AlertType.ERROR,"Try Again.").show();

    }
  }

  public void OnKeyReleased(KeyEvent keyEvent) {
    Object response = ValidationUtil.validate(map,btnAddSupplier);

    if (keyEvent.getCode() == KeyCode.ENTER) {
      if (response instanceof TextField) {
        TextField errorText = (TextField) response;
        errorText.requestFocus();
      } else if (response instanceof Boolean) {
        btnAddSupplier.requestFocus();
      }
    }
  }

  public void EditKeyReleased(KeyEvent keyEvent) {
    EditValidateData();

    Object response = ValidationUtil.validate(map,btnUpdateSupplier1);

    if (keyEvent.getCode() == KeyCode.ENTER) {
      if (response instanceof TextField) {
        TextField errorText = (TextField) response;
        errorText.requestFocus();
      } else if (response instanceof Boolean) {
        btnUpdateSupplier1.requestFocus();
      }
    }
  }

  private void EditValidateData() {
    map.clear();

    map.put(txtEditSup_Id,idPattern);
    map.put(txtEditName,namePattern);
    map.put(txtEditContact,contactPattern);
    map.put(txtEditNic,nicpattern);
    map.put(txtEditCompany,companyPattern);
  }
}
