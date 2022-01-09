package controller.DataController;

import database.DbConnection;
import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillController {
    public boolean placeBill(Bill b) throws SQLException{
        Connection con= null;
        try {
            con = DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stm = con.prepareStatement("INSERT INTO Bill VALUES (?,?,?,?,?,?,?,?,?)");
            stm.setObject(1,b.getBill_No());
            stm.setObject(2,b.getDate());
            stm.setObject(3,b.getTime());
            stm.setObject(4,b.getPart_Cost());
            stm.setObject(5,b.getMechanic_Cost());
            stm.setObject(6,b.getDiscount());
            stm.setObject(7,b.getAmount());
            stm.setObject(8,b.getUser_Id());
            stm.setObject(9,b.getBooking_Id());

            if (stm.executeUpdate()>0){

                if (saveBillDetail(b.getBill_No(),b.getParts())){
                    con.commit();
                    return true;
                }else{
                    con.rollback();
                    return false;
                }
            }else{
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
    public String getBillId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Bill ORDER BY Bill_No DESC LIMIT 1").executeQuery();
        if (rst.next()){
            int tempid = Integer.parseInt(rst.getString(1).split("-")[1]);
            tempid=tempid+1;
            if(tempid<9){
                return "Id-00"+tempid;
            }else if(tempid<99){
                return "Id-0"+tempid;
            }else{
                return "Id-"+tempid;
            }

        }else{
            return "Id-001";
        }
    }
    public List<getBill> getAllBills() throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Bill");
        ResultSet rst = pstm.executeQuery();
        List<getBill> Bills = new ArrayList<>();
        while (rst.next()) {
            Bills.add(new getBill(
                    rst.getString("Bill_No"),
                    rst.getDate("Date"),
                    rst.getString("Time"),
                    rst.getDouble("Part_Cost"),
                    rst.getDouble("Mechanic_Cost"),
                    rst.getDouble("Discount"),
                    rst.getDouble("Amount"),
                    rst.getString("User_ID"),
                    rst.getString("Booking_ID")
            ));
        }
        return Bills;
    }
    public List<String> getBillIds() throws SQLException, ClassNotFoundException {

        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT Bill_No FROM Bill");

        ResultSet rst = stm.executeQuery();
        List<String> bills = new ArrayList<>();
        while (rst.next()) {
            bills.add(
                    rst.getString("Bill_No")
            );
        }
        return bills;

    }
    private boolean saveBillDetail(String billNo, ArrayList<Part_Details> parts) throws SQLException, ClassNotFoundException {
        for (Part_Details temp:parts) {
                PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO `Part_Detail` VALUES (?,?,?,?,?,?,?)");
                stm.setObject(1, temp.getReg_No());
                stm.setObject(2, temp.getP_Id());
                stm.setObject(3,temp.getName());
                stm.setObject(4, temp.getPrice());
                stm.setObject(5, temp.getQty_forUse());
                stm.setObject(6, billNo);
                stm.setObject(7, temp.getBooking_Id());

                if (stm.executeUpdate() > 0) {
                    if (updateqty(temp.getP_Id(), (temp.getQty_forUse()))) {

                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
        }
        return true;
    }
    private boolean updateqty(String partNo,int qty) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().
                prepareStatement("UPDATE Part SET Qty=(Qty-" + qty + ")WHERE P_ID='" + partNo + "'");
        return stm.executeUpdate()>0;
    }
    public getBill getBillUsingId(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Bill WHERE (Bill_No=?)");
        stm.setObject(1,id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new getBill(
                    rst.getString(1),
                    rst.getDate(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getDouble(5),
                    rst.getDouble(6),
                    rst.getDouble(7),
                    rst.getString(8),
                    rst.getString(9)
            );

        } else {
            return null;
        }
    }
    public getBill getBillUsingdate(Date date) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Bill WHERE (Date =?)");
        stm.setObject(1,date);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new getBill(
                    rst.getString(1),
                    rst.getDate(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getDouble(5),
                    rst.getDouble(6),
                    rst.getDouble(7),
                    rst.getString(8),
                    rst.getString(9)
            );

        } else {
            return null;
        }
    }
    public boolean updatebill(Bill b,int oldqty) throws SQLException{
        Connection con= null;
        try {
            con = DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stm = con.prepareStatement("UPDATE Bill SET Part_Cost=?,Mechanic_Cost=?,Discount=?,Amount=? WHERE Bill_No=?");
            stm.setObject(1,b.getPart_Cost());
            stm.setObject(2,b.getMechanic_Cost());
            stm.setObject(3,b.getDiscount());
            stm.setObject(4,b.getAmount());
            stm.setObject(5,b.getBill_No());

            if(stm.executeUpdate()>0){
                if (updateDetail(b.getParts(),oldqty)){
                    con.commit();
                    return true;
                }else{
                    con.rollback();
                    return false;
                }
            }else{
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
    public boolean updateDetail(ArrayList<Part_Details> parts,int oldqty) throws SQLException, ClassNotFoundException {
        for (Part_Details temp:parts) {
                PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Part_Detail SET Price=?,Qty_foruse=? WHERE (Reg_No=?) AND (P_ID=?)");
                stm.setObject(1, temp.getPrice());
                stm.setObject(2,temp.getQty_forUse());
                stm.setObject(3,temp.getReg_No());
                stm.setObject(4,temp.getP_Id());

                if (stm.executeUpdate()>0){
                    if (updatepartQty(temp.getP_Id(),temp.getQty_forUse(),oldqty)){
                        return true;
                    }else{
                        return false;
                    }

                }
                return false;
        }
        return false;
    }
    private boolean updatepartQty(String p_id, int qty_forUse, int oldqty) throws SQLException, ClassNotFoundException {
            PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Part SET Qty=(Qty+?)-? WHERE P_ID=?");
            stm.setObject(1,oldqty);
            stm.setObject(2,qty_forUse);
            stm.setObject(3,p_id);
            return stm.executeUpdate()>0;
    }
}
