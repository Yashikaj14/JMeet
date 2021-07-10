package mainApp;

import data.User;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import request.LogoutRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class Main extends Application {
    public static final int portno = 8800;
    public static ObjectInputStream oisTracker = null;
    public static ObjectOutputStream oosTracker = null;
    public static Socket socket=null;
    public static final String ipaddress = "localhost";
    public static User user;

//    E:\JavaPCT\Project-Rakshak\Externals\javafx-sdk-11.0.2\lib
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/mainpage.fxml"));
        primaryStage.setTitle("Teams");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/teamslogo.png")));
        primaryStage.setScene(new Scene(root, 800, 600));

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                System.out.println("Yha tk bhi nahi pahucha kya?");
                System.out.println("Yha tk pahucha kya?");
                if(Main.user!=null && Main.socket!=null){
                    LogoutRequest request = new LogoutRequest(Main.user);
                    try {
                        oosTracker.writeObject(request);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Log Out Successfully");
                        alert.showAndWait();
                        Main.user=null;
                    } catch (IOException ie){
                        ie.printStackTrace();
                    }
                    Main.user = null;
                }
                if(socket != null){
                    try {
                        Main.socket.close();
                    } catch (IOException se){
                        se.printStackTrace();
                    }
                }
                System.exit(0);
            }
        });

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);

        if(socket!=null) {
            try {
                socket.close();
            } catch (IOException ie){
                System.out.println("Error while closing socket: " + ie.getMessage());
            }

        }
    }

    public static void loadControl(Pane primaryPane, String filename){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage primaryStage = (Stage) primaryPane.getScene().getWindow();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource(filename));

                } catch (IOException ie){
                    ie.printStackTrace();
                }
                primaryStage.setScene(new Scene(root, 800, 600));
            }
        });
    }
}

