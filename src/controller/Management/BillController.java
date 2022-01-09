package controller.Management;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Bill;
import model.getBill;
import view.TM.BillTm;

import java.sql.SQLException;
import java.util.List;

public class BillController {
    public TableView<BillTm> tblBill_Management;
    public TableColumn colBillId;
    public TableColumn colBookingId;
    public TableColumn colDate;
    public TableColumn colTime;
    public TableColumn colPartCost;
    public TableColumn colMechanicCost;
    public TableColumn colDiscount;
    public TableColumn colAmount;



    public void initialize(){


        try {
            loadtable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadtable() throws SQLException, ClassNotFoundException {
        List<getBill> allBills=new controller.DataController.BillController().getAllBills();
        ObservableList<BillTm> list= FXCollections.observableArrayList();
        for (getBill b:allBills){
            list.add(new BillTm(
               b.getBill_No(),
               b.getDate(),
               b.getTime(),
               b.getPart_Cost(),
               b.getMechanic_Cost(),
               b.getDiscount(),
               b.getAmount(),
               b.getBooking_Id()
            ));
        }

        colBillId.setCellValueFactory(new PropertyValueFactory<>("billNo"));
        colBookingId.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colPartCost.setCellValueFactory(new PropertyValueFactory<>("partCost"));
        colMechanicCost.setCellValueFactory(new PropertyValueFactory<>("mechanicCost"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        tblBill_Management.getItems().setAll(list);
    }
}

