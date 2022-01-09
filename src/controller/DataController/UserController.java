package controller.DataController;

import database.DbConnection;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserController {
    public boolean saveUser(User u) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO User VALUES (?,?,?,?,?,?)");
        stm.setObject(1,u.getUser_ID());
        stm.setObject(2,u.getName());
        stm.setObject(3,u.getAddress());
        stm.setObject(4,u.getContact());
        stm.setObject(5,u.getnIC());
        stm.setObject(6,u.getPassword());

        return stm.executeUpdate()>0;
    }
    public String getUserId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM User ORDER BY User_ID DESC LIMIT 1").executeQuery();
        if (rst.next()){
            int tempid = Integer.parseInt(rst.getString(1).split("-")[1]);
            tempid=tempid+1;
            if(tempid<9){
                return "U-00"+tempid;
            }else if(tempid<99){
                return "U-0"+tempid;
            }else{
                return "U-"+tempid;
            }

        }else{
            return "U-001";
        }
    }
    public User getUser(String id) throws SQLException, ClassNotFoundException {

        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM User WHERE (User_ID=?)");
        stm.setObject(1,id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new User(
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
    public List<User> getAllUsers() throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM User");
        ResultSet rst = pstm.executeQuery();
        List<User> users = new ArrayList<>();
        while (rst.next()) {
            users.add(new User(
                    rst.getString("User_ID"),
                    rst.getString("Name"),
                    rst.getString("Address"),
                    rst.getString("Contact"),
                    rst.getString("NIC"),
                    rst.getString("Password")
            ));
        }
        return users;
    }
    public static boolean deleteUser(String id) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM User WHERE User_ID=?");
        pstm.setObject(1,id);
        return pstm.executeUpdate() > 0;
    }
}
