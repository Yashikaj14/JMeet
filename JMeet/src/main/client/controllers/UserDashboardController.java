package controllers;

import constants.ResponseCode;
import constants.Status;
import data.Meeting;
import data.Notification;
import data.User;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import mainApp.Main;
import request.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class UserDashboardController {
    @FXML
    private AnchorPane primaryPane;
    @FXML
    private Button logoutButton, ExitButton, scheduleButton;
    @FXML
    private Label nameLabel, userNameLabel, emailLabel, organizationLabel, notificationLabel;
    @FXML
    private ComboBox<Status> statusBox;
    private ObservableList<Meeting> meetings = FXCollections.observableArrayList();
    private ObservableList<Notification> notifications = FXCollections.observableArrayList();

    public void initialize(){
        User user = Main.user;
        statusBox.getItems().setAll(Status.ONLINE, Status.OFFLINE, Status.AWAY, Status.BUSY, Status.IN_CALL);
        nameLabel.setText(user.getFullName());
        userNameLabel.setText(user.getUserID());
        emailLabel.setText(user.getEmail());
        organizationLabel.setText(user.getOrganization());
        statusBox.getSelectionModel().select(Status.ONLINE);

        new Thread(new Runnable() {
            @Override
            public void run() {
                GetNotificationRequest request = new GetNotificationRequest(Main.user.getUserID());
                try {
                    Main.oosTracker.writeObject(request);
                    Response response = (Response) Main.oisTracker.readObject();
                    ArrayList<Notification> notificationArrayList = (ArrayList<Notification>) response.getResponseObject();
                    notifications.setAll(notificationArrayList);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            int count = 0;
                            for (var a:
                                 notifications) {
                                if(!a.isRead())
                                    count++;
                            }
                            notificationLabel.setText(String.valueOf(count));
                        }
                    });

                } catch (IOException | ClassNotFoundException ie){
                    ie.printStackTrace();
                }
            }
        }).start();


    }

    public void setStatusClicked(){
        Status status = statusBox.getSelectionModel().getSelectedItem();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SetStatusRequest setStatusRequest = new SetStatusRequest(status, Main.user.getUserID());
                try {
                    Main.oosTracker.writeObject(setStatusRequest);
                    Response response = (Response) Main.oisTracker.readObject();

                } catch (IOException | ClassNotFoundException ie){
                    ie.printStackTrace();
                }
            }
        }).start();
    }

    public void onMeetingsClicked(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(primaryPane.getScene().getWindow());
        dialog.setTitle("Meetings");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                UpcomingMeetingRequest request = new UpcomingMeetingRequest(Main.user.getUserID());

                try {
                    Main.oosTracker.writeObject(request);
                    Response response = (Response) Main.oisTracker.readObject();
                    ArrayList<Meeting> meeting = (ArrayList<Meeting>) response.getResponseObject();
                    meetings.setAll(meeting);

                } catch (IOException | ClassNotFoundException ie){
                    ie.printStackTrace();
                }
            }
        });
        t.start();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/UpMeetings.fxml"));
        try {
            dialog.getDialogPane().setContent(loader.load());
        }catch (IOException ie){
            ie.printStackTrace();
        }
        UpcomingMeetingController controller = loader.getController();
        controller.setData(meetings);
        dialog.showAndWait();
    }

    public void onScheduleClicked(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(primaryPane.getScene().getWindow());
        dialog.setTitle("Schedule Meeting");
        dialog.getDialogPane().getButtonTypes().add(new ButtonType("Schedule", ButtonBar.ButtonData.OK_DONE));
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ScheduleMeeting.fxml"));
        try {
            dialog.getDialogPane().setContent(loader.load());
        } catch (IOException ie){
            ie.printStackTrace();
        }
        ScheduleMeetingDialog scheduleMeetingDialog = loader.getController();
        Optional<ButtonType> result = dialog.showAndWait();


        if(result.isPresent() && !result.get().equals(ButtonType.CANCEL)){
            Meeting meeting = scheduleMeetingDialog.getData();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    MeetingScheduleRequest request = new MeetingScheduleRequest(meeting);
                    try {
                        Main.oosTracker.writeObject(request);
                        Response response = (Response) Main.oisTracker.readObject();
                        if(response.getResponseCode().equals(ResponseCode.SUCCESS)){
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Meeting Scheduled");
                                    alert.setHeaderText("Meeting Successfully Scheduled");
                                    String content = "Meeting Link: " + (String) response.getResponseObject() + "\n" +
                                            "Meeting Host: " + meeting.getHostID();
                                    alert.setContentText(content);
                                    alert.showAndWait();
                                    Main.loadControl(primaryPane, "/UserDashboard.fxml");
                                }
                            });

                        }
                        else {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Meeting Not Scheduled");
                                    alert.setHeaderText("Unable to Schedule Meeting");
                                    String content = "Please Try again after some time";
                                    alert.setContentText(content);
                                    alert.showAndWait();
                                }
                            });

                        }

                    } catch (IOException | ClassNotFoundException ie){
                        ie.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public void onButtonClicked(ActionEvent ae){
        if(ae.getSource().equals(logoutButton)){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    LogoutRequest request = new LogoutRequest(Main.user);
                    try {
                        Main.oosTracker.writeObject(request);
                        Main.user = null;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Main.loadControl(primaryPane, "/Login.fxml");
                            }
                        });
                    } catch (IOException ie){
                        ie.printStackTrace();
                    }
                }
            }).start();
        } else if (ae.getSource().equals(ExitButton)){
            Platform.exit();
        }
    }
    public void onSearchUserClicked(){
        Main.loadControl(primaryPane, "/SearchUsers.fxml");
    }

    public void onNotificationsClicked(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(primaryPane.getScene().getWindow());
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.setTitle("Notifications");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Notifications.fxml"));
        try {
            dialog.getDialogPane().setContent(loader.load());
        } catch (IOException ie){
            ie.printStackTrace();
        }
        NotificationViewController controller = loader.getController();
        controller.setData(notifications);
        dialog.showAndWait();
        new Thread(new Runnable() {
            @Override
            public void run() {
                ReadAllNotificationRequest request = new ReadAllNotificationRequest(Main.user.getUserID());
                try {
                    Main.oosTracker.writeObject(request);
                    Response response = (Response) Main.oisTracker.readObject();
                    if(response.getResponseCode().equals(ResponseCode.SUCCESS)){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Main.loadControl(primaryPane, "/UserDashboard.fxml");
                            }
                        });
                    }
                } catch (IOException | ClassNotFoundException ie){
                    ie.printStackTrace();
                }

            }
        }).start();
    }

}
