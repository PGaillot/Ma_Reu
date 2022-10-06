package event;


import com.gayo.maru.model.MeetModel;

public class DeleteMeetEvent {

    /**
     * Meet to delete
     */
    public MeetModel meet;

    /**
     * Constructor.
     * @param meet
     */
    public DeleteMeetEvent(MeetModel meet) {
        this.meet = meet;
        System.out.println("3333333333333333333333333333333333333333333333333333333333333333333");
    }
}
