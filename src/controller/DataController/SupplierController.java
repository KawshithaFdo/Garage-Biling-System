package controller.DataController;

import database.DbConnection;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierController {
    public boolean saveSupplier(Supplier s) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO Supplier VALUES (?,?,?,?,?,?)");
        stm.setObject(1,s.getSup_Id());
        stm.setObject(2,s.getName());
        stm.setObject(3,s.getContact());
        stm.setObject(4,s.getNic());
        stm.setObject(5,s.getCompany());
        stm.setObject(6,s.getAdmin_Id());

        return stm.executeUpdate()>0;
    }
    public boolean updateSupplier(Supplier s) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().
                prepareStatement("UPDATE Supplier SET Name=?,Contact=?,NIC=?,Company=?,Admin_ID=? WHERE Sup_ID=?");
        stm.setObject(1,s.getName());
        stm.setObject(2,s.getContact());
        stm.setObject(3,s.getNic());
        stm.setObject(4,s.getCompany());
        stm.setObject(5,s.getAdmin_Id());
        stm.setObject(6,s.getSup_Id());


        return stm.executeUpdate()>0;
    }
    public String getSupplierid() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Supplier ORDER BY Sup_ID DESC LIMIT 1").executeQuery();
        if (rst.next()){
            int tempid = Integer.parseInt(rst.getString(1).split("-")[1]);
            tempid=tempid+1;
            if(tempid<9){
                return "S-00"+tempid;
            }else if(tempid<99){
                return "S-0"+tempid;
            }else{
                return "S-"+tempid;
            }

        }else{
            return "S-001";
        }
    }
    public Supplier getSupplier(String id) throws SQLException, ClassNotFoundException {

        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Supplier WHERE (Sup_ID=?)");
        stm.setObject(1,id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Supplier(
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
    public Supplier getSupplierUsingNic(String id) throws SQLException, ClassNotFoundException {

        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Supplier WHERE (NIC=?)");
        stm.setObject(1,id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Supplier(
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
    public List<Supplier> getAllSuppliers() throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Supplier");
        ResultSet rst = pstm.executeQuery();
        List<Supplier> suppliers = new ArrayList<>();
        while (rst.next()) {
            suppliers.add(new Supplier(
                    rst.getString("Sup_ID"),
                    rst.getString("Name"),
                    rst.getString("Contact"),
                    rst.getString("NIC"),
                    rst.getString("Company"),
                    rst.getString("Admin_ID")
            ));
        }
        return suppliers;
    }
    public List<String> getAllSupplierIds() throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT Sup_ID FROM Supplier");
        ResultSet rst = pstm.executeQuery();
        List<String> suppliers = new ArrayList<>();
        while (rst.next()) {
            suppliers.add(
                    rst.getString("Sup_ID")
            );
        }
        return suppliers;
    }
    public static boolean deleteSupplier(String id) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM Supplier WHERE Sup_ID=?");
        pstm.setObject(1,id);
        return pstm.executeUpdate() > 0;
    }
}
