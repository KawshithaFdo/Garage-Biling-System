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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.ValidationUtil;
import view.TM.BillTm;
import view.TM.Part_DetailsTm;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Pattern;

public class ManagePaymentController {
    public AnchorPane ManagePaymentContext;
    public ComboBox<String> cmbPartIds;
    public TextField MechanicCost;
    public TableView<Part_DetailsTm> tblEditBill;
    public TableColumn colPartId;
    public TableColumn colPrice;
    public JFXTextField Name;
    public JFXTextField Brand;
    public Label partsCost;
    public Label totalDiscount;
    public Label totalAmount;
    public ComboBox<String> cmbBillId;
    public JFXButton btnremovepart;
    public String vehicleNo;
    public String Date;
    public String Time;
    public String BillId;
    public TableColumn colReg_No;
    public TableColumn colQty;
    public JFXButton btnUpdateParts;
    public TextField QtyNew;
    public JFXTextField QtyOld;
    public String userId;
    public String bookingId;
    public int selectedid=-1;
    String Bill_Id;
    public String UserName;
    String CName;
    String regNo;
    Double Tdiscount;
    Double Price;
    Double pcost;
    Double mcost;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern qtyNew = Pattern.compile("^[0-9]{1,}$");
    Pattern mechanicCost = Pattern.compile("^[0-9]{1,}[.]?[0-9]{1,2}?$");

    public void initialize() {
        btnremovepart.setDisable(true);
        cmbPartIds.setDisable(true);
        btnUpdateParts.setDisable(true);
        ValidateData();

        colPartId.setCellValueFactory(new PropertyValueFactory<>("p_Id"));
        colReg_No.setCellValueFactory(new PropertyValueFactory<>("reg_No"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty_forUse"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        dateandtime();
            try {
                loadBillIddata();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            cmbBillId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                BillId=cmbBillId.getValue();

                try {
                        loadPartIddata();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                cmbPartIds.setDisable(false);
                try {
                    loadData();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    getBill b=new BillController().getBillUsingId(BillId);
                    if (b!=null) {
                        totalAmount.setText(String.valueOf(b.getAmount()));
                        MechanicCost.setText(String.valueOf(b.getMechanic_Cost()));
                        partsCost.setText(String.valueOf(b.getPart_Cost()));
                        totalDiscount.setText(String.valueOf(b.getDiscount()));
                        userId = b.getUser_Id();
                        bookingId = b.getBooking_Id();
                    }else{
                        totalAmount.setText("");
                        MechanicCost.setText("");
                        partsCost.setText("");
                        totalDiscount.setText("");
                        userId=null;
                        bookingId=null;
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
            tblEditBill.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
                selectedid=1;
                if (selectedid==1){
                    btnremovepart.setDisable(false);
                }
            });

            cmbPartIds.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                try {
                        loadPartData();
                        btnUpdateParts.setDisable(false);

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });


    }

    private void ValidateData() {
        map.put(QtyNew,qtyNew);
        map.put(MechanicCost,mechanicCost);
    }

    private void loadPartData() throws SQLException, ClassNotFoundException {
        Part p=new PartController().getParts(String.valueOf(cmbPartIds.getValue()));
        if (p!=null){
            Name.setText(p.getName());
            Brand.setText(p.getBrand());
            getBill b=new BillController().getBillUsingId(cmbBillId.getValue());
            vehicleNo=new BookingController().getBookingUsingId(String.valueOf(b.getBooking_Id())).getReg_No();
            Part_Details allparts=new Part_DetailController().getPartsUsingidAndRegNo(String.valueOf(cmbPartIds.getValue()),vehicleNo);
            QtyOld.setText(String.valueOf(allparts.getQty_forUse()));
        }


    }

    private void loadPartIddata() throws SQLException, ClassNotFoundException {
        List<String> allparts=new Part_DetailController().getPartIds(cmbBillId.getValue());
        ObservableList<String> ids= FXCollections.observableArrayList();
        for (String s:allparts){
            ids.add(s);
        }
        cmbPartIds.setItems(ids);
    }

    private void loadBillIddata() throws SQLException, ClassNotFoundException {
        List<String> allBills=new BillController().getBillIds();
        ObservableList<String> ids= FXCollections.observableArrayList();
        for (String s:allBills){
            ids.add(s);
        }
        cmbBillId.setItems(ids);
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

    public void loadData() throws SQLException, ClassNotFoundException {
       List<Part_Details> allParts=new Part_DetailController().getParts(cmbBillId.getValue());
        ObservableList<Part_DetailsTm> list=FXCollections.observableArrayList();
        for (Part_Details p:allParts) {
            list.add(new Part_DetailsTm(
                    p.getReg_No(),
                    p.getP_Id(),
                    p.getBooking_Id(),
                    p.getPrice(),
                    p.getQty_forUse(),
                    p.getBill_No()
            ));
        }
        tblEditBill.getItems().setAll(list);
    }

    public void RemovepartOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (selectedid!=1){
            btnremovepart.setDisable(true);
            new Alert(Alert.AlertType.WARNING,"Plea select Part.").show();
            return;
        }
        String D_pId=tblEditBill.getSelectionModel().getSelectedItem().getP_Id();
        String D_VId=tblEditBill.getSelectionModel().getSelectedItem().getReg_No();
        int qty=tblEditBill.getSelectionModel().getSelectedItem().getQty_forUse();
//=================================Discount==================================================================
        Part p =new PartController().getParts(D_pId);
        Double getDis=p.getDiscount();
        Double getUnit=p.getUnitprice();

        Double deleteddiscount=(((getDis/100)*getUnit)*qty);
        Double oldDiscount=Double.parseDouble(totalDiscount.getText());
        Double newDiscount=oldDiscount-deleteddiscount;
        totalDiscount.setText(String.valueOf(newDiscount));
//==================================amount==================================================================
        Double oldPrice=Double.parseDouble(totalAmount.getText());
        Double newPrice=oldPrice-((getUnit*qty)-newDiscount);
        totalAmount.setText(String.valueOf(newPrice));
//===============================part cost=====================================================================
        Double OldpartCost=Double.parseDouble(partsCost.getText());
        Double NewPartCost=OldpartCost-((getUnit*qty)-newDiscount);
        partsCost.setText(String.valueOf(NewPartCost));



        if (new Part_DetailController().deletePartfromBill(D_pId,D_VId,qty,BillId,NewPartCost,newPrice,newDiscount)){
            new Alert(Alert.AlertType.CONFIRMATION,"Success").show();
            selectedid=0;
            D_pId="";
            D_VId="";
            loadData();
            btnremovepart.setDisable(true);
        }else{
            new Alert(Alert.AlertType.ERROR,"Try Again.").show();
        }
    }

    public void UpdateParts(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        int oldqty=Integer.parseInt(QtyOld.getText());
        int newqty=Integer.parseInt(QtyNew.getText());

        Part p =new PartController().getParts(cmbPartIds.getValue());
        Double unitprice=p.getUnitprice();
        Double discount=p.getDiscount();

    //=============================================================================================================
        Double olddiscount=Double.parseDouble(totalDiscount.getText());
        Double oldqtydiscount=(((discount/100)*unitprice)*oldqty);
        Double newqtydiscount=(((discount/100)*unitprice)*newqty);
        Double newdiscount=((olddiscount-oldqtydiscount)+newqtydiscount);
        totalDiscount.setText(String.valueOf(newdiscount));
    //============================================================================================================
        Double oldpartCost=Double.parseDouble(partsCost.getText());
        Double oldqtycost=(((oldqty*unitprice)-oldqtydiscount));
        Double newqtyCost=(((newqty*unitprice)-newqtydiscount));
        Double newpartcost=((oldpartCost-oldqtycost)+newqtyCost);
        partsCost.setText(String.valueOf(newpartcost));
    //=============================================================================================================
        Double mechaniccost=Double.parseDouble(MechanicCost.getText());
        Double newamount=(newpartcost+mechaniccost);
        totalAmount.setText(String.valueOf(newamount));

        Part p1=new PartController().getParts(cmbPartIds.getValue());
        String name=p1.getName();
        ArrayList<Part_Details> parts=new ArrayList<>();
            parts.add(new Part_Details(
                    vehicleNo,
                    cmbPartIds.getValue(),
                    name,
                    newqtyCost,
                    newqty,
                    cmbBillId.getValue(),
                    bookingId
            ));

        Bill b=new Bill(BillId, java.sql.Date.valueOf(Date),Time,newpartcost,mechaniccost,newdiscount,newamount,userId,bookingId,parts);

        if (new BillController().updatebill(b,oldqty)){
                new Alert(Alert.AlertType.CONFIRMATION,"Success").show();
                Bill_Id=BillId;
                regNo=vehicleNo;
                Tdiscount=newdiscount;
                Price=newamount;
                pcost=newpartcost;
                mcost=mechaniccost;
                PrintBill();
                clear();

        }else{
            new Alert(Alert.AlertType.ERROR,"Try Again.").show();
        }

    }

    private void PrintBill() {
        try {
            getnamestoBill();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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

    public void clear(){
        Name.setText("");
        Brand.setText("");
        QtyOld.setText("");
        QtyNew.setText("");
      cmbBillId.setValue(null);
    }

    public void EditkeyReleased(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnUpdateParts);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                btnUpdateParts.requestFocus();
            }
        }
    }

    public void getnamestoBill() throws SQLException, ClassNotFoundException {
        getBill b=new BillController().getBillUsingId(Bill_Id);
        String b_Id=b.getBooking_Id();
        Booking b1=new BookingController().getBookingUsingId(b_Id);
        regNo=b1.getReg_No();
        String cid=b1.getCust_Id();
        Customer c=new CustomerController().getCustomerUsingId(cid);
        CName=c.getName();
    }

}
