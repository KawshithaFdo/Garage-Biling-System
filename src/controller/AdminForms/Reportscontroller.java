package controller.AdminForms;

import controller.DataController.BillController;
import controller.DataController.BookingController;
import database.DbConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import model.Booking;
import model.getBill;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;

public class Reportscontroller {
    public AnchorPane AdminReportsContext;
    public DatePicker date;

    public void Billreport(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/BillReport.jrxml"));
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

    public void DailyIncome(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (date.getValue()==null){
            new Alert(Alert.AlertType.WARNING,"Enter Date.").show();
            return;
        }else {
            getBill b = new BillController().getBillUsingdate(Date.valueOf(date.getValue()));
            if (b == null) {
                new Alert(Alert.AlertType.WARNING, "This date hasn't Bookings").show();
                return;
            }
            try {
                HashMap map = new HashMap();
                map.put("Date", java.sql.Date.valueOf(date.getValue()));
                JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/DailyIncome.jrxml"));
                JasperReport compileReport = JasperCompileManager.compileReport(design);

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
    }

    public void Supplierreport(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/SupplierReport.jrxml"));
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

    public void MechanicSalary(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/MechanicSalaryReport.jrxml"));
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

    public void MechanicDetail(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/MechanicDetailReport.jrxml"));
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
