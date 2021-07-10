package controllers;

import data.Meeting;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import mainApp.Main;
import request.Response;
import request.UpcomingMeetingRequest;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class UpcomingMeetingController {
    @FXML
    private AnchorPane primaryPane;
    @FXML
    private ListView<Meeting> meetingListView;
    @FXML
    private Label titleLabel, dateLabel, timeLabel;
    @FXML
    private Hyperlink meetingLink;
    @FXML
    private TextArea detailArea;
    private ObservableList<Meeting> meetings = FXCollections.observableArrayList();
    private Predicate<Meeting> upcomingMeetings, allMeetings;

    public void initialize(){
        meetingListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Meeting>() {
            @Override
            public void changed(ObservableValue<? extends Meeting> observableValue, Meeting meeting, Meeting t1) {
                Meeting meet = meetingListView.getSelectionModel().getSelectedItem();
                if(meet != null){
                    titleLabel.setText(meet.getTitle());
                    dateLabel.setText(meet.getDate().toString());
                    timeLabel.setText(meet.getTime().toString());
                    meetingLink.setText(meet.getMeetingLink());
                    detailArea.setText(meet.getDetails());
                }
            }
        });
        upcomingMeetings = new Predicate<Meeting>() {
            @Override
            public boolean test(Meeting meeting) {
                return meeting.getDate().compareTo(LocalDate.now())>=0;
            }
        };


        allMeetings = new Predicate<Meeting>() {
            @Override
            public boolean test(Meeting meeting) {
                return true;
            }
        };
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                UpcomingMeetingRequest request = new UpcomingMeetingRequest(Main.user.getUserID());
//
//                try {
//                    Main.oosTracker.writeObject(request);
//                    Response response = (Response) Main.oisTracker.readObject();
//                    ArrayList<Meeting> meeting = (ArrayList<Meeting>) response.getResponseObject();
//                    meetings.setAll(meeting);
//                    Platform.runLater(new Runnable() {
//                        @Override
//                        public void run() {
//                            FilteredList<Meeting> meetingFilteredList = new FilteredList<>(meetings, upcomingMeetings);
//                            meetingListView.setItems(meetingFilteredList);
//                            meetingListView.getSelectionModel().selectFirst();
//                        }
//                    });
//                } catch (IOException | ClassNotFoundException ie){
//                    ie.printStackTrace();
//                }
//            }
//        }).start();
    }

    public void onLinkClicked(){
        try {
            Desktop.getDesktop().browse(new URL(meetingLink.getText()).toURI());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void setData(ObservableList<Meeting> meets){
        meetings.setAll(meets);
        meetingListView.setItems(meetings);
    }
}
