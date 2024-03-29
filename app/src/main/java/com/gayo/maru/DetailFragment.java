package com.gayo.maru;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gayo.maru.model.MeetModel;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DetailFragment extends Fragment {

    private static final String argumentKey = "meet";

    TextView  mLeader, mDate, mTopicName, mRoomName, mNbGuest;
    RecyclerView mRecyclerViewMailGuess;

    public static DetailFragment newInstance(MeetModel model) {
        DetailFragment detailFragment = new DetailFragment();
        // Pass Arguments
        Bundle MeetBundle = new Bundle();
        MeetBundle.putSerializable(argumentKey, model);
        detailFragment.setArguments(MeetBundle);
        return detailFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        mRoomName = view.findViewById(R.id.detail_tv_room);
        mLeader = view.findViewById(R.id.detail_tv_leader);
        mDate = view.findViewById(R.id.detail_tv_date);
        mTopicName = view.findViewById(R.id.detail_tv_topic);
        mRecyclerViewMailGuess = view.findViewById(R.id.detail_rv_guess);
        mNbGuest = view.findViewById(R.id.detail_tv_nbGuess);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        ((MainActivity) getActivity()).setBackButton(true);
        Bundle data = getArguments();
        if (data != null) {
            MeetModel currentMeetModel = (MeetModel) getArguments().getSerializable(argumentKey);
            // get variables from the model
            String roomName = currentMeetModel.getRoom();
            String topicMeet = currentMeetModel.getTopic();
            String leaderName = currentMeetModel.getMeetLeader();
            String[] guestsMails = currentMeetModel.getMails();
            int durationMeet = currentMeetModel.getDuration();
            Date dateMeet = currentMeetModel.getDate();
            // set variable to fragment
            mRoomName.setText("salle " + roomName);
            mLeader.setText(leaderName);
            mTopicName.setText(topicMeet);
            mDate.setText(ConfigureDateFormat(dateMeet) + " (" + durationMeet + "h)");
            mNbGuest.setText(guestsMails.length + " participants :");
            mRecyclerViewMailGuess = (RecyclerView) view.findViewById(R.id.detail_rv_guess);
            DetailMeetGuessMailRVAdapter adapter = new DetailMeetGuessMailRVAdapter(Arrays.asList(guestsMails), getContext());
            mRecyclerViewMailGuess.setAdapter(adapter);
            mRecyclerViewMailGuess.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            System.err.println("Le bundle est vide");
        }
    }

    private String ConfigureDateFormat(Date dateMeet) {
        // define a local date format : FRENCH;
        DateFormat format_fr = DateFormat.getDateInstance(DateFormat.FULL, Locale.FRENCH);
        DateFormat format_hours = new SimpleDateFormat("HH'h'mm");
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_WEEK, 1);
        Date tomorrow = calendar.getTime();
        String meetHour = format_hours.format(dateMeet);
        if (format_fr.format(now).equals(format_fr.format(dateMeet))) {
            return ("Aujourd'hui à " + meetHour);
        } else if (format_fr.format(tomorrow).equals(format_fr.format(dateMeet))) {
            return ("Demain à" + meetHour);
        } else {
            return (format_fr.format(dateMeet) + " à " + meetHour);
        }
    }

}