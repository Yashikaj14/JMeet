package controllers;

import data.Notification;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.util.Callback;

import java.util.Comparator;

public class NotificationViewController {
    @FXML
    private AnchorPane primaryPane;
    @FXML
    private ListView<Notification> notificationListView;

    private ObservableList<Notification> notifications = FXCollections.observableArrayList();
    private SortedList<Notification> sortedNotifications;

    public void initialize(){
        sortedNotifications = new SortedList<>(notifications, new Comparator<Notification>() {
            @Override
            public int compare(Notification o1, Notification o2) {
                if(o1.getDate().equals(o2.getDate()))
                    return -o1.getTime().compareTo(o2.getTime());
                return -o1.getDate().compareTo(o2.getDate());
            }
        });
        notificationListView.setItems(sortedNotifications);

        notificationListView.setCellFactory(new Callback<ListView<Notification>, ListCell<Notification>>() {
            @Override
            public ListCell<Notification> call(ListView<Notification> notificationListView) {
                ListCell<Notification> listCell = new ListCell<>(){
                    @Override
                    protected void updateItem(Notification notification, boolean b) {
                        super.updateItem(notification, b);
                        if(b){
                            setText(null);
                        }
                        else{
                            setText(notification.toString());
                            if(!notification.isRead()){
                                setFont(new Font("Times New Roman Bold", 16));
                            } else {
                                setFont(new Font("Times New Roman", 16));
                            }
                        }
                    }
                };
                return listCell;
            }
        });
    }

    public void setData(ObservableList<Notification> list){
        notifications.setAll(list);
    }
}
