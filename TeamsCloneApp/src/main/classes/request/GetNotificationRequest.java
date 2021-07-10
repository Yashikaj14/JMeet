package request;

import constants.RequestCode;

import java.io.Serializable;

public class GetNotificationRequest extends Request implements Serializable {
    private String userID;

    public GetNotificationRequest(String userID) {
        this.userID = userID;
        this.requestCode = RequestCode.GET_NOTIFICATION_REQUEST;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
