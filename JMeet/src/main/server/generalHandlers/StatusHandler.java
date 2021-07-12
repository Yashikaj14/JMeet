package generalHandlers;

import constants.Status;
import mainClasses.Main;
import request.SetStatusRequest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatusHandler {
    public static boolean setStatus(SetStatusRequest request){
        String getQuery = "SELECT * FROM statuses WHERE user_id = ?";
        try {
            PreparedStatement getStatement = Main.connection.prepareStatement(getQuery);
            getStatement.setString(1, request.getUserID());
            ResultSet rs = getStatement.executeQuery();
            if(rs.next()){
                String updateQuery = "UPDATE statuses SET status = ? where user_id = ?";
                PreparedStatement updateStatement = Main.connection.prepareStatement(updateQuery);
                updateStatement.setString(1, request.getStatus().toString());
                updateStatement.setString(2, request.getUserID());
                updateStatement.execute();
            } else {
                String insertQuery = "INSERT INTO statuses (user_id, status) values (?,?)";
                PreparedStatement insertStatement = Main.connection.prepareStatement(insertQuery);
                insertStatement.setString(1, request.getUserID());
                insertStatement.setString(2, request.getStatus().toString());
                insertStatement.execute();
            }
            return true;
        } catch (SQLException se){
            se.printStackTrace();
        }
        return false;
    }
}
