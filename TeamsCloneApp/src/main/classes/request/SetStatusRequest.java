package request;

import constants.RequestCode;
import constants.Status;

import java.io.Serializable;

public class SetStatusRequest extends Request implements Serializable {
    private Status status;
    private String userID;

    public SetStatusRequest(Status status, String userID) {
        this.status = status;
        this.userID = userID;
        this.requestCode = RequestCode.SET_STATUS_REQUEST;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
