package request;

import constants.RequestCode;
import data.Meeting;

import java.io.Serializable;

public class MeetingScheduleRequest extends Request implements Serializable {
    private Meeting meeting;

    public MeetingScheduleRequest(Meeting meeting) {
        this.meeting = meeting;
        this.requestCode = RequestCode.MEETING_SCHEDULE_REQUEST;
    }

    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }
}
