package controller.AdminForms;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import controller.DataController.PartController;
import controller.DataController.SupplierController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.Part;
import model.Supplier;
import util.ValidationUtil;
import view.TM.PartTm;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class ManageSparePartsController {

    public AnchorPane ManageSparePartsContext;
    public Label SparePartId;
    public TextField S_Name;
    public TextField Part_Name;
    public TextField Part_Country;
    public TextField Part_Brand;
    public TextField Part_Qty;
    public TextField Part_UnitPrice;
    public TextField Part_Discount;
    public JFXButton btnAddSpareParts;
    public TableView<PartTm> tblSpareParts;
    public TableColumn colpartId;
    public TableColumn colname;
    public TableColumn colBrand;
    public TableColumn colCountry;
    public TableColumn colQty;
    public TableColumn colUnitPrice;
    public TableColumn colDiscount;
    public TableColumn colsupplierId;
    public TextField EditPart_Name;
    public TextField EditPart_Country;
    public TextField EditPart_Brand;
    public TextField EditPart_Qty;
    public TextField EditPart_UnitPrice;
    public TextField EditPart_Discount;
    public JFXButton btnDeleteSpareParts;
    public JFXButton btnUpdateSpareParts;
    public TextField EditPart_Id;
    public String EditSup_Id;
    public ComboBox cmbSupplierId;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern namePattern = Pattern.compile("^[[A-z]{1,30}[ ]?]+[(]?[0-9]?[/]?[0-9]?[)]?$");
    Pattern brandPattern = Pattern.compile("^[A-z]{1,30}$");
    Pattern countryPattern = Pattern.compile("^[A-z]{1,30}$");
    Pattern qtyPattern = Pattern.compile("^[0-9]{1,}$");
    Pattern unitpricePattern = Pattern.compile("^[1-9][0-9]*([.][0-9]{1,2})?$");
    Pattern discountPattern=Pattern.compile("^[1-9]?[0-9]{1}?([.][0-9]{1,2})?$");
    Pattern idPattern = Pattern.compile("^[P](-00)[0-9]{1,4}$");

    public void initialize(){
        btnAddSpareParts.setDisable(true);
        Validatedata();
        btnUpdateSpareParts.setDisable(true);
        btnDeleteSpareParts.setDisable(true);

        try {
            loadTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            SparePartId.setText(new PartController().getPartId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        tblSpareParts.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDeleteSpareParts.setDisable(false);
        });

        try {
            opencmbSupplier();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        cmbSupplierId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                openSupplierDetails();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });


    }

    private void Validatedata() {
        map.put(Part_Name,namePattern);
        map.put(Part_Brand,brandPattern);
        map.put(Part_Country,countryPattern);
        map.put(Part_Qty,qtyPattern);
        map.put(Part_UnitPrice,unitpricePattern);
        map.put(Part_Discount,discountPattern);
    }

    private void openSupplierDetails() throws SQLException, ClassNotFoundException {
        Supplier supplier=new SupplierController().getSupplier(String.valueOf(cmbSupplierId.getValue()));
        S_Name.setText(supplier.getName());
        btnAddSpareParts.setDisable(false);
    }

    private void opencmbSupplier() throws SQLException, ClassNotFoundException {
        List<String> allids= new SupplierController().getAllSupplierIds();
        ObservableList<String> table=FXCollections.observableArrayList();
        for (String s:allids){
            table.add(s);
        }
        cmbSupplierId.setItems(table);
    }

    public void AddSpareParts(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Part part=new Part(SparePartId.getText(),Part_Name.getText(),Part_Country.getText(),Part_Brand.getText(),Integer.valueOf(Part_Qty.getText()),
                Double.parseDouble(Part_UnitPrice.getText()),Double.parseDouble(Part_Discount.getText()),String.valueOf(cmbSupplierId.getValue()));
        if (new PartController().AddParts(part)){
            new Alert(Alert.AlertType.CONFIRMATION,"Successful.").show();
            loadTable();
            clear();
            btnAddSpareParts.setDisable(true);
        }else{
            new Alert(Alert.AlertType.WARNING,"Try Again.").show();
        }
    }

    private void clear() throws SQLException, ClassNotFoundException {
        SparePartId.setText(new PartController().getPartId());
        Part_Name.setText("");
        Part_Country.setText("");
        Part_Brand.setText("");
        Part_Qty.setText("");
        Part_UnitPrice.setText("");
        Part_Discount.setText("");
        EditPart_Id.setText("");
        EditPart_Name.setText("");
        EditPart_Country.setText("");
        EditPart_Brand.setText("");
        EditPart_Qty.setText("");
        EditPart_UnitPrice.setText("");
        EditPart_Discount.setText("");

    }

    private void loadTable() throws SQLException, ClassNotFoundException {
        List<Part> allParts=new PartController().getAllParts();
        ObservableList<PartTm> list= FXCollections.observableArrayList();
        for (Part p:allParts){
            list.add(new PartTm(
                    p.getP_Id(),
                    p.getName(),
                    p.getCountry(),
                    p.getBrand(),
                    p.getQty(),
                    p.getUnitprice(),
                    p.getDiscount(),
                    p.getSup_Id()
            ));
        }
        colpartId.setCellValueFactory(new PropertyValueFactory<>("p_Id"));
        colname.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitprice"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colsupplierId.setCellValueFactory(new PropertyValueFactory<>("S_Id"));

        tblSpareParts.getItems().setAll(list);
    }

    public void OpenEditPartDetails(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Part p1=new PartController().getParts(EditPart_Id.getText());
        if (p1==null){
            new Alert(Alert.AlertType.WARNING,"Invalid Part Id.").show();
            return;
        }else{
            EditPart_Name.setText(p1.getName());
            EditPart_Brand.setText(p1.getBrand());
            EditPart_Country.setText(p1.getCountry());
            EditPart_Qty.setText(String.valueOf(p1.getQty()));
            EditPart_UnitPrice.setText(String.valueOf(p1.getUnitprice()));
            EditPart_Discount.setText(String.valueOf(p1.getDiscount()));
            EditSup_Id=p1.getSup_Id();
            btnUpdateSpareParts.setDisable(false);
        }
    }

    public void UpdateSpareParts(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Part p=new Part(EditPart_Id.getText(),EditPart_Name.getText(),EditPart_Country.getText(),EditPart_Brand.getText(),Integer.valueOf(EditPart_Qty.getText()),
                Double.parseDouble(EditPart_UnitPrice.getText()),Double.parseDouble(EditPart_Discount.getText()),EditSup_Id);
        if (new PartController().updatePart(p)){
            new Alert(Alert.AlertType.CONFIRMATION,"Updated.").show();
            loadTable();
            clear();
            btnUpdateSpareParts.setDisable(true);
        }else{
            new Alert(Alert.AlertType.WARNING,"Try Again.").show();
        }
    }

    public void DeleteSpareParts(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String D_Id=tblSpareParts.getSelectionModel().getSelectedItem().getP_Id();
        if (new PartController().deletePart(D_Id)){
            new Alert(Alert.AlertType.CONFIRMATION,"Deleted.").show();
            loadTable();
            btnDeleteSpareParts.setDisable(true);
        }else{
            new Alert(Alert.AlertType.WARNING,"Try Again.").show();
        }
    }

    public void OnKeyReleased(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnAddSpareParts);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                btnAddSpareParts.requestFocus();
            }
        }
    }

    public void OnEditKeyReleased(KeyEvent keyEvent) {
        EditValidateData();

        Object response = ValidationUtil.validate(map,btnUpdateSpareParts);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                btnUpdateSpareParts.requestFocus();
            }
        }
    }

    private void EditValidateData() {
        map.clear();

        map.put(EditPart_Id,idPattern);
        map.put(EditPart_Name,namePattern);
        map.put(EditPart_Brand,brandPattern);
        map.put(EditPart_Country,countryPattern);
        map.put(EditPart_Qty,qtyPattern);
        map.put(EditPart_UnitPrice,unitpricePattern);
        map.put(EditPart_Discount,discountPattern);
    }
}
