package controller.DataController;

import database.DbConnection;
import model.Booking;
import model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingController {
    public boolean addBooking(Booking b) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO Booking VALUES (?,?,?,?,?,?)");
        stm.setObject(1,b.getBooking_Id());
        stm.setObject(2,b.getDate());
        stm.setObject(3,b.getTime());
        stm.setObject(4,b.getCust_Id());
        stm.setObject(5,b.getReg_No());
        stm.setObject(6,b.getUser_Id());

        return stm.executeUpdate()>0;
    }
    public boolean updateBooking(Booking b) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().
                prepareStatement("UPDATE Booking SET Date=?,Time=?,Cust_ID=?,Reg_No=?,User_ID=? WHERE Booking_ID=?");
        stm.setObject(1,b.getDate());
        stm.setObject(2,b.getTime());
        stm.setObject(3,b.getCust_Id());
        stm.setObject(4,b.getReg_No());
        stm.setObject(5,b.getUser_Id());
        stm.setObject(6,b.getBooking_Id());
        return stm.executeUpdate()>0;
    }
    public String getBookingId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Booking ORDER BY Booking_ID DESC LIMIT 1").executeQuery();
        if (rst.next()){
            int tempid = Integer.parseInt(rst.getString(1).split("-")[1]);
            tempid=tempid+1;
            if(tempid<9){
                return "B-00"+tempid;
            }else if(tempid<99){
                return "B-0"+tempid;
            }else{
                return "B-"+tempid;
            }

        }else{
            return "B-001";
        }
    }
    public List<Booking> getBooking() throws SQLException, ClassNotFoundException {

        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Booking");

        ResultSet rst = stm.executeQuery();
        List<Booking> bookings = new ArrayList<>();
        while (rst.next()) {
            bookings.add(new Booking(
                    rst.getString("Booking_ID"),
                    rst.getDate("Date"),
                    rst.getString("Time"),
                    rst.getString("Cust_ID"),
                    rst.getString("Reg_No"),
                    rst.getString("User_ID")
            ));
        }
        return bookings;

    }
    public List<String> getBookingIds() throws SQLException, ClassNotFoundException {

        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT Booking_ID FROM Booking");

        ResultSet rst = stm.executeQuery();
        List<String> bookings = new ArrayList<>();
        while (rst.next()) {
            bookings.add(
                    rst.getString("Booking_ID")
            );
        }
        return bookings;

    }
    public Booking getBookingUsingId(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Booking WHERE (Booking_ID=?)");
        stm.setObject(1,id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Booking(
                    rst.getString(1),
                    rst.getDate(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            );

        } else {
            return null;
        }
    }
    public Booking getBookingUsingDate(Date date) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Booking WHERE (Date=?)");
        stm.setObject(1,date);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Booking(
                    rst.getString(1),
                    rst.getDate(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            );

        } else {
            return null;
        }
    }
    public  boolean deleteBookings(String id) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM Booking WHERE Booking_ID=?");
        pstm.setObject(1,id);
        return pstm.executeUpdate() > 0;
    }
}
