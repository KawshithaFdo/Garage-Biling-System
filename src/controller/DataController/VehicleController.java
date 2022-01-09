package controller.DataController;

import database.DbConnection;
import model.Admin;
import model.Customer;
import model.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleController {
    public boolean saveVehicle(Vehicle v) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO Vehicle VALUES (?,?,?,?,?,?)");
        stm.setObject(1,v.getReg_No());
        stm.setObject(2,v.getName());
        stm.setObject(3,v.getBrand());
        stm.setObject(4,v.getYom());
        stm.setObject(5,v.getFuel());
        stm.setObject(6,v.getCust_Id());

        return stm.executeUpdate()>0;
    }
    public boolean updateVehicle(Vehicle v) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().
                prepareStatement("UPDATE Vehicle SET Name=?,Brand=?,YOM=?,Fuel_Type=?,Cust_ID=? WHERE Reg_No=?");
        stm.setObject(1,v.getName());
        stm.setObject(2,v.getBrand());
        stm.setObject(3,v.getYom());
        stm.setObject(4,v.getFuel());
        stm.setObject(5,v.getCust_Id());
        stm.setObject(6,v.getReg_No());
        return stm.executeUpdate()>0;
    }
    public Vehicle getVehicleUsingId(String id) throws SQLException, ClassNotFoundException {

        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Vehicle WHERE (Reg_No=?)");
        stm.setObject(1,id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Vehicle(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            );

        } else {
            return null;
        }

    }
    public List<Vehicle> getAllVehicles() throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Vehicle");
        ResultSet rst = pstm.executeQuery();
        List<Vehicle> vehicles = new ArrayList<>();
        while (rst.next()) {
            vehicles.add(new Vehicle(
                    rst.getString("Reg_No"),
                    rst.getString("Name"),
                    rst.getString("Brand"),
                    rst.getString("YOM"),
                    rst.getString("Fuel_Type"),
                    rst.getString("Cust_ID")
            ));
        }
        return vehicles;
    }
    public static boolean deleteVehicle(String id) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM Vehicle WHERE Reg_No=?");
        pstm.setObject(1,id);
        return pstm.executeUpdate() > 0;
    }
}
