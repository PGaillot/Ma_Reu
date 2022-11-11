package com.gayo.maru;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.SeekBar;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.gayo.maru.di.DI;
import com.gayo.maru.utils.RecyclerViewItemCountAssertion;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import service.DatesApiService;
import service.MeetApiService;

@LargeTest
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AddMeetEspTest {


    String topic = "sujet";
    String leaderName = "nom";
    String mail = "mail@test.com";
    final int nbGuest = 3;
    final int duration = 4;

    Date today = new Date();
    MeetApiService meetApiService = DI.getMeetApiService();
    DatesApiService datesApiService = DI.getDatesApiService();
    String day = datesApiService.GenerateDateString(today);



    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void A_addMeetEspTest() {


        ViewInteraction floatingActionButton = onView(withId(R.id.fab));
        ViewInteraction topicEditText = onView(withId(R.id.addNew_et_topic));
        ViewInteraction roomsSpinner = onView(withId(R.id.AddActSpinnerRoom));
        ViewInteraction leaderEditText = onView(withId(R.id.AddActEditTextTextLeader));
        ViewInteraction mailEditText = onView(withId(R.id.AddActEditTextMail));
        ViewInteraction addMailButton = onView(withId(R.id.AddMailBtn));
        ViewInteraction validateButton = onView( withId(R.id.validateButton));
        ViewInteraction cv_why = onView(withId(R.id.cardViewWhy));
        ViewInteraction cv_when = onView(withId(R.id.cardViewWhen));
        ViewInteraction cv_where = onView(withId(R.id.cardViewWhere));
        ViewInteraction cv_who = onView(withId(R.id.cardViewWho));
        ViewInteraction scrollView = onView(withId(R.id.addMeetAct_ScrollView));
        ViewInteraction meetsRecyclerView = onView(withId(R.id.rv_meetListFrag));



        // 1 - Click on AddFab
        floatingActionButton.perform(click());

        cv_why.perform(scrollTo());
        // 2 - Add a topicName
        topicEditText.perform(scrollTo());
        topicEditText.perform(replaceText(topic), closeSoftKeyboard());


        cv_when.perform(scrollTo());
        // 3 - Click on edit date
        ViewInteraction editDate = onView(withId(R.id.tvEditDate));
        editDate.perform(click());
        ViewInteraction dateMaterialButton = onView(withText("OK"));
        dateMaterialButton.perform(click());

        // 4 - Click on edit Hour
        ViewInteraction editTime = onView(withId(R.id.tvEditHour));
        editTime.perform(click());
        ViewInteraction materialButton = onView(withText("OK"));
        materialButton.perform(click());


        // 5 - Duration seekbar
        ViewInteraction seekbar = onView(withId(R.id.seekBar));
        seekbar.perform(setProgress(duration-1));


        scrollView.perform(swipeUp());
        cv_where.perform(scrollTo());
        // 6 - Click & select Room
        roomsSpinner.perform(scrollTo(), click());
        DataInteraction appCompatCheckedTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(6);
        appCompatCheckedTextView.perform(click());

        cv_who.perform(scrollTo());
        // 7 - Add Leader name
        leaderEditText.perform(replaceText(leaderName), closeSoftKeyboard());

        // 8 - Add 3 mails
        for (int i = 0; i < nbGuest; i++){
            mailEditText.perform( replaceText(mail), closeSoftKeyboard());
            addMailButton.perform(click());
        }

        scrollView.perform(swipeUp());
        // 9 - Valid form
        int meetListSize = meetApiService.getMeets().size();
        validateButton.perform(scrollTo(), click());

        // 10 - Check if the meet was created
        ViewInteraction meetRow = onView(
                allOf(withId(R.id.textView_leader), withText(day +" à " + "9h" + " - " + leaderName),
                        withParent(withParent(withId(R.id.rv_meetListFrag))),
                        isDisplayed()));
        meetRow.check(matches(withText(day +" à " + "9h" + " - " + leaderName)));

        meetsRecyclerView.check(new RecyclerViewItemCountAssertion(meetListSize +1));


    }

    @Test
    public void B_checkDetailsIsDisplay(){
        ViewInteraction meetRow = onView(
                allOf(withId(R.id.textView_leader), withText(day +" à " + "9h" + " - " + leaderName),
                        withParent(withParent(withId(R.id.rv_meetListFrag))),
                        isDisplayed()));

        meetRow.perform(click());
        ViewInteraction detailFrame = onView(withId(R.id.detail_frame_fragment));
        detailFrame.check(matches(isDisplayed()));
    }

    @Test
    public void C_checkDataDetails(){
        ViewInteraction meetRow = onView(
                allOf(withId(R.id.textView_leader), withText(day +" à " + "9h" + " - " + leaderName),
                        withParent(withParent(withId(R.id.rv_meetListFrag))),
                        isDisplayed()));
        meetRow.perform(click());
        ViewInteraction detailFrame = onView(withId(R.id.detail_frame_fragment));
        detailFrame.check(matches(isDisplayed()));


        ViewInteraction detailLeaderName = onView(withId(R.id.detail_tv_leader));
        detailLeaderName.check(matches(isDisplayed()));
        detailLeaderName.check(matches(withText(leaderName)));

        ViewInteraction detailTopic = onView(withId(R.id.detail_tv_topic));
        detailTopic.check(matches(isDisplayed()));
        detailTopic.check(matches(withText(topic)));

        ViewInteraction detailRoom = onView(withId(R.id.detail_tv_room));
        detailRoom.check(matches(isDisplayed()));
        detailRoom.check(matches(withText("salle noir")));

        ViewInteraction detailDateAndDuration = onView(withId(R.id.detail_tv_date));
        detailDateAndDuration.check(matches(isDisplayed()));
        detailDateAndDuration.check(matches(withText(day +" à " + "09h00" + " (" + duration + "h)")));

        ViewInteraction detailMailCount = onView(withId(R.id.detail_tv_nbGuess));
        detailMailCount.check(matches(isDisplayed()));
        detailMailCount.check(matches(withText( nbGuest + " participants :")));
    }

    @Test
    public void D_deleteMeet(){
        int meetListSize = meetApiService.getMeets().size();

        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.meet_row_iv_delete),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.rv_meetListFrag),
                                        0),
                                3),
                        isDisplayed()));
        appCompatImageView.perform(click());

        ViewInteraction materialButton = onView(withText("Oui"));
        materialButton.perform(scrollTo(), click());
        ViewInteraction meetsRecyclerView = onView(withId(R.id.rv_meetListFrag));
        meetsRecyclerView.check(new RecyclerViewItemCountAssertion(meetListSize -1));
    }


    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }


    public static ViewAction setProgress(final int progress) {
        return new ViewAction() {
            @Override
            public void perform(UiController uiController, View view) {
                SeekBar seekBar = (SeekBar) view;
                seekBar.setProgress(progress);
            }
            @Override
            public String getDescription() {
                return "Set a progress on a SeekBar";
            }
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(SeekBar.class);
            }
        };
    }
}

