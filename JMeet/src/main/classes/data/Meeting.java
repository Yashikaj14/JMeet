package data;


import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Meeting implements Serializable {
    private String title;
    private LocalDate date;
    private String hostID;
    private LocalTime time;
    private String meetingLink;
    private String details;

    public Meeting(String title, LocalDate date, String hostID, LocalTime time, String details) {
        this.title = title;
        this.date = date;
        this.hostID = hostID;
        this.time = time;
        this.details = details;
        this.meetingLink = "#";
    }

    public Meeting(String title, LocalDate date, String hostID, LocalTime time, String meetingLink, String details) {
        this.title = title;
        this.date = date;
        this.hostID = hostID;
        this.time = time;
        this.meetingLink = meetingLink;
        this.details = details;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMeetingLink() {
        return meetingLink;
    }

    public void setMeetingLink(String meetingLink) {
        this.meetingLink = meetingLink;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getHostID() {
        return hostID;
    }

    public void setHostID(String hostID) {
        this.hostID = hostID;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return title;
    }
}
