package com.gayo.maru;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gayo.maru.di.DI;
import com.gayo.maru.model.MeetModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import service.MeetApiService;


public class NewMeetFragment extends Fragment implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private TextView mDurationTV, mTVDate, mTVTime;
    private ChipGroup mChipGroup;
    private EditText mEditTextMails, mEditTextTopic, mEditTextLeader;
    private Spinner mRoomSpinner;
    private ImageButton mAddMailBtn;
    private SeekBar mDurationSeekbar;
    private LinearLayout mLLDate, mLLTime;
    private MeetApiService mMeetApiService;

    String[] roomsArray;
    Button mValidateButton;
    String mLeaderName, mDateString, mTimeString;
    Date mMeetDate, mTodayDate;
    List<String> mMeetMails = new ArrayList<>();
    String mMeetRoom;
    int mMeetDuration;
    String mTopic;


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        mDateString = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
//        mMeetDate = new SimpleDateFormat("dd/MM/yyyy").parse(mDateString);
        mTVDate.setText(mDateString);
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        mTimeString = hourOfDay + ":" + minute;
        mTVTime.setText(mTimeString);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_meet, container, false);

        mMeetApiService = DI.getMeetApiService();
        mChipGroup = view.findViewById(R.id.chipGroup);
        mEditTextMails = view.findViewById(R.id.AddActEditTextMail);
        mAddMailBtn = view.findViewById(R.id.AddMailBtn);
        mRoomSpinner = view.findViewById(R.id.AddActSpinnerRoom);
        mDurationSeekbar = view.findViewById(R.id.seekBar);
        mLLDate = view.findViewById(R.id.addNew_LL_date);
        mLLTime = view.findViewById(R.id.addNew_LL_time);
        mDurationTV = view.findViewById(R.id.addNew_tv_duration);
        mValidateButton = view.findViewById(R.id.validateButton);

        Calendar now = Calendar.getInstance();

        /** Click to the Edit btn on DATE */
        mLLDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        NewMeetFragment.this,
                        now.get(Calendar.YEAR), // Initial year selection
                        now.get(Calendar.MONTH), // Initial month selection
                        now.get(Calendar.DAY_OF_MONTH) // Inital day selection
                );
                dpd.show(getParentFragmentManager(), "Datepickerdialog");
            }
        });

        /** Click to the Edit btn on Time */
        mLLTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        NewMeetFragment.this,
                        now.get(Calendar.HOUR),
                        now.get(Calendar.MINUTE),
                        now.get(Calendar.SECOND),
                        true
                );
                tpd.show(getParentFragmentManager(), "Timepickerdialog");
            }
        });


        this.mAddMailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addChipMail();
            }
        });


        mTVDate = view.findViewById(R.id.editTextDate);
        mTVTime = view.findViewById(R.id.tvHour);
        mTodayDate = new Date(System.currentTimeMillis());
        mMeetDate = mTodayDate;
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat hourFormatter = new SimpleDateFormat("hh");
        SimpleDateFormat minFormatter = new SimpleDateFormat("mm");
        if (mMeetDate == mTodayDate) {
            mTVDate.setText("Aujourd'hui");
        } else {
            mTVDate.setText(dateFormatter.format(mMeetDate));
        }
        mTVTime.setText(hourFormatter.format(mMeetDate) + 'h' + minFormatter.format(mMeetDate));


        SetRoomsSpinner();

        mDurationSeekbar.setMax(7);
        mDurationSeekbar.setProgress(1);

        mDurationSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int progress = 1;

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progress = i;
                System.out.println(i);
                mDurationTV.setText(progress + 1 + "");
                mMeetDuration = progress + 1;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // When the user click on validate Button.
        mValidateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** On the validate btn click.*/
                mMeetRoom = mRoomSpinner.getSelectedItem().toString();
                mMeetDuration = mDurationSeekbar.getProgress();
                if (FormIsValid()) {
                    try {
                        SetMeetDate(mDateString, mTimeString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String[] meetMails = mMeetMails.toArray(new String[mMeetMails.size()]);
                    MeetModel newMeet = new MeetModel(mLeaderName, mMeetRoom, meetMails, mMeetDate, mMeetDuration, mTopic);
                    System.out.println("Topic : " + newMeet.getTopic() + " / Leader : " + newMeet.getMeetLeader() + " / Room : " + newMeet.getRoom() + " / Date : " + newMeet.getDate() + " / Duration : " + newMeet.getDuration() + " / MailList : " + newMeet.getMails());
                    addMeet(newMeet);
                    NavigateToMainActivity();
                }

            }
        });
        mEditTextTopic = view.findViewById(R.id.addNew_et_topic);
        mEditTextTopic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()) {
                    mTopic = editable.toString();
                } else {
                    mTopic = "No Topic";
                }
            }
        });

        mEditTextLeader = view.findViewById(R.id.AddActEditTextTextLeader);
        mEditTextLeader.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()) {
                    mLeaderName = editable.toString();
                } else {
                    mLeaderName = "no leader";
                }
            }
        });
        return view;
    }

    /**
     * Set all Rooms from string.xml to the RoomSpinner
     */
    public void SetRoomsSpinner() {
        mMeetApiService = DI.getMeetApiService();
        roomsArray = mMeetApiService.getRooms().toArray(new String[0]);
        List<String> roomsStringList = Arrays.asList(roomsArray);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item, roomsStringList
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mRoomSpinner.setAdapter(spinnerAdapter);
    }

    public void addMeet(MeetModel meet) {
        mMeetApiService.addMeet(meet);
        List<MeetModel> newMMList = mMeetApiService.getMeets();
        System.out.println(newMMList);
    }

    public void addChipMail() {
        String mail = mEditTextMails.getText().toString();
        if (mail.isEmpty() || mail == null) {
            Toast.makeText(getActivity(), "ce mail est vide !", Toast.LENGTH_LONG).show();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            Toast.makeText(getActivity(), mail + " n'est pas un mail invalide", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            mMeetMails.add(mail);
            LayoutInflater inflater = LayoutInflater.from(getActivity());

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
            Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void handleChipCloseIconClicked(@NonNull Chip chip) {
        ChipGroup parent = (ChipGroup) chip.getParent();
        parent.removeView(chip);
        mMeetMails.remove(chip.getText().toString());
    }

    public Boolean FormIsValid() {
        if (mTopic == null) {
            Toast.makeText(getActivity(), "La réunion n'a pas de sujet.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mMeetDate == null) {
            Toast.makeText(getActivity(), "La réunion n'a pas de date.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mMeetDuration <= 0) {
            Toast.makeText(getActivity(), "La réunion n'a pas de durée.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mMeetRoom == null) {
            Toast.makeText(getActivity(), "La réunion n'a pas de salle.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mLeaderName == null) {
            Toast.makeText(getActivity(), "La réunion n'a pas de leader.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mMeetMails.size() <= 1) {
            if (mMeetMails.size() == 0) {
                Toast.makeText(getActivity(), "Vous devez ajouter des mails pour inviter des collègues à la reunion.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "Vous devez être minimun 2 pour faire une réunion.", Toast.LENGTH_LONG).show();
            }
            return false;
        } else {
            return true;
        }
    }

    void NavigateToMainActivity() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }


    void SetMeetDate(String date, String hour) throws ParseException {
        DateFormat tDateFormat = new SimpleDateFormat("'HH:mm'");
        DateFormat dDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat meetDateFull = new SimpleDateFormat("dd/MM/yyyy:HH:mm");
        if (date != null && hour != null) {
            mMeetDate = meetDateFull.parse(date + ":" + hour);
        } else {
            if (date != null) {
                mMeetDate = dDateFormat.parse(date);
            } else if (hour != null) {
                mMeetDate = tDateFormat.parse(hour);
            } else {
            } System.out.println(mMeetDate);
        }
    }

}