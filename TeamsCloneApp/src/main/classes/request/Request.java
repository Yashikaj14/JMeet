package request;

import constants.RequestCode;

import java.io.Serializable;

public abstract class Request implements Serializable {
    protected RequestCode requestCode;

    public RequestCode getRequestCode() {
        return requestCode;
    }
}
