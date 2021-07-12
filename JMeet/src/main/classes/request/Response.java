package request;

import constants.ResponseCode;

import java.io.Serializable;

public class Response implements Serializable {
    private String responseID;
    private ResponseCode responseCode;
    private Object responseObject;

    public Response(String responseID, ResponseCode responseCode, Object responseObject) {
        this.responseID = responseID;
        this.responseCode = responseCode;
        this.responseObject = responseObject;
    }

    public String getResponseID() {
        return responseID;
    }

    public void setResponseID(String responseID) {
        this.responseID = responseID;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    public Object getResponseObject() {
        return responseObject;
    }

    public void setResponseObject(Object responseObject) {
        this.responseObject = responseObject;
    }
}
