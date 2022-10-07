package com.gayo.maru;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import com.gayo.maru.model.MeetModel;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class DetailMeetActivity extends AppCompatActivity implements Serializable {

    List<String> guestMails = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_meet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        MeetModel extras = (MeetModel) getIntent().getSerializableExtra("currentMeet");
        guestMails = Arrays.asList(extras.getMails());

        OpenDetailFragment();
    }

    private void OpenDetailFragment() {
        Fragment detailFragment = new DetailFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        MeetModel extras = (MeetModel) getIntent().getSerializableExtra("currentMeet");

        // Pass Arguments
        Bundle MeetBundle = new Bundle();
        MeetBundle.putSerializable("currentMeet", extras);

        detailFragment.setArguments(MeetBundle);
        System.out.println(MeetBundle);

        fragmentTransaction.replace(R.id.detail_act_constraint_layout,detailFragment).commit();
    }
}