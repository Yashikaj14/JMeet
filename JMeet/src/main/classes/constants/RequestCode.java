package constants;

import java.io.Serializable;

public enum RequestCode implements Serializable {
    LOGIN_REQUEST,
    SIGNUP_REQUEST,
    MEETING_SCHEDULE_REQUEST,
    SET_STATUS_REQUEST,
    UPCOMING_MEETING_REQUEST,
    GET_USERS_REQUEST,
    LOGOUT_REQUEST,
    GET_NOTIFICATION_REQUEST,
    READ_NOTIFICATIONS_REQUEST,
    SEND_MESSAGE_REQUEST,
    GET_MESSAGES_REQUEST
    ;


    @Override
    public String toString() {
        return this.name();
    }
}
