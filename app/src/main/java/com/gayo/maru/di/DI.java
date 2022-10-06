package com.gayo.maru.di;

import service.DummyMeetApiService;
import service.MeetApiService;

public class DI {

    private static MeetApiService service = (MeetApiService) new DummyMeetApiService();
    /**
     * Get an instance on @{@link MeetApiService}
     * @return
     */
    public static MeetApiService getMeetApiService(){
        return service;
    }
}
