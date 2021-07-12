package request;

import constants.RequestCode;

import java.io.Serializable;

public class GetUsersRequest extends Request implements Serializable {
    public GetUsersRequest() {
        this.requestCode = RequestCode.GET_USERS_REQUEST;
    }
}
