package controller.UserForms;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import controller.DataController.BookingController;
import controller.DataController.CustomerController;
import controller.DataController.VehicleController;
import controller.UserFormController;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Booking;
import model.Customer;
import model.Vehicle;
import util.ValidationUtil;
import view.TM.BookingTm;
import view.TM.VehicleTm;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class ManageBookingController {
    public Label BookingId;
    public TextField reg_No;
    public TextField Cust_Id;
    public TextField Custname;
    public JFXButton btnAddBooking;
    public AnchorPane ManageBookingContext;
    public TableColumn colCustomerId;
    public TableColumn colReg_No;
    public TableColumn colBookingId;
    public TableColumn colDate;
    public TableColumn colTime;
    public TableView<BookingTm> tblBooking;
    public JFXButton btnDeleteBooking;
    public DatePicker EditDate;
    public JFXTimePicker EditTime;
    public JFXButton btnUpdateBooking;
    public DatePicker Date;
    public JFXTimePicker Time;
    public String U_Id;
    public TextField EditBookingId;
    public String EditUser;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern reg_NoPattern = Pattern.compile("^[0-9]{2}[-][0-9]{4}|[A-Z]{2,3}[-][0-9]{4}$");
    Pattern idPattern = Pattern.compile("^[B](-00)[0-9]{1,4}$");


    public void initialize(){
        btnAddBooking.setDisable(true);
        Validatedata();
        btnUpdateBooking.setDisable(true);
        btnDeleteBooking.setDisable(true);
        try {
            BookingId.setText(new BookingController().getBookingId());
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
        tblBooking.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDeleteBooking.setDisable(false);
        });
    }

    private void Validatedata() {
        map.put(reg_No,reg_NoPattern);
    }

    public void loadTable() throws SQLException, ClassNotFoundException {
        List<Booking> allbookings=new BookingController().getBooking();
        ObservableList<BookingTm> bookingtable= FXCollections.observableArrayList();
        for (Booking b:allbookings) {
            bookingtable.add(new BookingTm(
                    b.getBooking_Id(),
                    b.getDate(),
                    b.getTime(),
                    b.getCust_Id(),
                    b.getReg_No(),
                    b.getUser_Id()
            ));

        }

        colBookingId.setCellValueFactory(new PropertyValueFactory<>("booking_Id"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("cust_Id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colReg_No.setCellValueFactory(new PropertyValueFactory<>("reg_No"));

        tblBooking.getItems().setAll(bookingtable);

    }

    public void OpenCustName() throws SQLException, ClassNotFoundException {
        Customer c1= new CustomerController().getCustomerUsingId(Cust_Id.getText());
        if (c1!=null){
            Custname.setText(c1.getName());
        }
    }

    public void OpenRegNo(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
       Vehicle v1= new VehicleController().getVehicleUsingId(reg_No.getText());
        if (v1==null){
            new Alert(Alert.AlertType.WARNING,"Please Enter Valid Reg_No.").show();
            return;
        }else{
            btnAddBooking.setDisable(false);
            Cust_Id.setText(v1.getCust_Id());
            OpenCustName();
            Date.setValue(LocalDate.now());
            Time.setValue(LocalTime.now());
        }
    }

    public void AddBooking_OnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Booking booking = new Booking(BookingId.getText(), java.sql.Date.valueOf(Date.getValue()),String.valueOf(Time.getValue()),Cust_Id.getText(),
                reg_No.getText(),U_Id);
        if (new BookingController().addBooking(booking)){
            new Alert(Alert.AlertType.CONFIRMATION,"Successful.").show();
            loadTable();
            clear();
        }else{
            new Alert(Alert.AlertType.ERROR,"Try Again.").show();
        }
    }

    public void UpdateBooking_OnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (EditDate.getValue()==null & EditTime.getValue()==null){
            new Alert(Alert.AlertType.WARNING,"PLease select Date & Time.").show();
            return;
        }
        Booking b1=new BookingController().getBookingUsingId(EditBookingId.getText());
            String R_No=b1.getReg_No();
            String Cust_id=b1.getCust_Id();
             Booking booking=new Booking(EditBookingId.getText(), java.sql.Date.valueOf(EditDate.getValue()),
                     String.valueOf(EditTime.getValue()),Cust_id,R_No,EditUser);
             if (new BookingController().updateBooking(booking)){
                 new Alert(Alert.AlertType.CONFIRMATION,"Updated.").show();
                 loadTable();
                 clear();
                 btnUpdateBooking.setDisable(true);
             }else{
                 new Alert(Alert.AlertType.ERROR,"Try Again.").show();
             }
    }

    public void openUpdatedata(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Booking b1=new BookingController().getBookingUsingId(EditBookingId.getText());
        if (b1==null) {
            new Alert(Alert.AlertType.ERROR, "Booking Id is invalid.").show();
            return;
        }else{
            btnUpdateBooking.setDisable(false);
            EditUser=b1.getUser_Id();
        }
    }

    public void DeleteBooking_OnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String D_id=tblBooking.getSelectionModel().getSelectedItem().getBooking_Id();
        if (new BookingController().deleteBookings(D_id)){
            new Alert(Alert.AlertType.CONFIRMATION,"Deleted.").show();
            loadTable();
            clear();
            btnDeleteBooking.setDisable(true);
        }else{
            new Alert(Alert.AlertType.ERROR,"Try Again.").show();
        }
    }

    public void clear() throws SQLException, ClassNotFoundException {
        BookingId.setText(new BookingController().getBookingId());
        reg_No.setText("");
        Cust_Id.setText("");
        Custname.setText("");

    }

    public void KeyReleased(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnAddBooking);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                btnAddBooking.requestFocus();
            }
        }
    }

    public void EditKeyreleased(KeyEvent keyEvent) {
        EditValidatedata();

        Object response = ValidationUtil.validate(map,btnUpdateBooking);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                btnUpdateBooking.requestFocus();
            }
        }
    }

    private void EditValidatedata() {
        map.clear();
        map.put(EditBookingId,idPattern);
    }
}
