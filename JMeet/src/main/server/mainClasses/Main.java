package mainClasses;

import tools.DBConnection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.time.format.DateTimeFormatter;

public class Main {
    public static ServerSocket serverSocket;
    public static Connection connection;
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");


    public static void main(String[] args) {

        DBConnection dbConnection = new DBConnection();
        connection = dbConnection.getConnection();
        System.out.println("Database connected: " + connection.toString());


        try{
            serverSocket = new ServerSocket(8800);

        } catch (IOException ie){
            ie.printStackTrace();
        }
        while (true) {
            try {

                System.out.println("Waiting for client...");
                Socket socket = serverSocket.accept();
                System.out.println("Client Connected");
                Thread t = new Thread(new ClientHandler(socket));
                t.start();

            } catch(IOException ie){
                ie.printStackTrace();
            }
        }

    }
}
