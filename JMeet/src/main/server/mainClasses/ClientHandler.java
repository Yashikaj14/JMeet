package mainClasses;

import authenticationHandler.LoginHandler;
import authenticationHandler.SignupHandler;
import constants.RequestCode;
import constants.ResponseCode;
import constants.Status;
import data.Meeting;
import data.Message;
import data.Notification;
import data.User;
import generalHandlers.*;
import javafx.util.Pair;
import request.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.List;

public class ClientHandler implements Runnable {
    private Socket socket;
    private ObjectOutputStream oosTracker;
    private ObjectInputStream oisTracker;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            oisTracker = new ObjectInputStream(this.socket.getInputStream());
            oosTracker = new ObjectOutputStream(this.socket.getOutputStream());
        }
        catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    @Override
    public void run() {
        boolean quit = false;
        while (!quit) {
            Request request = null;
            Object ob;
            try {
//                System.out.println("Here");
                ob = oisTracker.readObject();
//                System.out.println("Object Read" + ob.toString());
                request = (Request) ob;
//                System.out.println("Object Read" + request.getRequestCode());

            } catch (IOException | ClassNotFoundException ie) {
                quit = true;
                System.out.println("End of inputs: " + ie.getMessage());

            }
            try {

                if (request.getRequestCode().equals(RequestCode.LOGIN_REQUEST)){
                    System.out.println("Login Request received");
                    LoginRequest loginRequest = (LoginRequest) request;
                    User user = LoginHandler.verifyLogin(loginRequest);
                    Response response;
                    if (user != null){
                        response = new Response("LOGIN_RESPONSE", ResponseCode.SUCCESS, user);

                    }else {
                        response = new Response("LOGIN_RESPONSE", ResponseCode.FAILURE, null);
                    }
                    try {
                        oosTracker.writeObject(response);
                    } catch (IOException ie){
                        ie.printStackTrace();
                    }
                }else if (request.getRequestCode().equals(RequestCode.SIGNUP_REQUEST)){
                    SignupRequest signupRequest = (SignupRequest) request;
                    boolean res = SignupHandler.verifySignup(signupRequest);
                    Response response;
                    if (res){
                        response = new Response("SIGNUP_RESPONSE", ResponseCode.SUCCESS, null);
                    }else {
                        response = new Response("SIGNUP_RESPONSE", ResponseCode.FAILURE, null);
                    }
                    try {
                        oosTracker.writeObject(response);
                    } catch (IOException ie){
                        ie.printStackTrace();
                    }
                } else if (request.getRequestCode().equals(RequestCode.MEETING_SCHEDULE_REQUEST)){
                    MeetingScheduleRequest scheduleRequest = (MeetingScheduleRequest) request;
                    String link = MeetingScheduleHandler.scheduleMeeting(scheduleRequest);
                    Response response;
                    if (!link.equals("#")){
                        response = new Response("SIGNUP_RESPONSE", ResponseCode.SUCCESS, link);
                    }else {
                        response = new Response("SIGNUP_RESPONSE", ResponseCode.FAILURE, null);
                    }
                    try {
                        oosTracker.writeObject(response);
                    } catch (IOException ie){
                        ie.printStackTrace();
                    }
                } else if(request.getRequestCode().equals(RequestCode.UPCOMING_MEETING_REQUEST)){
                    UpcomingMeetingRequest meetingRequest = (UpcomingMeetingRequest) request;
                    List<Meeting> meetings = MeetingScheduleHandler.getUpcomingMeetings(meetingRequest);
                    Response response = new Response("GET_MEETING_RESPONSE", ResponseCode.SUCCESS, meetings);
                    try {
                        oosTracker.writeObject(response);
                    } catch (IOException ie){
                        ie.printStackTrace();
                    }
                } else if(request.getRequestCode().equals(RequestCode.SET_STATUS_REQUEST)){
                    SetStatusRequest statusRequest = (SetStatusRequest) request;
                    boolean res = StatusHandler.setStatus(statusRequest);
                    Response response;
                    if(res)
                        response = new Response("GET_STATUS_RESPONSE", ResponseCode.SUCCESS, null);
                    else
                        response = new Response("GET_STATUS_RESPONSE", ResponseCode.FAILURE, null);
                    try {
                        oosTracker.writeObject(response);
                    } catch (IOException ie){
                        ie.printStackTrace();
                    }
                } else if(request.getRequestCode().equals(RequestCode.LOGOUT_REQUEST)){
                    LogoutRequest logoutRequest = (LogoutRequest) request;
                    LoginHandler.handleLogout(logoutRequest);
                } else if(request.getRequestCode().equals(RequestCode.GET_USERS_REQUEST)){
                    System.out.println("get users request received");
                    List<Pair<User, Status>> users = DataHandler.getUsers();
                    Response response = new Response("GET_USERS_RESPONSE", ResponseCode.SUCCESS, users);
                    try {
                        oosTracker.writeObject(response);
                    } catch (IOException ie){
                        ie.printStackTrace();
                    }
                } else if(request.getRequestCode().equals(RequestCode.GET_NOTIFICATION_REQUEST)){
                    GetNotificationRequest notificationRequest = (GetNotificationRequest) request;
                    List<Notification> notifications = NotificationHandler.getNotifications(notificationRequest);

                    Response response = new Response("GET_USERS_RESPONSE", ResponseCode.SUCCESS, notifications);
                    try {
                        oosTracker.writeObject(response);
                    } catch (IOException ie){
                        ie.printStackTrace();
                    }
                } else if(request.getRequestCode().equals(RequestCode.READ_NOTIFICATIONS_REQUEST)){
                    ReadAllNotificationRequest notificationRequest = (ReadAllNotificationRequest) request;
                    NotificationHandler.readNotifications(notificationRequest.getUserID());
                    Response response = new Response("READ_NOTIFICATIONS", ResponseCode.SUCCESS, null);
                    try {
                        oosTracker.writeObject(response);
                    } catch (IOException ie){
                        ie.printStackTrace();
                    }
                } else if (request.getRequestCode().equals(RequestCode.GET_MESSAGES_REQUEST)){
                    GetMessagesRequest messagesRequest = (GetMessagesRequest) request;
                    List<Message> messages = MessageHandler.getMessages(messagesRequest.getUserID(), messagesRequest.getOtherUserID());
                    Response response = new Response("GET_MESSAGES", ResponseCode.SUCCESS, messages);
                    try {
                        oosTracker.writeObject(response);
                    } catch (IOException ie){
                        ie.printStackTrace();
                    }
                } else if(request.getRequestCode().equals(RequestCode.SEND_MESSAGE_REQUEST)){
                    SendMessageRequest messageRequest = (SendMessageRequest) request;
                    MessageHandler.addMessage(messageRequest.getMessage());
                    Response response = new Response("ADD_MESSAGE", ResponseCode.SUCCESS, null);
                    try {
                        oosTracker.writeObject(response);
                    } catch (IOException ie){
                        ie.printStackTrace();
                    }
                }



            } catch (NullPointerException ne){
                ne.printStackTrace();
                System.out.println("Null aaya h: "+ ne.getMessage());
            }
        }
    }
}
