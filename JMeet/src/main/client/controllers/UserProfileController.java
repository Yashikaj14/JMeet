package controllers;

import constants.Status;
import data.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import mainApp.Main;

import java.io.IOException;

public class UserProfileController {
    @FXML
    private AnchorPane primaryPane;
    @FXML
    private Label nameLabel, emailLabel, userNameLabel, organizationLabel, statusLabel;
    @FXML
    private Button chatButton, backButton;
    public static Pair<User, Status> profileUser;

    public void initialize(){

    }
    public void setData(Pair<User, Status> user){
        nameLabel.setText(user.getKey().getFullName());
        emailLabel.setText(user.getKey().getEmail());
        userNameLabel.setText(user.getKey().getUserID());
        organizationLabel.setText(user.getKey().getOrganization());
        statusLabel.setText(user.getValue().toString());
        profileUser = user;
    }

    public void onButtonClicked(ActionEvent ae){
        if(ae.getSource().equals(backButton)){
            Main.loadControl(primaryPane, "/SearchUsers.fxml");
        }
    }
    public void onChatButtonClicked(){
        Stage primaryStage = (Stage) primaryPane.getScene().getWindow();
        Parent root = null;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ChatWindow.fxml"));

        try {
            root = loader.load();
        } catch (IOException ie){
            ie.printStackTrace();
        }
        ChatWindowController controller = loader.getController();
        controller.setData(profileUser);
        primaryStage.setScene(new Scene(root, 800, 600));
    }

}
