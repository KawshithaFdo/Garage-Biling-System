package controller.DataController;

import database.DbConnection;
import model.Admin;
import model.Mechanic;
import model.Part;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PartController {
    public boolean AddParts(Part p) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO Part VALUES (?,?,?,?,?,?,?,?)");
        stm.setObject(1,p.getP_Id());
        stm.setObject(2,p.getName());
        stm.setObject(3,p.getCountry());
        stm.setObject(4,p.getBrand());
        stm.setObject(5,p.getQty());
        stm.setObject(6,p.getUnitprice());
        stm.setObject(7,p.getDiscount());
        stm.setObject(8,p.getSup_Id());

        return stm.executeUpdate()>0;
    }
    public boolean updatePart(Part p) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().
                prepareStatement("UPDATE Part SET Name=?,Country=?,Brand=?,Qty=?,Unitprice=?,Discount=?,Sup_ID=? WHERE P_ID=?");
        stm.setObject(1,p.getName());
        stm.setObject(2,p.getCountry());
        stm.setObject(3,p.getBrand());
        stm.setObject(4,p.getQty());
        stm.setObject(5,p.getUnitprice());
        stm.setObject(6,p.getDiscount());
        stm.setObject(7,p.getSup_Id());
        stm.setObject(8,p.getP_Id());

        return stm.executeUpdate()>0;
    }
    public String getPartId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Part ORDER BY P_ID DESC LIMIT 1").executeQuery();
        if (rst.next()){
            int tempid = Integer.parseInt(rst.getString(1).split("-")[1]);
            tempid=tempid+1;
            if(tempid<9){
                return "P-00"+tempid;
            }else if(tempid<99){
                return "P-0"+tempid;
            }else{
                return "P-"+tempid;
            }

        }else{
            return "P-001";
        }
    }
    public Part getParts(String id) throws SQLException, ClassNotFoundException {

        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Part WHERE (P_ID=?)");
        stm.setObject(1,id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Part(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getInt(5),
                    rst.getDouble(6),
                    rst.getDouble(7),
                    rst.getString(8)
            );

        } else {
            return null;
        }

    }
    public List<Part> getAllParts() throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Part");
        ResultSet rst = pstm.executeQuery();
        List<Part> parts = new ArrayList<>();
        while (rst.next()) {
            parts.add(new Part(
                    rst.getString("P_ID"),
                    rst.getString("Name"),
                    rst.getString("Country"),
                    rst.getString("Brand"),
                    rst.getInt("Qty"),
                    rst.getDouble("Unitprice"),
                    rst.getDouble("Discount"),
                    rst.getString("Sup_ID")
            ));
        }
        return parts;
    }
    public List<String> getAllPartIds() throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT P_ID FROM Part");
        ResultSet rst = pstm.executeQuery();
        List<String> parts = new ArrayList<>();
        while (rst.next()) {
            parts.add(
                    rst.getString("P_ID")
            );
        }
        return parts;
    }
    public static boolean deletePart(String id) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM Part WHERE P_ID=?");
        pstm.setObject(1,id);
        return pstm.executeUpdate() > 0;
    }
}
