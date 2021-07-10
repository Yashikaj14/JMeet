package generalHandlers;

import data.Notification;
import mainClasses.Main;
import request.GetNotificationRequest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class NotificationHandler {

    public static List<Notification> getNotifications(GetNotificationRequest request){
        String getQuery = "SELECT * FROM notifications WHERE receiver_id = ?";
        List<Notification> notifications = new ArrayList<>();
        try {
            PreparedStatement getStatement = Main.connection.prepareStatement(getQuery);
            getStatement.setString(1, request.getUserID());
            ResultSet rs = getStatement.executeQuery();
            while(rs.next()){
                String sender_id = rs.getString("sender_id");
                String receiver_id = rs.getString("receiver_id");
                String message = rs.getString("message");
                LocalDate date = LocalDate.parse(rs.getString("date"), Main.formatter);
                LocalTime time = LocalTime.parse(rs.getString("time"), Main.timeFormatter);
                boolean read = rs.getInt("is_read") !=0;
                notifications.add(new Notification(sender_id, receiver_id, message, date, time, read));
            }

        } catch (SQLException se){
            se.printStackTrace();
        }
        return notifications;
    }

    public static boolean addNotification(Notification notification){
        String insertQuery = "INSERT INTO notifications (sender_id, receiver_id, message, date, time, is_read) " +
                "values (?,?,?,?,?,?)";
        try {
            PreparedStatement statement = Main.connection.prepareStatement(insertQuery);
            statement.setString(1, notification.getSenderID());
            statement.setString(2, notification.getSenderID());
            statement.setString(3, notification.getNotification());
            statement.setString(4, notification.getDate().format(Main.formatter));
            statement.setString(5, notification.getTime().format(Main.timeFormatter));
            statement.setInt(6, notification.isRead()?1:0);
            statement.execute();

            return true;
        } catch (SQLException se){
            se.printStackTrace();
        }
        return false;
    }

    public static void readNotifications(String userID){
        String readQuery = "UPDATE notifications SET is_read = 1 where receiver_id = ?";
        try {
            PreparedStatement statement = Main.connection.prepareStatement(readQuery);
            statement.setString(1, userID);
            statement.execute();

        } catch (SQLException se){
            se.printStackTrace();
        }
    }

}
