package com.gayo.maru;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gayo.maru.model.MeetModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class NewMeetActivity extends AppCompatActivity {

    private ChipGroup mChipGroup;
    private EditText mEditTextMails;
    private ImageButton mAddMailBtn;

    String[] roomsArray;
    Button validateButton;


    MeetModel mMeet;

    String mLeaderName;
    Date mMeetDate, mTodayDate;
    List<String> mMeetMails = new ArrayList<>();
    String mMeetRoom;
    int mMeetDuration;
    String mTopic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.mChipGroup = (ChipGroup) this.findViewById(R.id.chipGroup);
        this.mEditTextMails = (EditText) this.findViewById(R.id.AddActEditTextMail);
        this.mAddMailBtn = (ImageButton) this.findViewById(R.id.AddMailBtn);
        Spinner roomSpinner = (Spinner) findViewById(R.id.AddActSpinnerRoom);
        SeekBar durationSeekbar =  (SeekBar) findViewById(R.id.seekBar);

        this.mAddMailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addChipMail();
            }
        });


        TextView tvDate = findViewById(R.id.editTextDate);
        TextView tvHour = findViewById(R.id.tvHour);
        mTodayDate = new Date(System.currentTimeMillis());
        mMeetDate = mTodayDate;
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat hourFormatter = new SimpleDateFormat("hh");
        SimpleDateFormat minFormatter = new SimpleDateFormat("mm");
        if (mMeetDate == mTodayDate){
            tvDate.setText("Aujourd'hui");
        }else{
            tvDate.setText(dateFormatter.format(mMeetDate));
        }
        tvHour.setText(hourFormatter.format(mMeetDate) + 'h' + minFormatter.format(mMeetDate));


        SetRoomsSpinner();

        validateButton = findViewById(R.id.validateButton);
//        validateButton.setEnabled(FormIsValid());

        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** On the validate btn click.*/
                mMeetRoom = roomSpinner.getSelectedItem().toString();
                mMeetDuration = durationSeekbar.getProgress();
                if (FormIsValid()){
                    String[] meetMails = mMeetMails.toArray(new String[mMeetMails.size()]);
                    MeetModel newMeet = new MeetModel(mLeaderName, mMeetRoom, meetMails, mMeetDate,mMeetDuration, mTopic);
                    System.out.println("Topic : " + newMeet.getTopic() + " / Leader : " + newMeet.getMeetLeader() + " / Room : " + newMeet.getRoom() + " / Date : " + newMeet.getDate() + " / Duration : " + newMeet.getDuration() + " / MailList : " + newMeet.getMails());
                }

            }
        });


        EditText editTextTopic = findViewById(R.id.addNew_et_topic);
        editTextTopic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()){
                    mTopic = editable.toString();
                } else {
                    mTopic = "No Topic";
                }
            }
        });

        EditText editTextLeader = findViewById(R.id.AddActEditTextTextLeader);
        editTextLeader.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()){
                    mLeaderName = editable.toString();
                } else {
                    mLeaderName = "no leader";
                }
            }
        });


    }

    /**
     Set all Rooms from string.xml to the RoomSpinner
     */
    public void SetRoomsSpinner(){
        roomsArray = getResources().getStringArray(R.array.rooms_name);
        List<String> roomsStringList = Arrays.asList(roomsArray);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, roomsStringList
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner roomsSpinner = (Spinner) findViewById(R.id.AddActSpinnerRoom);
        roomsSpinner.setAdapter(spinnerAdapter);
    }

    public MeetModel GenerateMeet(String leaderName, Date meetDate, String room, String[] arrayMails, int meetDuration, String topic){
        MeetModel newMeet = new MeetModel(leaderName, room, arrayMails, meetDate, meetDuration, topic );
        return  newMeet;
    }


    public void addChipMail(){
        String mail = mEditTextMails.getText().toString();
        if(mail.isEmpty() || mail == null){
            Toast.makeText(this, "ce mail est vide !", Toast.LENGTH_LONG).show();
            return;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            Toast.makeText(this, mail + " n'est pas un mail invalide", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            mMeetMails.add(mail);
            LayoutInflater inflater = LayoutInflater.from(this);

            Chip newChip = (Chip) inflater.inflate(R.layout.mail_chip_entry, mChipGroup, false);
            newChip.setText(mail);
            newChip.setCheckable(false);
            newChip.setCloseIconVisible(true);
            newChip.setOnCloseIconClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleChipCloseIconClicked((Chip) v);
                }
            });
            this.mChipGroup.addView(newChip);
            this.mEditTextMails.setText("");
            System.out.println(mMeetMails);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void handleChipCloseIconClicked(@NonNull Chip chip) {
        ChipGroup parent = (ChipGroup) chip.getParent();
        parent.removeView(chip);
        mMeetMails.remove(chip.getText().toString());
    }


    public Boolean FormIsValid(){
        if (mTopic != null && mMeetDate != null && mMeetDuration >= 1 && mMeetRoom != null && mLeaderName != null && mMeetMails.size() >= 2){
            return true;
        } else {
            Toast.makeText(this, "La réunion n'est pas valide", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}