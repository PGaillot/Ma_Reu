package com.gayo.maru;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gayo.maru.di.DI;
import com.gayo.maru.model.MeetModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import event.DeleteMeetEvent;
import service.MeetApiService;


public class MeetListFragment extends Fragment {

    private MeetApiService mMeetApiService;
    private RecyclerView mRecyclerView;
    private MainMeetsRVAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMeetApiService = DI.getMeetApiService();
    }

    private void sortMeetList(List<MeetModel> meets) {
        Collections.sort(meets, new Comparator<MeetModel>() {
            @Override
            public int compare(MeetModel m1, MeetModel m2) {
                return m1.getDate().compareTo(m2.getDate());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meet_list, container, false);
        mRecyclerView = view.findViewById(R.id.rv_meetListFrag);
        initList();
        return view;
    }


    private void initList() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MainMeetsRVAdapter(getContext(), new ArrayList<>(), new MainMeetRVAdapterItemClickListener() {
            @Override
            public void onClick(MeetModel model) {
                ((MainActivity)getActivity()).openDetailFragment(model);
                ((MainActivity)getActivity()).testSelectedMeet();
            }
        });
        mRecyclerView.setAdapter(adapter);
        refreshList();
    }


    private void refreshList() {
        ArrayList<MeetModel> meets = (ArrayList<MeetModel>) mMeetApiService.getMeets();
        sortMeetList(meets);
        adapter.update(meets);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe
    public void onDeleteMeet(DeleteMeetEvent event) {
        mMeetApiService.deleteMeet(event.meet);
        MainActivity mMainActivity = (MainActivity) getActivity();
        Fragment mMainInfoFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.MainInfoFrameFragment);
        if(mMainInfoFragment != null){
        mMainActivity.OpenInfoFragment();
        }
        refreshList();
        System.out.println(" this meet is delete !");
    }


}