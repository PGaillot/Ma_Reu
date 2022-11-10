package com.gayo.maru;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.gayo.maru.di.DI;
import com.gayo.maru.model.MeetModel;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import event.DeleteMeetEvent;
import service.DatesApiService;

public class MainMeetsRVAdapter extends RecyclerView.Adapter<MainMeetsRVAdapter.MyViewHolder> implements Serializable {

    ArrayList<MeetModel> meetsArrayList;
    Context mContext;
    MainMeetRVAdapterItemClickListener mMainMeetRVAdapterItemClickListener;

    public MainMeetsRVAdapter(Context context, ArrayList<MeetModel> meetsArrayList, MainMeetRVAdapterItemClickListener mMainMeetRVAdapterItemClickListener) {
        this.mContext = context;
        this.meetsArrayList = meetsArrayList;
        this.mMainMeetRVAdapterItemClickListener = mMainMeetRVAdapterItemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.meet_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.leaderName.setText(DI.getDatesApiService().GenerateDateString(meetsArrayList.get(position).getDate()) + " à " + DI.getDatesApiService().GenerateHourString(meetsArrayList.get(position).getDate()) + " - " + meetsArrayList.get(position).getMeetLeader());
        holder.mails.setText(String.join(", ", meetsArrayList.get(position).getMails()));
        holder.roomColorDot.setCardBackgroundColor(GetColorLinkOfRoom(meetsArrayList.get(position).getRoom()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMainMeetRVAdapterItemClickListener.onClick(meetsArrayList.get(position));
            }
        });
    }

    private int GetColorLinkOfRoom(String meetColor) {
        int color;
        switch (meetColor) {
            case "bleu":
                color = ContextCompat.getColor(mContext, R.color.room_blue);
                break;
            case "jaune":
                color = ContextCompat.getColor(mContext, R.color.room_yellow);
                break;
            case "verte":
                color = ContextCompat.getColor(mContext, R.color.room_green);
                break;
            case "rose":
                color = ContextCompat.getColor(mContext, R.color.room_pink);
                break;
            case "blanche":
                color = ContextCompat.getColor(mContext, R.color.room_white);
                break;
            case "rouge":
                color = ContextCompat.getColor(mContext, R.color.room_red);
                break;
            case "noir":
                color = ContextCompat.getColor(mContext, R.color.room_black);
                break;
            default:
                color = ContextCompat.getColor(mContext, R.color.purple_200);
                break;
        }
        return color;
    }

    @Override
    public int getItemCount() {
        return meetsArrayList.size();
    }

    public void update(ArrayList<MeetModel> list) {
//        meetsArrayList = new ArrayList<>();
        meetsArrayList.clear();
        meetsArrayList.addAll(list);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView leaderName, mails;
        CardView roomColorDot;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            leaderName = itemView.findViewById(R.id.textView_leader);
            mails = itemView.findViewById(R.id.textview_mails);
            roomColorDot = itemView.findViewById(R.id.roomColorDotCard);

            //  Delete item Click !
            itemView.findViewById(R.id.meet_row_iv_delete).setOnClickListener(view -> {

                // Create DialogAlert to confirm the delete action from user.
                AlertDialog.Builder confirmDeleteMeetAlertDialog = new AlertDialog.Builder(mContext);
                confirmDeleteMeetAlertDialog.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User click "Yes"
                        String toastMessage = "La réunion de " + meetsArrayList.get(getAdapterPosition()).getMeetLeader() + " à été effacée.";
                        Toast deleteToast = Toast.makeText(mContext, toastMessage, Toast.LENGTH_LONG);
                        deleteToast.show();
                        EventBus.getDefault().post(new DeleteMeetEvent(meetsArrayList.get(getAdapterPosition())));
                    }
                });

                confirmDeleteMeetAlertDialog.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User click "No"
                        System.out.println("The present Meeting as not been deleted");
                    }
                });
                confirmDeleteMeetAlertDialog.setMessage("de vouloir supprimer cette réunion ?");
                confirmDeleteMeetAlertDialog.setTitle("Êtes-vous sûr ?");
                AlertDialog dialog = confirmDeleteMeetAlertDialog.create();
                dialog.show();
            });
        }
    }

}
