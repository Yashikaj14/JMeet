package generalHandlers;

import comUtils.EncryptDecrypt;
import data.Message;
import data.Notification;
import mainClasses.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MessageHandler {

    public static List<Message> getMessages(String userID){
        String getQuery = "SELECT * FROM messages where (sender_id = ? or receiver_id = ?)";
        List<Message> messages = new ArrayList<>();
        try {
            PreparedStatement getStatement = Main.connection.prepareStatement(getQuery);
            getStatement.setString(1, userID);
            getStatement.setString(2, userID);
            ResultSet rs = getStatement.executeQuery();
            while(rs.next()){
                String sender_id = rs.getString("sender_id");
                String message = rs.getString("message");
                String receiver_id = rs.getString("receiver_id");
                LocalDate date = LocalDate.parse(rs.getString("date"), Main.formatter);
                LocalTime time = LocalTime.parse(rs.getString("time"), Main.timeFormatter);
                boolean read = rs.getInt("is_read")!=0;
                messages.add(new Message(sender_id, receiver_id, message, date, time, read));
            }

        } catch (SQLException se){
            se.printStackTrace();
        }
        return messages;
    }

    public static List<Message> getMessages(String userID, String otherUserID){
        String getQuery = "SELECT * FROM messages where (sender_id = ? and receiver_id = ?)" +
                " OR (sender_id = ? and receiver_id = ?)";
        List<Message> messages = new ArrayList<>();
        try {
            PreparedStatement getStatement = Main.connection.prepareStatement(getQuery);
            getStatement.setString(1, userID);
            getStatement.setString(2, otherUserID);
            getStatement.setString(3, otherUserID);
            getStatement.setString(4, userID);
            ResultSet rs = getStatement.executeQuery();
            while(rs.next()){
                String sender_id = rs.getString("sender_id");
                String receiver_id = rs.getString("receiver_id");
                String message = EncryptDecrypt.decrypt(rs.getString("message"));
                LocalDate date = LocalDate.parse(rs.getString("date"), Main.formatter);
                boolean read = rs.getInt("is_read")!=0;
                LocalTime time = LocalTime.parse(rs.getString("time"), Main.timeFormatter);
                messages.add(new Message(sender_id, receiver_id, message, date, time, read));
            }

        } catch (Exception se){
            se.printStackTrace();
        }
        return messages;
    }

    public static void addMessage(Message message){
        String insertQuery = "INSERT INTO messages (sender_id, receiver_id, message, date, time, is_read) " +
                "VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement insertStatement = Main.connection.prepareStatement(insertQuery);
            insertStatement.setString(1, message.getSenderID());
            insertStatement.setString(2, message.getReceiverID());
            insertStatement.setString(3, EncryptDecrypt.encrypt(message.getText()));
            insertStatement.setString(4, message.getDate().format(Main.formatter));
            insertStatement.setString(5, message.getTime().format(Main.timeFormatter));
            insertStatement.setInt(6, message.isRead()?1:0);
            insertStatement.execute();
            String content = "You have a new Message from " + message.getSenderID() + " (" + message.getDate() + message.getTime() + ")";
            Notification notification = new Notification(message.getSenderID(), message.getReceiverID(), content, LocalDate.now(), LocalTime.now());

            NotificationHandler.addNotification(notification);
        } catch (Exception se){
            se.printStackTrace();
        }
    }

}
