package com.gayo.maru;


import static org.junit.Assert.assertTrue;

import com.gayo.maru.di.DI;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Date;

import service.DatesApiService;

@RunWith(JUnit4.class)
public class DateServiceUnitTest {

    private DatesApiService service;

    @Before
    public void setup(){service = DI.getDatesApiService();}

    @Test
    public void testTodayWithSuccess(){
        Date today = new Date();
        assertTrue(service.TodayTest(today));
    }
}
