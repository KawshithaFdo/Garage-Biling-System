package controller.DataController;

import database.DbConnection;
import model.Booking;
import model.Customer;
import model.Part;
import model.Part_Details;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Part_DetailController {
    public List<Part_Details> getParts(String id) throws SQLException, ClassNotFoundException {

        Connection con = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM Part_Detail WHERE Bill_No LIKE '%"+id+"%'");
        ResultSet rs = pstm.executeQuery();

        List<Part_Details> parts = new ArrayList<>();

        while (rs.next()) {
            parts.add(new Part_Details(
                    rs.getString("reg_No"),
                    rs.getString("p_Id"),
                    rs.getString("P_Name"),
                    rs.getDouble("price"),
                    rs.getInt("qty_forUse"),
                    rs.getString("bill_No"),
                    rs.getString("booking_Id")
            ));
        }

        return parts;

    }
    public Part_Details getPartsUsingidAndRegNo(String id,String no) throws SQLException, ClassNotFoundException {

        Connection con = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM Part_Detail WHERE (P_Id=?) AND (Reg_No=?)");
        pstm.setObject(1,id);
        pstm.setObject(2,no);

        ResultSet rst = pstm.executeQuery();
        if (rst.next()) {
            return new Part_Details(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getInt(5),
                    rst.getString(6),
                    rst.getString(7)
            );

        } else {
            return null;
        }


    }
    public List<String> getPartIds(String id) throws SQLException, ClassNotFoundException {

        PreparedStatement pstm = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT P_ID FROM Part_Detail WHERE Bill_No=?");
        pstm.setObject(1,id);
        ResultSet rst = pstm.executeQuery();
        List<String> parts = new ArrayList<>();
        while (rst.next()) {
            parts.add(
                    rst.getString("P_ID")
            );
        }
        return parts;

    }
    public boolean deletePartfromBill(String id,String no,int qty,String billno,Double partcost,Double amount,Double discount) throws SQLException{
        Connection con = null;
        try {
            con = DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement pstm = con.prepareStatement("delete from part_Detail where (P_Id=?) AND (Reg_No=?);");
            pstm.setObject(1,id);
            pstm.setObject(2,no);
            if(pstm.executeUpdate() > 0) {
                if (updateqty(qty,id) | updatebill(billno, partcost, discount, amount)){
                    con.commit();
                    return true;
                }else{
                    con.rollback();
                    return false;
                }
            }else {
                con.rollback();
                return false;
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            con.setAutoCommit(true);
        }
       return false;

    }
    private boolean updateqty(int qty,String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().
                prepareStatement("UPDATE Part SET Qty=(Qty+" + qty +")WHERE P_ID='" + id + "'");
        return stm.executeUpdate()>0;
    }
    private boolean updatebill(String bill_No,Double partCost,Double discount,Double amount) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().
                prepareStatement("UPDATE Bill SET Part_Cost=?,Discount=?,Amount=? WHERE Bill_No=?");
        stm.setObject(1,partCost);
        stm.setObject(2,discount);
        stm.setObject(3,amount);
        stm.setObject(4,bill_No);
        return (stm.executeUpdate()>0);
    }
}
