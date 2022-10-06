package service;

import com.gayo.maru.model.MeetModel;

import java.util.List;

public interface MeetApiService {

    List<MeetModel> getMeets();

    List<String> getRooms();

    void deleteMeet(MeetModel meet);

    void createMeet(MeetModel meet);

}
