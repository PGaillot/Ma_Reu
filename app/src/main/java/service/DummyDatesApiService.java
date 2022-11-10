package service;

import com.gayo.maru.model.MeetModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DummyDatesApiService implements DatesApiService {

    Date now = new Date();
    DateFormat mDateFormat_DDMMYYYY = new SimpleDateFormat("ddMMyyyy");
    // define a local date format : FRENCH;
    DateFormat format_fr_LONG = DateFormat.getDateInstance(DateFormat.FULL, Locale.FRENCH);
    DateFormat format_fr_SHORT = DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRENCH);
    Calendar calendar = Calendar.getInstance();


    @Override
    public Boolean TodayTest(Date date) {
        if (mDateFormat_DDMMYYYY.format(date).equals(mDateFormat_DDMMYYYY.format(now))) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public String GenerateDateString(Date date) {
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_WEEK, 1);
        Date tomorrow = calendar.getTime();
        if (TodayTest(date)) {
            return ("Aujourd'hui");
        } else if (mDateFormat_DDMMYYYY.format(tomorrow).equals(mDateFormat_DDMMYYYY.format(date))) {
            return ("Demain");
        } else {
            return (format_fr_SHORT.format(date));
        }
    }

    public String GenerateHourString(Date date){
        DateFormat mDateFormatterHH = new SimpleDateFormat("HH");
        DateFormat mDateFormatterMM = new SimpleDateFormat("mm");
        int mHH = Integer.parseInt(mDateFormatterHH.format(date));
        int mMM = Integer.parseInt(mDateFormatterMM.format(date));
        if (mMM >= 1 && mMM <= 30){
            return mHH + "h30";
        } else {
        return ( mHH + 1) +"h";
        }
    }

}
