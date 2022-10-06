package com.gayo.maru;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gayo.maru.di.DI;
import com.gayo.maru.model.MeetModel;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import event.DeleteMeetEvent;

public class MainMeetsRVAdapter extends RecyclerView.Adapter<MainMeetsRVAdapter.MyViewHolder> implements Serializable {

    ArrayList<MeetModel> meetsArrayList;
    Context mContext;

    public MainMeetsRVAdapter(Context context, ArrayList<MeetModel> meetsArrayList) {
        this.mContext = context;
        this.meetsArrayList = meetsArrayList;
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
        SimpleDateFormat hourDateFormatter = new SimpleDateFormat("HH");
        SimpleDateFormat minDateFormatter = new SimpleDateFormat("mm");
        holder.leaderName.setText("Salle " + meetsArrayList.get(position).getRoom() + " - " + hourDateFormatter.format(meetsArrayList.get(position).getDate()) + "h" + minDateFormatter.format(meetsArrayList.get(position).getDate()) + " - " + meetsArrayList.get(position).getMeetLeader());
        holder.mails.setText(String.join(", ", meetsArrayList.get(position).getMails()));

//        Click on the item row
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailActivityIntent = new Intent(view.getContext(), DetailMeetActivity.class);
                MeetModel currentMeet = meetsArrayList.get(position);
                detailActivityIntent.putExtra("currentMeet", currentMeet);
                view.getContext().startActivity(detailActivityIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return meetsArrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView leaderName, mails;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            leaderName = itemView.findViewById(R.id.textView_leader);
            mails = itemView.findViewById(R.id.textview_mails);



//            Delete item Click !
            itemView.findViewById(R.id.meet_row_iv_delete).setOnClickListener(view -> {
                System.out.println("CLICK ! ###########################################################################################################");
                EventBus.getDefault().post(new DeleteMeetEvent(meetsArrayList.get(getAdapterPosition())));
            });
        }
    }
}
