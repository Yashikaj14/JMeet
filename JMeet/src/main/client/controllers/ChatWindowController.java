package controllers;

import constants.Status;
import data.Message;
import data.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;
import mainApp.Main;
import request.GetMessagesRequest;
import request.Response;
import request.SendMessageRequest;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;

public class ChatWindowController {
    @FXML
    private AnchorPane primaryPane;
    @FXML
    private TextArea chatArea, messageArea;
    @FXML
    private Label chatLabel;
    @FXML
    private Button sendButton;
    @FXML
    private ListView<Message> messageListView;

    private Pair<User, Status> profileUser = UserProfileController.profileUser;
    private ObservableList<Message> messages = FXCollections.observableArrayList();
    private SortedList<Message> sortedMessages;

    public void initialize(){
        sortedMessages = new SortedList<>(messages, new Comparator<Message>() {
            @Override
            public int compare(Message o1, Message o2) {
                if(o1.getDate().equals(o2.getDate()))
                    return o1.getTime().compareTo(o2.getTime());
                return o1.getDate().compareTo(o2.getDate());
            }
        });
        messageListView.setItems(sortedMessages);

        messageListView.setCellFactory(new Callback<ListView<Message>, ListCell<Message>>() {
            @Override
            public ListCell<Message> call(ListView<Message> messageListView) {
                ListCell<Message> cell = new ListCell<>(){
                    @Override
                    protected void updateItem(Message message, boolean b) {
//                        super.updateItem(message, b);
                        if(b){
                            setText(null);
                        }else {
                            if(message.getReceiverID().equals(Main.user.getUserID())){
                                setText(message.toString());
                                setAlignment(Pos.CENTER_LEFT);
                            } else if(message.getReceiverID().equals(profileUser.getKey().getUserID())){
                                setText(message.toString());
                                setAlignment(Pos.CENTER_RIGHT);
                            }
                        }
                    }
                };

                return cell;
            }
        });

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                GetMessagesRequest request = new GetMessagesRequest(Main.user.getUserID(), profileUser.getKey().getUserID());
                try {
                    Main.oosTracker.writeObject(request);
                    Response response = (Response)Main.oisTracker.readObject();
                    ArrayList<Message> messageList = (ArrayList<Message>) response.getResponseObject();
                    messages.setAll(messageList);

                } catch (IOException | ClassNotFoundException ie){
                    ie.printStackTrace();
                }
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException ie){
            ie.printStackTrace();
        }

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while(true){
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException ie){
//                        ie.printStackTrace();
//                    }
////                    Platform.runLater();
//                }
//            }
//        }).start();
    }
    public void setData(Pair<User, Status> user){
        chatLabel.setText(user.getKey().getFullName());
        profileUser = user;
    }

    public void onSendMessageClicked(){
        String text = messageArea.getText();
        Message message = new Message(Main.user.getUserID(), profileUser.getKey().getUserID(), text, LocalDate.now(), LocalTime.now());
        messages.add(message);
        new Thread(new Runnable() {
            @Override
            public void run() {
                SendMessageRequest request = new SendMessageRequest(message);
                try {
                    Main.oosTracker.writeObject(request);
                    Response response = (Response) Main.oisTracker.readObject();

                } catch (IOException | ClassNotFoundException ie){
                    ie.printStackTrace();
                }
            }
        }).start();
    }

    public void onBackButtonClicked(){
        Parent root = null;
        Stage primaryStage = (Stage) primaryPane.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/UserProfile.fxml"));
        try {
            root = loader.load();
        } catch (IOException ie){
            ie.printStackTrace();
        }
        UserProfileController controller = loader.getController();
        controller.setData(profileUser);
        primaryStage.setScene(new Scene(root, 800, 600));
    }

}
