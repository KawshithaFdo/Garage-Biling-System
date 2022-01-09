package controller.UserForms;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import controller.DataController.*;
import database.DbConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.ValidationUtil;
import view.TM.CartTm;
import view.TM.PartTm;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Pattern;

public class MakePaymentController{

    public String UserId;
    public String Date;
    public String Time;
    public AnchorPane MakePaymentContext;
    public ComboBox<String> cmbPartIds;
    public TextField QtyForUse;
    public TextField MechanicCost;
    public TableView<CartTm> tblBill;
    public JFXTextField Name;
    public JFXTextField Brand;
    public JFXButton btnAddtoCart;
    public Label partsCost;
    public Label totalDiscount;
    public Label totalAmount;
    public JFXButton btnConfirmPayment;
    public JFXTextField CustName;
    public JFXTextField Reg_No;
    public Label lblBill_Id;
    public ComboBox<String> cmbBookingId;
    public TableColumn colPartId;
    public TableColumn colPartname;
    public TableColumn colPartQty;
    public TableColumn colPartUnitprice;
    public TableColumn colDiscount;
    public TableColumn colPrice;
    public JFXTextField QtyAvailable;
    public TableColumn colQtyAvailable;
    public int cartSelectedRowNo = -1;
    public JFXButton btnClear;
    public String B_Id;
    public String Bill_Id;
    public String UserName;
    public String CName;
    public String regNo;
    public Double Tdiscount;
    public Double Price;
    public Double pcost;
    public Double mcost;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern qtyforusePattern = Pattern.compile("^[0-9]{1,}$");
    Pattern mechanicCostPattern = Pattern.compile("^[0-9]{1,}$");

    public void initialize(){

        btnAddtoCart.setDisable(true);
        MechanicCost.setDisable(true);
        btnConfirmPayment.setDisable(true);
        ValidateData();
        btnClear.setDisable(true);
        cmbPartIds.setDisable(true);

        colPartId.setCellValueFactory(new PropertyValueFactory<>("p_Id"));
        colPartname.setCellValueFactory(new PropertyValueFactory<>("p_Name"));
        colQtyAvailable.setCellValueFactory(new PropertyValueFactory<>("qty_avilable"));
        colPartQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPartUnitprice.setCellValueFactory(new PropertyValueFactory<>("unitprice"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        dateandtime();

        try {
            lblBill_Id.setText(new BillController().getBillId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            loadcmbPartData();
            loadcmbBookingData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmbBookingId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                Booking_IdDetails();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        cmbPartIds.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                Part_IdDetails();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        tblBill.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cartSelectedRowNo=(int) newValue;
            btnClear.setDisable(false);
        });

    }

    private void ValidateData() {

        map.put(QtyForUse,qtyforusePattern);
    }

    private void loadcmbBookingData() throws SQLException, ClassNotFoundException {
        List<String> allids=new BookingController().getBookingIds();
        ObservableList<String> ids=FXCollections.observableArrayList();
        for (String s:allids){
            ids.add(s);
        }
        cmbBookingId.setItems(ids);
    }

    public void Booking_IdDetails() throws SQLException, ClassNotFoundException {
        Booking b1=new BookingController().getBookingUsingId(String.valueOf(cmbBookingId.getValue()));
        if (b1==null){
            Reg_No.setText("");
            CustName.setText("");
            return;
        }
        B_Id=cmbBookingId.getValue();
        Reg_No.setText(b1.getReg_No());
        Customer c=new CustomerController().getCustomerUsingId(b1.getCust_Id());
        CustName.setText(c.getName());
        cmbPartIds.setDisable(false);
    }

    public void loadcmbPartData() throws SQLException, ClassNotFoundException {
        List<String> allids= new PartController().getAllPartIds();
        ObservableList<String> table=FXCollections.observableArrayList();
        for (String s:allids){
            table.add(s);
        }
        cmbPartIds.setItems(table);
    }

    public void Part_IdDetails() throws SQLException, ClassNotFoundException {
        Part p=new PartController().getParts(String.valueOf(cmbPartIds.getValue()));
        if (p==null){
            Name.setText("");
            Brand.setText("");
            QtyAvailable.setText("");
            return;
        }
        Name.setText(p.getName());
        Brand.setText(p.getBrand());
        QtyAvailable.setText(String.valueOf(p.getQty()));
        btnAddtoCart.setDisable(false);
    }

    private void dateandtime() {
        java.util.Date date=new Date();
        SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd");
        Date= (f.format(date));

        Timeline time=new Timeline(new KeyFrame(Duration.ZERO, e->{
            LocalTime currentTime = LocalTime.now();
            Time= currentTime.getHour()+":"+currentTime.getMinute()+":"+ currentTime.getSecond();
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    ObservableList<CartTm> list=FXCollections.observableArrayList();


    public void AddToCartOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (QtyForUse.getText().isEmpty()){
            new Alert(Alert.AlertType.WARNING,"Please Enter Valid Qty. ").show();
            return;
        }else {


            int qty = Integer.valueOf(QtyAvailable.getText());
            int qty_used = Integer.parseInt(QtyForUse.getText());


            Part allParts = new PartController().getParts(cmbPartIds.getValue());
            Double unitPrice = allParts.getUnitprice();
            Double discount = allParts.getDiscount();

            Double totalDiscount = (((discount / 100) * unitPrice) * qty_used);
            Double price = ((unitPrice * qty_used) - totalDiscount);
            int Qty=0;
            if (qty < qty_used) {
                new Alert(Alert.AlertType.WARNING, "This Qty isn't Available").show();
                return;
            }else{
                QtyAvailable.setText(String.valueOf(qty - qty_used));
                Qty=qty-qty_used;
            }

            CartTm tm = new CartTm(
                    cmbPartIds.getValue(),
                    Name.getText(),
                    qty_used,
                    Qty,
                    unitPrice,
                    totalDiscount,
                    price
            );


            int rowNumber = isExists(tm);

            if (rowNumber == -1) {
                list.add(tm);
            } else {
                CartTm temp = list.get(rowNumber);
                CartTm newTm = new CartTm(
                        temp.getP_Id(),
                        temp.getP_Name(),
                        temp.getQty() + qty_used,
                        qty - qty_used - temp.getQty(),
                        unitPrice,
                        totalDiscount + temp.getDiscount(),
                        price + temp.getPrice()
                );

                list.remove(rowNumber);
                list.add(newTm);
            }
            tblBill.setItems(list);
            QtyForUse.setText("");
            MechanicCost.setDisable(false);
        }
    }

    private int isExists(CartTm tm){
        for (int i = 0; i < list.size(); i++) {
            if (tm.getP_Id().equals(list.get(i).getP_Id())){
                return i;
            }
        }
        return -1;
    }

    public void calculateFinalBill(ActionEvent actionEvent) {
       calculatebill();
    }

    Double part_cost=0.0;
    Double total_discount=0.0;
    Double amount=0.0;

    public void calculatebill(){
        int mechanic_cost=0;
        if (MechanicCost.getText().isEmpty()){
            mechanic_cost=0;
            return;
        }else {
            mechanic_cost = Integer.valueOf(MechanicCost.getText());
        }
            for (CartTm tm : list) {
                part_cost += tm.getPrice();
                total_discount += tm.getDiscount();
                amount += tm.getPrice();
            }
            amount += mechanic_cost;
            partsCost.setText(String.valueOf(part_cost) + "/=");
            totalDiscount.setText(String.valueOf(total_discount) + "/=");
            totalAmount.setText(String.valueOf(amount) + "/=");
    }

    public void ConfirmPaymentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        ArrayList<Part_Details> parts=new ArrayList<>();
        ArrayList<String> partids=new ArrayList<>();

        for (CartTm tempTm:list) {
            parts.add(
                    new Part_Details(
                            Reg_No.getText(),
                            tempTm.getP_Id(),
                            tempTm.getP_Name(),
                            tempTm.getPrice(),
                            (tempTm.getQty()),
                            lblBill_Id.getText(),
                            B_Id
                    ));
            partids.add(tempTm.getP_Id());
        }
//=========================Check====================================================
       for (int i=0;i<parts.size();i++){
          Part_Details p=new Part_DetailController().getPartsUsingidAndRegNo(String.valueOf(partids.get(i)),Reg_No.getText());
          if(p!=null){
              new Alert(Alert.AlertType.WARNING,"This Part is Exists.").show();
              clear();
              list.remove(0, list.size());
              tblBill.refresh();
              calculatebill();
              return;
          }
       }

//==================================================================================

        Bill bill=new Bill(
                lblBill_Id.getText(), java.sql.Date.valueOf(Date),Time,part_cost,Double.parseDouble(MechanicCost.getText()),
                total_discount,amount,UserId,cmbBookingId.getValue(),parts
        );
        try {
            if (new BillController().placeBill(bill)){
                new Alert(Alert.AlertType.CONFIRMATION,"Success.").show();
                Bill_Id=lblBill_Id.getText();
                CName=CustName.getText();
                regNo=Reg_No.getText();
                Tdiscount=total_discount;
                Price=amount;
                pcost=part_cost;
                mcost=Double.parseDouble(MechanicCost.getText());
                openBill();
                clear();
                Reg_No.setText("");
                CustName.setText("");
                cmbBookingId.setValue(null);
                list.remove(0, list.size());
                tblBill.refresh();
                calculatebill();
            }else{
                new Alert(Alert.AlertType.WARNING,"Try Again.").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void openBill() {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/Bill.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);

            HashMap Hmap=new HashMap();
            Hmap.put("Bill Id",Bill_Id);
            Hmap.put("User Name",UserName);
            Hmap.put("CustomerName",CName);
            Hmap.put("VehicleNo",regNo);
            Hmap.put("TotalDiscount",Tdiscount);
            Hmap.put("NetAmount",Price);
            Hmap.put("PartCost",pcost);
            Hmap.put("MechanicCost",mcost);

            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, Hmap, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
            // JasperPrintManager.printReport(jasperPrint,false);

        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void clear() throws SQLException, ClassNotFoundException {
            lblBill_Id.setText(new BillController().getBillId());
            MechanicCost.setText("0");
            Name.setText("");
            Brand.setText("");
            QtyAvailable.setText("");
            QtyForUse.setText("");
            part_cost=0.0;
            total_discount=0.0;
            amount=0.0;
            cmbPartIds.setValue(null);

    }

    public void ClearOnaction(ActionEvent actionEvent) {
        if (cartSelectedRowNo==-1){
            new Alert(Alert.AlertType.WARNING, "Please Select a row").show();
        }else{
            list.remove(cartSelectedRowNo);
            cartSelectedRowNo=-1;
            calculatebill();
            tblBill.refresh();
        }
    }

    public void QtyKeyReleased(KeyEvent keyEvent) {

        Object response = ValidationUtil.validate(map,btnAddtoCart);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                btnAddtoCart.requestFocus();
            }
        }
    }

    private void EditValidateData() {
        map.clear();
        map.put(MechanicCost,mechanicCostPattern);
    }

    public void MechanicCostKeyReleased(KeyEvent keyEvent) {
        EditValidateData();

        Object response = ValidationUtil.validate(map,btnConfirmPayment);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                btnConfirmPayment.requestFocus();
            }
        }
    }

}
