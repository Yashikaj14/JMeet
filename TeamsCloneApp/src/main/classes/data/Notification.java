package data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Notification implements Serializable {
    private String senderID;
    private String receiverID;
    private String notification;
    private LocalDate date;
    private LocalTime time;
    private boolean read;

    public Notification(String senderID, String receiverID, String notification, LocalDate date, LocalTime time) {
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.notification = notification;
        this.date = date;
        this.time = time;
        this.read = false;
    }

    public Notification(String senderID, String receiverID, String notification, LocalDate date, LocalTime time, boolean read) {
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.notification = notification;
        this.date = date;
        this.time = time;
        this.read = read;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    @Override
    public String toString() {
        return notification + ": " + senderID + " (" + date + " " + time + ")";
    }
}
