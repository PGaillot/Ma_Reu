package service;

import com.gayo.maru.model.MeetModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DummyMeetApiService implements MeetApiService {

    private final List<MeetModel> meets = DummyMeetGenerator.GenerateMeet();
    private final List<String> rooms = DummyMeetGenerator.GenerateRooms();
    private final Date now = new Date();

    @Override
    public List<MeetModel> getMeets() {
        return meets;
    }

    @Override
    public List<MeetModel> getMeetFromDate(Date date) {
        List<MeetModel> todayMeets = new ArrayList<>();
        DateFormat day = new SimpleDateFormat("dd");
        for (MeetModel meet :
                getMeets()) {
            if (day.format(date).equals(day.format(meet.getDate()))){
                System.out.println(day.format(date) + "=" + day.format(meet.getDate()));
                todayMeets.add(meet);
            } else {
                System.out.println(day.format(date) + "!=" +  day.format(meet.getDate()));
            }
        }
        return todayMeets;
    }

    @Override
    public List<MeetModel> getTodayMeets() {
        return getMeetFromDate(now);
    }

    @Override
    public List<String> getRooms() {
        return rooms;
    }

    @Override
    public void addMeet(MeetModel meet) {
        meets.add(meet);
    }

    @Override
    public void deleteMeet(MeetModel meet) {
        meets.remove(meet);
    }

    @Override
    public void createMeet(MeetModel meet) {
    }


}
