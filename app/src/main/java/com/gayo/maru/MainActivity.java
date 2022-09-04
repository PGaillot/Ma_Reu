package com.gayo.maru;

import android.os.Bundle;

import com.gayo.maru.model.meetModel;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.gayo.maru.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;



public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    ArrayList<meetModel> mMeetModelArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        for (int i = 0; i < 5; i++) {SetUpMeet();}
    }

    public void SetUpMeet(){
        int listSize = GenerateRandGuestListSize();
        Date date=new java.util.Date();
        String[] meetRoomsNames = getResources().getStringArray(R.array.rooms_name);
        String[] meetLeaderNames = getResources().getStringArray(R.array.meet_leaders);


            mMeetModelArrayList.add(new meetModel(
                    meetLeaderNames[ThreadLocalRandom.current().nextInt(0, getResources().getStringArray(R.array.meet_leaders).length)],
                    meetRoomsNames[ThreadLocalRandom.current().nextInt(0, getResources().getStringArray(R.array.rooms_name).length)],
                    SetMailsList(listSize),
                    date));


            System.out.println("List Size  = " + listSize);
            SetMailsList(listSize);
    }


    /** Set a mail list with Int @param */
    public String[] SetMailsList(int guests){
        String[] fullMeetMails = getResources().getStringArray(R.array.users_mails);
        List<String> outMeetMails = new ArrayList<>(Arrays.asList());
        for (int i = 0; i < guests; i++) {
            int randomNum = ThreadLocalRandom.current().nextInt(0, getResources().getStringArray(R.array.users_mails).length);
            if (outMeetMails.contains(fullMeetMails[randomNum])){
                guests++;
            }else{
                outMeetMails.add(fullMeetMails[randomNum]);
            }
        }
        outMeetMails.toArray();
        String[] outArray = outMeetMails.toArray(new String[outMeetMails.size()]);
        PrintStringArray(outArray);
        return outArray;
    }

    /** To print a String Array. */
    public void PrintStringArray(String[] stringArr){
        for (int i = 0; i < stringArr.length; i++) {
            System.out.println("#### ITEM [" +i+ "] : " + stringArr[i]);
        }
    }

    public int GenerateRandGuestListSize(){
        int randNum = ThreadLocalRandom.current().nextInt(0, getResources().getStringArray(R.array.users_mails).length);
        if (randNum <= 1){
            randNum = 2;
        } else if(randNum >= getResources().getStringArray(R.array.users_mails).length + 1) {
            randNum = getResources().getStringArray(R.array.users_mails).length;
        }
        return randNum;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}