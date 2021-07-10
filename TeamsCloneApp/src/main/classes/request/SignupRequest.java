package request;

import constants.RequestCode;
import data.User;

import java.io.Serializable;

public class SignupRequest extends Request implements Serializable {
    private User user;

    public SignupRequest(User user) {
        this.user = user;
        this.requestCode = RequestCode.SIGNUP_REQUEST;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
