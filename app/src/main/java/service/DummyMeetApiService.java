package service;

import com.gayo.maru.model.MeetModel;

import java.util.List;

public class DummyMeetApiService implements MeetApiService {

    private final List<MeetModel> meets = DummyMeetGenerator.GenerateMeet();
    private final List<String> rooms = DummyMeetGenerator.GenerateRooms();

    @Override
    public List<MeetModel> getMeets(){return meets;}

    @Override
    public List<String> getRooms(){return rooms;}

    @Override
    public void deleteMeet(MeetModel meet) { meets.remove(meet);}

    @Override
    public void createMeet(MeetModel meet) {}


}
