package request;

import constants.RequestCode;

import java.io.Serializable;

public class ReadAllNotificationRequest extends Request implements Serializable {
    private String userID;

    public ReadAllNotificationRequest(String userID) {
        this.userID = userID;
        this.requestCode = RequestCode.READ_NOTIFICATIONS_REQUEST;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
