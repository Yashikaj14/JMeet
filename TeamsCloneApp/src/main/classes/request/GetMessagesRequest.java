package request;
import constants.RequestCode;

import java.io.Serializable;


public class GetMessagesRequest extends Request implements Serializable {
    private String userID;
    private String otherUserID;

    public GetMessagesRequest(String userID, String otherUserID) {
        this.userID = userID;
        this.otherUserID = otherUserID;
        this.requestCode = RequestCode.GET_MESSAGES_REQUEST;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getOtherUserID() {
        return otherUserID;
    }

    public void setOtherUserID(String otherUserID) {
        this.otherUserID = otherUserID;
    }
}
