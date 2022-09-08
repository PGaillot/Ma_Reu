package com.gayo.maru;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gayo.maru.model.MeetModel;


public class DetailFragment extends Fragment {

    TextView mRoomName;
    TextView mTopicName;
    TextView mDate;
    TextView mDuration;
    TextView mLeader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // initialize view
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        // assign Meet data Variables
        mRoomName = view.findViewById(R.id.detail_tv_room);
        mLeader = view.findViewById(R.id.detail_tv_leader);



        if (getArguments() != null){

            MeetModel currentMeetModel = (MeetModel) getArguments().getSerializable("currentMeet");

            // get variables from the model
            String roomName = currentMeetModel.getRoom();
            String leaderName = currentMeetModel.getMeetLeader();

            // set variable to fragment
            mRoomName.setText(roomName);
            mLeader.setText(leaderName);
        }

        return view;
    }
}