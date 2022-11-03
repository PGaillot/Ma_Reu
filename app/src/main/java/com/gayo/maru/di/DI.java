package com.gayo.maru.di;

import service.DatesApiService;
import service.DummyDatesApiService;
import service.DummyMeetApiService;
import service.MeetApiService;

public class DI {

    private static MeetApiService sMeetApiService = (MeetApiService) new DummyMeetApiService();
    private static DatesApiService sDatesApiService = (DatesApiService) new DummyDatesApiService();

    /**
     * Get an instance on @{@link MeetApiService}
     *
     * @return
     */
    public static MeetApiService getMeetApiService() {
        return sMeetApiService;
    }
    public static DatesApiService getDatesApiService() {
        return sDatesApiService;
    }
}
