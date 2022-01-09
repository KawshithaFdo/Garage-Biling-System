package controller.DataController;

import database.DbConnection;
import model.Admin;
import model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerController {
    public boolean saveCustomer(Customer c) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO Customer VALUES (?,?,?,?,?,?)");
        stm.setObject(1,c.getCust_ID());
        stm.setObject(2,c.getName());
        stm.setObject(3,c.getAddress());
        stm.setObject(4,c.getContact());
        stm.setObject(5,c.getnIC());
        stm.setObject(6,c.getUser_ID());

        return stm.executeUpdate()>0;
    }
    public boolean updateCustomer(Customer c) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().
                prepareStatement("UPDATE Customer SET Name=?,Address=?,Contact=?,NIC=?,User_ID=? WHERE Cust_ID=?");
        stm.setObject(1,c.getName());
        stm.setObject(2,c.getAddress());
        stm.setObject(3,c.getContact());
        stm.setObject(4,c.getnIC());
        stm.setObject(5,c.getUser_ID());
        stm.setObject(6,c.getCust_ID());
        return stm.executeUpdate()>0;
    }
    public String getCustomerId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Customer ORDER BY Cust_ID DESC LIMIT 1").executeQuery();
        if (rst.next()){
            int tempid = Integer.parseInt(rst.getString(1).split("-")[1]);
            tempid=tempid+1;
            if(tempid<9){
                return "C-00"+tempid;
            }else if(tempid<99){
                return "C-0"+tempid;
            }else{
                return "C-"+tempid;
            }

        }else{
            return "C-001";
        }
    }
    public Customer getCustomerUsingId(String id) throws SQLException, ClassNotFoundException {

        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Customer WHERE (Cust_ID=?)");
        stm.setObject(1,id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Customer(
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
    public Customer getCustomerUsingNIC(String nic) throws SQLException, ClassNotFoundException {

        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Customer WHERE (NIC=?)");
        stm.setObject(1,nic);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Customer(
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
    public List<Customer> getAllCustomers() throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Customer");
        ResultSet rst = pstm.executeQuery();
        List<Customer> customers = new ArrayList<>();
        while (rst.next()) {
            customers.add(new Customer(
                    rst.getString("Cust_ID"),
                    rst.getString("Name"),
                    rst.getString("Address"),
                    rst.getString("Contact"),
                    rst.getString("NIC"),
                    rst.getString("User_ID")
            ));
        }
        return customers;
    }
    public static boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM Customer WHERE Cust_ID=?");
        pstm.setObject(1,id);
        return pstm.executeUpdate() > 0;
    }
    public static List<Customer> searchCustomer(String value) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM customer WHERE Name LIKE '%"+value+"%'");
        ResultSet rs = pstm.executeQuery();

        List<Customer> customers = new ArrayList<>();

        while (rs.next()) {
            customers.add(new Customer(
                    rs.getString("Cust_ID"),
                    rs.getString("Name"),
                    rs.getString("Address"),
                    rs.getString("Contact"),
                    rs.getString("NIC"),
                    rs.getString("User_ID")
            ));
        }

        return customers;
    }
}
