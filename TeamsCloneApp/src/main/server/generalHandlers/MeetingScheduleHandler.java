package generalHandlers;

import data.Meeting;
import data.Notification;
import mainClasses.Main;
import request.MeetingScheduleRequest;
import request.UpcomingMeetingRequest;
import tools.LinkHandler;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MeetingScheduleHandler {

    public static String scheduleMeeting(MeetingScheduleRequest request){
        String meetingQuery = "INSERT INTO meetings (title, host_id, meeting_link, date, time, details) " +
                "VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement statement = Main.connection.prepareStatement(meetingQuery);
            statement.setString(1, request.getMeeting().getTitle());
            statement.setString(2, request.getMeeting().getHostID());
            String link = LinkHandler.generateLink();
            statement.setString(3, link);
            statement.setString(4, request.getMeeting().getDate().format(Main.formatter));
            statement.setString(5, request.getMeeting().getTime().format(Main.timeFormatter));
            statement.setString(6, request.getMeeting().getDetails());
            statement.execute();

            String notification = "Meeting Scheduled on " + request.getMeeting().getDate().toString() + " " +
                    request.getMeeting().getTime() + ", Meeting Link: " + link;
            NotificationHandler.addNotification(new Notification(request.getMeeting().getHostID(), request.getMeeting().getHostID(),
                    notification, LocalDate.now(), LocalTime.now(), false));

            return link;
        } catch (SQLException se){
            se.printStackTrace();
        }
        return "#";
    }

    public static List<Meeting> getUpcomingMeetings(UpcomingMeetingRequest request){
        String meetingQuery = "SELECT * FROM meetings WHERE host_id = ?";
        List<Meeting> meetings = new ArrayList<>();
        try {
            PreparedStatement meetingStatement = Main.connection.prepareStatement(meetingQuery);
            meetingStatement.setString(1, request.getUserID());
            ResultSet rs = meetingStatement.executeQuery();
            while (rs.next()){
                String title = rs.getString("title");
                String hostID = rs.getString("host_id");
                String meeting_link = rs.getString("meeting_link");
                LocalDate date = LocalDate.parse(rs.getString("date"), Main.formatter);
                LocalTime time = LocalTime.parse(rs.getString("time"), Main.timeFormatter);
                String details = rs.getString("details");
                meetings.add(new Meeting(title, date, hostID, time, meeting_link, details));
            }

        } catch (SQLException ie){
            ie.printStackTrace();
        }
        return meetings;
    }
}
