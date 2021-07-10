package authenticationHandler;

import comUtils.EncryptDecrypt;
import constants.Status;
import data.User;
import mainClasses.Main;
import request.LoginRequest;
import request.LogoutRequest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginHandler {
    public static User verifyLogin(LoginRequest loginRequest){
        String userQuery = "SELECT * FROM users WHERE (user_id = ? or email = ?) AND password = ?";

        try {
            PreparedStatement verifyStatement = Main.connection.prepareStatement(userQuery);
            verifyStatement.setString(1, loginRequest.getUsername());
            verifyStatement.setString(2, loginRequest.getUsername());
            verifyStatement.setString(3, EncryptDecrypt.encrypt(loginRequest.getPassword()));
            ResultSet result = verifyStatement.executeQuery();
            if(!result.next()){
                return null;
            }
            String onlineQuery = "SELECT * FROM online_users where user_id = ?";
            PreparedStatement onlineStatement = Main.connection.prepareStatement(onlineQuery);
            onlineStatement.setString(1, loginRequest.getUsername());
            ResultSet rs = onlineStatement.executeQuery();
            if(rs.next()){
                String updateQuery = "UPDATE online_users SET user_ip = ? where user_id = ?";
                PreparedStatement updateStatement = Main.connection.prepareStatement(updateQuery);
                updateStatement.setString(1, loginRequest.getIpAddress().getCanonicalHostName());
                updateStatement.setString(2, loginRequest.getUsername());
                updateStatement.execute();
            } else {
                String insertQuery = "INSERT INTO online_users (user_id, user_ip) values (?,?)";
                PreparedStatement insertStatement = Main.connection.prepareStatement(insertQuery);
                insertStatement.setString(1, loginRequest.getUsername());
                insertStatement.setString(2, loginRequest.getIpAddress().getCanonicalHostName());
                insertStatement.execute();
            }
            String statusQuery = "SELECT * FROM statuses where user_id = ?";
            PreparedStatement statusStatement = Main.connection.prepareStatement(statusQuery);
            statusStatement.setString(1, loginRequest.getUsername());
            rs = statusStatement.executeQuery();
            if(rs.next()){
                String updateQuery = "UPDATE statuses SET status = ? where user_id = ?";
                PreparedStatement updateStatement = Main.connection.prepareStatement(updateQuery);
                updateStatement.setString(1, Status.ONLINE.toString());
                updateStatement.setString(2, loginRequest.getUsername());
                updateStatement.execute();
            } else {
                String insertQuery = "INSERT INTO statuses (user_id, status) values (?,?)";
                PreparedStatement insertStatement = Main.connection.prepareStatement(insertQuery);
                insertStatement.setString(1, loginRequest.getUsername());
                insertStatement.setString(2, Status.ONLINE.toString());
                insertStatement.execute();
            }

            return new User(
                    result.getString("user_id"),
                    result.getString("password"),
                    result.getString("email"),
                    result.getString("firstname"),
                    result.getString("lastname"),
                    result.getString("organization")
            );

        } catch (Exception se){
            se.printStackTrace();
        }

        return null;
    }

    public static void handleLogout(LogoutRequest request){
        String deleteQuery = "DELETE FROM online_users where user_id = ?";
        try {
            PreparedStatement deleteStatement = Main.connection.prepareStatement(deleteQuery);
            deleteStatement.setString(1, request.getUser().getUserID());
            deleteStatement.execute();
            String updateQuery = "UPDATE statuses SET status = ? WHERE user_id = ?";
            PreparedStatement updateStatement = Main.connection.prepareStatement(updateQuery);
            updateStatement.setString(1, Status.OFFLINE.toString());
            updateStatement.setString(2, request.getUser().getUserID());
            updateStatement.execute();

        } catch (SQLException se){
            se.printStackTrace();
        }
    }

}
