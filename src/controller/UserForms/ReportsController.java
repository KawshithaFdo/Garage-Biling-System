package controller.UserForms;

import com.jfoenix.controls.JFXButton;
import controller.DataController.BookingController;
import database.DbConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import model.Booking;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;

public class ReportsController {
    public AnchorPane UserReportsContext;
    public JFXButton btnDailyBookings;
    public DatePicker date;
    public String username;


    public void DailyBookingReports(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (date.getValue()==null){
            new Alert(Alert.AlertType.WARNING,"Enter Date.").show();
            return;
        }else {
            Booking b=new BookingController().getBookingUsingDate(Date.valueOf(date.getValue()));
            if (b==null){
                new Alert(Alert.AlertType.WARNING,"This date hasn't Bookings").show();
                return;
            }
            try {
                JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/DailyBookings.jrxml"));
                JasperReport compileReport = JasperCompileManager.compileReport(design);

                HashMap Hmap = new HashMap();
                Hmap.put("Bookingdate", (java.sql.Date.valueOf(date.getValue())));
                Hmap.put("Username", username);

                JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, Hmap, DbConnection.getInstance().getConnection());
                JasperViewer.viewReport(jasperPrint, false);
                // JasperPrintManager.printReport(jasperPrint,false);

            } catch (JRException e) {
                e.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void SparePartsReports(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/SparePartsDetails.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);

            HashMap map = new HashMap();
            map.put("Username", username);

            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
            // JasperPrintManager.printReport(jasperPrint,false);

        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void CustomerReports(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/CustomerDetails.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);

            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
            // JasperPrintManager.printReport(jasperPrint,false);

        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void VehicleReports(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/VehicleReport.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);

            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
            // JasperPrintManager.printReport(jasperPrint,false);

        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
