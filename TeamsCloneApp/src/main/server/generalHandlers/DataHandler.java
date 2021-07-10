package generalHandlers;

import constants.Status;
import data.User;
import javafx.util.Pair;
import mainClasses.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataHandler {

    public static List<Pair<User, Status>> getUsers(){
        String userQuery = "SELECT * FROM users";
        List<Pair<User, Status>> users = new ArrayList<>();
        try {
            PreparedStatement userStatement = Main.connection.prepareStatement(userQuery);
            ResultSet rs = userStatement.executeQuery();
            while (rs.next()){
                String userID = rs.getString("user_id");
                String password = rs.getString("password");
                String email = rs.getString("email");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String organization = rs.getString("organization");
                String statusQuery = "SELECT * FROM statuses where user_id = ?";
                PreparedStatement statusStatement = Main.connection.prepareStatement(statusQuery);
                statusStatement.setString(1, userID);
                ResultSet statusRs = statusStatement.executeQuery();
                Status status = Status.OFFLINE;
                if(statusRs.next()){
                    status = Status.valueOf(statusRs.getString("status"));
                }
                users.add(new Pair<>(new User(userID, password, email, firstname, lastname, organization), status));
            }

        } catch (SQLException ie){
            ie.printStackTrace();
        }
        return users;
    }
}
