package service;

import java.util.Date;

public interface DatesApiService {

    Boolean TodayTest(Date date);

    void PrintToday();

    String GenerateDateString(Date date);

    String GenerateHourString(Date date);
}
