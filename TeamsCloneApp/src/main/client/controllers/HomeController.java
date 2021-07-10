package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import mainApp.Main;

public class HomeController {
    @FXML
    private AnchorPane pane;
    @FXML
    private Button loginButton, SignupButton;

    public void onLoginPressed(){
        Main.loadControl(pane, "/Login.fxml");
    }

    public void onSignupPressed(){
        Main.loadControl(pane, "/Signup.fxml");
    }

}
