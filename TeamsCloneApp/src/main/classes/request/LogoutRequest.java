package request;

import constants.RequestCode;
import data.User;

import java.io.Serializable;

public class LogoutRequest extends Request implements Serializable {
    private User user;

    public LogoutRequest(User user) {
        this.user = user;
        this.requestCode = RequestCode.LOGOUT_REQUEST;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
