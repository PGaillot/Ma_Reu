package com.gayo.maru;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainInfoFragment extends Fragment {

    TextView mMeetCountTV;
    private int mMeetCount;

    public MainInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        update();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_info, container, false);
        mMeetCountTV = view.findViewById(R.id.MainInfoTV);


        if (mMeetCount > 1) {
            mMeetCountTV.setText("Aujourd'hui il y a " + mMeetCount + " réunions.");
        } else if (mMeetCount == 0) {
            mMeetCountTV.setText("Aujourd'hui il n'y a pas de réunion.");
        } else {
            mMeetCountTV.setText("Aujourd'hui il y a " + mMeetCount + " réunion.");
        }
        return view;
    }

    public void update(){
        Bundle data = getArguments();
        if (data != null) {
            mMeetCount = getArguments().getInt("mainActMeetCount");
        } else {
            System.out.println("Le bundle est vide ... ");
        }
    }

}