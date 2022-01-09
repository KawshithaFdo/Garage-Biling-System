package controller.DataController;

import database.DbConnection;
import model.Admin;
import model.Customer;
import model.Mechanic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MechanicController {
    public boolean saveMechanic(Mechanic m) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO Mechanic VALUES (?,?,?,?,?,?,?)");
        stm.setObject(1,m.getM_Id());
        stm.setObject(2,m.getName());
        stm.setObject(3,m.getAddress());
        stm.setObject(4,m.getContact());
        stm.setObject(5,m.getNic());
        stm.setObject(6,m.getSalary());
        stm.setObject(7,m.getAdmin_id());

        return stm.executeUpdate()>0;
    }
    public boolean updateMechanic(Mechanic m) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().
                prepareStatement("UPDATE Mechanic SET Name=?,Address=?,Contact=?,NIC=?,Salary=?,Admin_ID=? WHERE M_ID=?");
        stm.setObject(1,m.getName());
        stm.setObject(2,m.getAddress());
        stm.setObject(3,m.getContact());
        stm.setObject(4,m.getNic());
        stm.setObject(5,m.getSalary());
        stm.setObject(6,m.getAdmin_id());
        stm.setObject(7,m.getM_Id());

        return stm.executeUpdate()>0;
    }
    public String getMechanicId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Mechanic ORDER BY M_ID DESC LIMIT 1").executeQuery();
        if (rst.next()){
            int tempid = Integer.parseInt(rst.getString(1).split("-")[1]);
            tempid=tempid+1;
            if(tempid<9){
                return "M-00"+tempid;
            }else if(tempid<99){
                return "M-0"+tempid;
            }else{
                return "M-"+tempid;
            }

        }else{
            return "M-001";
        }
    }
    public Mechanic getMechanic(String id) throws SQLException, ClassNotFoundException {

        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Mechanic WHERE (M_ID=?)");
        stm.setObject(1,id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Mechanic(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getDouble(6),
                    rst.getString(7)
            );

        } else {
            return null;
        }

    }
    public Mechanic getMechanicUsingNic(String nic) throws SQLException, ClassNotFoundException {

        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Mechanic WHERE (NIC=?)");
        stm.setObject(1,nic);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Mechanic(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getDouble(6),
                    rst.getString(7)
            );

        } else {
            return null;
        }

    }
    public List<Mechanic> getAllMechanics() throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Mechanic");
        ResultSet rst = pstm.executeQuery();
        List<Mechanic> mechanics = new ArrayList<>();
        while (rst.next()) {
            mechanics.add(new Mechanic(
                    rst.getString("M_ID"),
                    rst.getString("Name"),
                    rst.getString("Address"),
                    rst.getString("Contact"),
                    rst.getString("NIC"),
                    rst.getDouble("Salary"),
                    rst.getString("Admin_ID")
            ));
        }
        return mechanics;
    }
    public static boolean deleteMechanic(String id) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM Mechanic WHERE M_ID=?");
        pstm.setObject(1,id);
        return pstm.executeUpdate() > 0;
    }
}
