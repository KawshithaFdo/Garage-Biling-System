package controller.DataController;

import database.DbConnection;
import model.Admin;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminController {
    public Admin getAdmin(String id) throws SQLException, ClassNotFoundException {

        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Admin WHERE (Admin_ID=?)");
        stm.setObject(1,id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Admin(
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
}
