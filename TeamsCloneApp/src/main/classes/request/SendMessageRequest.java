package request;

import constants.RequestCode;
import data.Message;

import java.io.Serializable;

public class SendMessageRequest extends Request implements Serializable {
    private Message message;

    public SendMessageRequest(Message message) {
        this.message = message;
        this.requestCode = RequestCode.SEND_MESSAGE_REQUEST;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
