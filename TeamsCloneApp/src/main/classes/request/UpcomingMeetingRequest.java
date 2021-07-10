package request;

import constants.RequestCode;

import java.io.Serializable;

public class UpcomingMeetingRequest extends Request implements Serializable {
    private String userID;

    public UpcomingMeetingRequest(String userID) {
        this.userID = userID;
        this.requestCode = RequestCode.UPCOMING_MEETING_REQUEST;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
