package service;

import com.gayo.maru.model.MeetModel;

import java.util.Date;
import java.util.List;

public interface MeetApiService {

    List<MeetModel> getMeets();

    List<MeetModel> getMeetFromDate(Date date);

    List<MeetModel> getTodayMeets();

    List<String> getRooms();

    void addMeet(MeetModel meet);

    void deleteMeet(MeetModel meet);

    void createMeet(MeetModel meet);


}
