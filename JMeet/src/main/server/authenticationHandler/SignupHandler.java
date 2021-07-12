package authenticationHandler;

import comUtils.EncryptDecrypt;
import constants.Status;
import mainClasses.Main;
import request.SignupRequest;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignupHandler {

    public static boolean verifySignup(SignupRequest request){

        String registerQuery = "INSERT INTO users (user_id, password, email, firstname, lastname, organization) " +
                "VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement statement = Main.connection.prepareStatement(registerQuery);
            statement.setString(1, request.getUser().getUserID());
            statement.setString(2, EncryptDecrypt.encrypt(request.getUser().getPassword()));
            statement.setString(3, request.getUser().getEmail());
            statement.setString(4, request.getUser().getFirstname());
            statement.setString(5, request.getUser().getLastname());
            statement.setString(6, request.getUser().getOrganization());
            statement.execute();

            String statusQuery = "INSERT INTO statuses (user_id, status) values (?,?)";
            PreparedStatement statusStatement = Main.connection.prepareStatement(statusQuery);
            statusStatement.setString(1, request.getUser().getUserID());
            statusStatement.setString(2, Status.OFFLINE.toString());
            statusStatement.execute();

            return true;
        } catch (Exception se){
            se.printStackTrace();
        }


        return false;
    }

}
