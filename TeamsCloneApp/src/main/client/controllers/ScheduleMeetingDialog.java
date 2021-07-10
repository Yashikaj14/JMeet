package controllers;

import data.Meeting;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import mainApp.Main;

import java.time.LocalDate;
import java.time.LocalTime;

public class ScheduleMeetingDialog {

    @FXML
    private AnchorPane primaryPane;
    @FXML
    private TextField titleField;
    @FXML
    private TextArea detailArea;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<LocalTime> timeBox;

    public void initialize(){
        ObservableList<LocalTime> timeList = FXCollections.observableArrayList();
        for (int i=0;i<24;i++){
            timeList.add(LocalTime.of(i, 0));
            timeList.add(LocalTime.of(i, 30));
        }
        timeBox.setEditable(false);
        timeBox.setItems(timeList);
    }

    public Meeting getData(){
        LocalDate date = datePicker.getValue();
        String title = titleField.getText();
        LocalTime time = timeBox.getSelectionModel().getSelectedItem();
        String details = detailArea.getText();

        return new Meeting(title, date, Main.user.getUserID(), time, details);
    }
}
