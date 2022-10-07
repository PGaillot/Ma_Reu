package com.gayo.maru;

import android.content.Intent;
import android.os.Bundle;

import com.gayo.maru.di.DI;
import com.gayo.maru.model.MeetModel;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gayo.maru.databinding.ActivityMainBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Date;

import event.DeleteMeetEvent;
import service.DatesApiService;
import service.MeetApiService;

public class MainActivity extends AppCompatActivity {

    private MeetApiService mMeetApiService;
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private RecyclerView mRecyclerView;
    private int mMeetCount;
    private MainInfoFragment mainInfoFragment;

    ArrayList<MeetModel> mMeetModelArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMeetApiService = DI.getMeetApiService();
        mMeetModelArrayList = (ArrayList<MeetModel>) mMeetApiService.getMeets();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenAddNewMeetActivity();
            }
        });

        mRecyclerView = findViewById(R.id.main_rv_meets);
        MainMeetsRVAdapter adapter = new MainMeetsRVAdapter(this, mMeetModelArrayList);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ShowAndConfigureInfoFragment();
    }

    /**
     * Init the List of Meets
     */
    private void initList() {
        mMeetModelArrayList = (ArrayList<MeetModel>) mMeetApiService.getMeets();
        mRecyclerView.setAdapter(new MainMeetsRVAdapter(this, mMeetModelArrayList));
    }


    @Override
    public void onResume() {
        super.onResume();
        initList();
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

    /**
     * Fired if the user clicks on a delete button
     *
     * @param event
     */
    @Subscribe
    public void onDeleteMeet(DeleteMeetEvent event) {
        mMeetApiService.deleteMeet(event.meet);
        initList();
        System.out.println(" this meet is delete !");
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void OpenAddNewMeetActivity() {
        Intent intent = new Intent(this, NewMeetActivity.class);
        startActivity(intent);
    }

    public void OpenInfoFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        mMeetCount = mMeetModelArrayList.size();
        Bundle meetCountBundle = new Bundle();
        meetCountBundle.putInt("mainActMeetCount", mMeetCount);
        fragment.setArguments(meetCountBundle);
        fragmentTransaction.replace(R.id.MainInfoFrameFragment, fragment).commit();
    }


    private void ShowAndConfigureInfoFragment() {
        mainInfoFragment = (MainInfoFragment) getSupportFragmentManager().findFragmentById(R.id.MainInfoFrameFragment);

        Boolean test1 = mainInfoFragment == null;
        Boolean test2 = findViewById(R.id.MainInfoFrameFragment) != null;
        if (test1 && test2) {
            mainInfoFragment = new MainInfoFragment();
            OpenInfoFragment(mainInfoFragment);
        }
    }
}