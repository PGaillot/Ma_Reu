package com.gayo.maru;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import com.gayo.maru.di.DI;
import com.gayo.maru.model.MeetModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import service.DummyMeetGenerator;
import service.MeetApiService;

@RunWith(JUnit4.class)
public class MeetServiceUnitTest {

    private MeetApiService service;

    @Before
    public void setup(){service = DI.getMeetApiService();}

    @Test
    public void getMeetsWithSuccess(){
        List<MeetModel> meets = service.getMeets();
        List<MeetModel> expectedMeet = DummyMeetGenerator.DUMMY_MEETS;
        assertEquals(meets, expectedMeet);
    }

    @Test
    public void deleteMeetWithSuccess(){
        MeetModel meets = service.getMeets().get(0);
        service.deleteMeet(meets);
        assertFalse(service.getMeets().contains(meets));
    }

    @Test
    public void addMeetWithSuccess(){
        Date date = new Date();
        String[] mailList = {"mail@mail.fr", "mail@mail.fr"};
        MeetModel meet = new MeetModel("Leader", "Room", mailList, date, 4, "Topic");
        service.addMeet(meet);
        assertTrue(service.getMeets().contains(meet));
    }

    @Test
    public void getTodayMeetWitsSuccess(){
        DateFormat dateFormat = new SimpleDateFormat("dd-M-yy");
        Date today = new Date();
        List<MeetModel> todayMeets = service.getTodayMeets();
        assertEquals(dateFormat.format(todayMeets.get(0).getDate()), dateFormat.format(today));
    }

    @Test
    public void getMeetFromDateWithSuccess(){
        DateFormat dateFormat = new SimpleDateFormat("dd-M-yy");
        Date date = new Date();
        List<MeetModel> meets = service.getMeetFromDate(date);
        assertEquals(dateFormat.format(meets.get(0).getDate()), dateFormat.format(date));
    }

}
