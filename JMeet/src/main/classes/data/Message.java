package data;

import javafx.scene.paint.Color;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Message implements Serializable {
    private String senderID;
    private String receiverID;
    private String text;
    private LocalDate date;
    private LocalTime time;
    private boolean read;

    public Message(String senderID, String receiverID, String text, LocalDate date, LocalTime time, boolean read) {
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.text = text;
        this.date = date;
        this.time = time;
        this.read = read;
    }

    public Message(String senderID, String receiverID, String text, LocalDate date, LocalTime time) {
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.text = text;
        this.date = date;
        this.time = time;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
        return  text + "\n(" +
                "date=" + date +
                ", time=" + time + ")";
    }
}
