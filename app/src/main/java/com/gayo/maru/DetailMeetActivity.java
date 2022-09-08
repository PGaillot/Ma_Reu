package com.gayo.maru;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import com.gayo.maru.model.MeetModel;

public class DetailMeetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_meet);

        OpenDetailFragment();
    }

    private void OpenDetailFragment() {

        MeetModel extras = (MeetModel) getIntent().getSerializableExtra("currentMeet");

        Fragment detailFragment = new DetailFragment();

        // Pass Arguments
        Bundle MeetBundle = new Bundle();
        MeetBundle.putSerializable("currentMeet", extras);
        detailFragment.setArguments(MeetBundle);



        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.detail_act_constraint_layout,new DetailFragment())
                .commit();

    }
}