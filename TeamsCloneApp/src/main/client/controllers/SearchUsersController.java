package controllers;

import constants.Status;
import data.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;
import mainApp.Main;
import request.GetUsersRequest;
import request.Response;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

public class SearchUsersController {
    @FXML
    private AnchorPane primaryPane;
    @FXML
    private TextField searchField;
    @FXML
    private Button backButton, searchButton;
    @FXML
    private ListView<Pair<User, Status>> userListView;
    private ObservableList<Pair<User, Status>> userList = FXCollections.observableArrayList();
    private FilteredList<Pair<User, Status>> userFilter;
    private Predicate<Pair<User, Status>> allUsers, searchUsers;

    public void initialize(){
        userListView.setCellFactory(new Callback<ListView<Pair<User, Status>>, ListCell<Pair<User, Status>>>() {
            @Override
            public ListCell<Pair<User, Status>> call(ListView<Pair<User, Status>> pairListView) {
                ListCell<Pair<User, Status>> pairListCell = new ListCell<>(){
                    @Override
                    protected void updateItem(Pair<User, Status> userStatusPair, boolean b) {
                        super.updateItem(userStatusPair, b);
                        if (b){
                            setText(null);
                        }
                        else {
                            setText(userStatusPair.getKey().toString());
                            setTextFill(Color.BLACK);
                            setFont(new Font("Times New Roman", 16));
                        }
                    }
                };

                return pairListCell;
            }
        });

        userListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent click) {

                if (click.getClickCount() == 2) {
                    onDoubleClicked();
                }
            }
        });

        allUsers = new Predicate<Pair<User, Status>>() {
            @Override
            public boolean test(Pair<User, Status> userStatusPair) {
                return true;
            }
        };

        userFilter = new FilteredList<>(userList);

        new Thread(new Runnable() {
            @Override
            public void run() {
                GetUsersRequest request = new GetUsersRequest();
                try {
                    Main.oosTracker.writeObject(request);
                    Response response = (Response) Main.oisTracker.readObject();
                    List<Pair<User, Status>> pairList = (List<Pair<User, Status>>) response.getResponseObject();
                    userList.setAll(pairList);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if(userList.isEmpty()){
                                System.out.println("It is empty");
                            }
                            for (var a:
                                 userList) {
                                System.out.println(a.getKey().getUserID());
                            }
                            userListView.setItems(userFilter);
                        }
                    });

                } catch (IOException | ClassNotFoundException ie){
                    ie.printStackTrace();
                }
            }
        }).start();
    }

    public void onButtonClicked(ActionEvent ae){
        if(ae.getSource().equals(backButton)){
            Main.loadControl(primaryPane, "/UserDashboard.fxml");
        }
    }

    public void searchButtonPressed(){
        searchUsers = new Predicate<Pair<User, Status>>() {
            @Override
            public boolean test(Pair<User, Status> userStatusPair) {
                return (userStatusPair.getKey().getUserID().contains(searchField.getCharacters()) || userStatusPair.getKey().getFullName().contains(searchField.getCharacters()) || userStatusPair.getKey().getEmail().contains(searchField.getCharacters()));
            }
        };
        userFilter.setPredicate(searchUsers);
    }

    public void onDoubleClicked(){
        Stage primaryStage = (Stage) primaryPane.getScene().getWindow();
        Parent root = null;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/UserProfile.fxml"));
        try {
            root = loader.load();
        } catch (IOException ie){
            ie.printStackTrace();
        }
        UserProfileController controller = loader.getController();
        controller.setData(userListView.getSelectionModel().getSelectedItem());
        primaryStage.setScene(new Scene(root, 800, 600));
    }
}
