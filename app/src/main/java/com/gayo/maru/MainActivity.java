package com.gayo.maru;
import android.content.Intent;
import android.os.Bundle;
import com.gayo.maru.di.DI;
import com.gayo.maru.model.MeetModel;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import service.MeetApiService;

public class MainActivity extends AppCompatActivity {

    private MeetApiService mMeetApiService;
    private int mMeetCount;
    private MeetListFragment mMeetListFragment;
    private Fragment mMainInfoFragment;
    private FloatingActionButton mAddFab;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMeetApiService = DI.getMeetApiService();
        mAddFab = findViewById(R.id.fab);
        mToolbar = findViewById(R.id.toolbar);
        configureToolbar();

        mAddFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenAddNewMeetActivity();
            }
        });
        showAndConfigureInfoFragment();
        openMeetListFragment();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void configureToolbar() {
        setSupportActionBar(mToolbar);
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                getSupportActionBar().setDisplayHomeAsUpEnabled(getSupportFragmentManager().getBackStackEntryCount() > 0);
            }
        });
    }

    private void openMeetListFragment() {
        mMeetListFragment = new MeetListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fl_mainActivity, mMeetListFragment)
                .commit();
    }

    public void openDetailFragment(MeetModel model) {
        DetailFragment detailFragment = new DetailFragment();
        Bundle meetBundle = new Bundle();
        meetBundle.putSerializable("meet", model);
        detailFragment.setArguments(meetBundle);

        if (findViewById(R.id.MainInfoFrameFragment) != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.MainInfoFrameFragment, detailFragment)
                    .addToBackStack(null)
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_mainActivity, detailFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void showAndConfigureInfoFragment() {
        mMainInfoFragment = getSupportFragmentManager().findFragmentById(R.id.MainInfoFrameFragment);
        if (mMainInfoFragment == null && findViewById(R.id.MainInfoFrameFragment) != null) {
            OpenInfoFragment();
        }
    }

    public void OpenInfoFragment() {
        mMainInfoFragment = new MainInfoFragment();
        mMeetCount = mMeetApiService.getTodayMeets().size();
        Bundle meetCountBundle = new Bundle();
        meetCountBundle.putInt("mainActMeetCount", mMeetCount);
        mMainInfoFragment.setArguments(meetCountBundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.MainInfoFrameFragment, mMainInfoFragment)
                .commit();
    }

    public void OpenAddNewMeetActivity() {
        Intent intent = new Intent(this, NewMeetActivity.class);
        startActivity(intent);
    }
}
