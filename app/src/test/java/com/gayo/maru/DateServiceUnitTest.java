package com.gayo.maru;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import com.gayo.maru.di.DI;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import service.DatesApiService;

@RunWith(JUnit4.class)
public class DateServiceUnitTest {

    private DatesApiService service;
    Date today = new Date();
    Calendar calendar = Calendar.getInstance();
    DateFormat format_fr_SHORT = DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRENCH);

    @Before
    public void setup() {
        service = DI.getDatesApiService();
    }

    @Test
    public void testTodayWithSuccess() {
        assertTrue(service.TodayTest(today));
    }

//   TEST GenerateDateString

    @Test
    public void testGenerateDateStringReturnNotToday() {
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_WEEK, 1);
        Date tomorrow = calendar.getTime();
        assertNotEquals("Aujourd'hui", service.GenerateDateString(tomorrow));
    }

    @Test
    public void testGenerateDateStringReturnTodayWithSuccess() {
        assertEquals("Aujourd'hui", service.GenerateDateString(today));
    }

    @Test
    public void testGenerateDateStringReturnTomorrowWithSuccess() {
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_WEEK, 1);
        Date tomorrow = calendar.getTime();
        assertEquals("Demain", service.GenerateDateString(tomorrow));
    }

    @Test
    public void testGenerateDateStringReturnNotTomorrow() {
        assertNotEquals("Demain", service.GenerateDateString(today));
    }

    @Test
    public void testGenerateDateStringReturnDateFormatWithSuccess() {
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_WEEK, 7);
        Date nextWeek = calendar.getTime();
        assertEquals(service.GenerateDateString(nextWeek), format_fr_SHORT.format(nextWeek));
    }

    @Test
    public void testGenerateDateStringReturnNotDateFormat() {
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_WEEK, 7);
        Date nextWeek = calendar.getTime();
        assertNotEquals(service.GenerateDateString(today), format_fr_SHORT.format(nextWeek));
    }

    // TEST GenerateHourString

    @Test
    public void testGenerateHourStringReturnHourFormatHalfHourWithSuccess() {
        calendar.set(Calendar.HOUR, 9);
        calendar.set(Calendar.MINUTE, 27);
        Date testDate = calendar.getTime();
        assertEquals("9h30", service.GenerateHourString(testDate));
    }

    @Test
    public void testGenerateHourStringReturnHourNotFormatHalfHour() {
        calendar.set(Calendar.HOUR, 9);
        calendar.set(Calendar.MINUTE, 27);
        Date testDate = calendar.getTime();
        assertNotEquals("9h27", service.GenerateHourString(testDate));
    }

    @Test
    public void testGenerateHourStringReturnHourFormatWithSuccess() {
        calendar.set(Calendar.HOUR, 9);
        calendar.set(Calendar.MINUTE, 52);
        Date testDate = calendar.getTime();
        assertEquals("10h", service.GenerateHourString(testDate));
    }


    @Test
    public void testGenerateHourStringReturnNotHour() {
        calendar.set(Calendar.HOUR, 9);
        calendar.set(Calendar.MINUTE, 52);
        Date testDate = calendar.getTime();
        assertNotEquals("9h", service.GenerateHourString(testDate));
    }

}
