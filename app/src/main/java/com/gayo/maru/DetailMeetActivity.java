package com.gayo.maru;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.gayo.maru.databinding.ActivityMainBinding;
import com.gayo.maru.model.MeetModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DetailMeetActivity extends AppCompatActivity implements Serializable {

    List<String> guestMails = null;
    private ActivityMainBinding binding;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
        setContentView(R.layout.activity_detail_meet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MeetModel extras = (MeetModel) getIntent().getSerializableExtra("currentMeet");
        guestMails = Arrays.asList(extras.getMails());

//        SetGuestRecyclerView();

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

        ArrayList<String> guestMails = new ArrayList<String>(Arrays.asList(extras.getMails()));
    }


}