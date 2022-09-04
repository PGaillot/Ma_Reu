package com.gayo.maru;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gayo.maru.model.meetModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainMeetsRVAdapter extends RecyclerView.Adapter<MainMeetsRVAdapter.MyViewHolder> {

    ArrayList<meetModel> meetsArrayList;
    Context mContext;

    public MainMeetsRVAdapter(Context context, ArrayList<meetModel> meetsArrayList){
        this.mContext = context;
        this.meetsArrayList = meetsArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.meet_row,parent,  false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SimpleDateFormat hourDateFormatter = new SimpleDateFormat("HH");
        SimpleDateFormat minDateFormatter = new SimpleDateFormat("mm");
        holder.leaderName.setText(meetsArrayList.get(position).getRoom() + " - " + hourDateFormatter.format(meetsArrayList.get(position).getDate())+"h"+ minDateFormatter.format(meetsArrayList.get(position).getDate()) + " - " +meetsArrayList.get(position).getMeetLeader() );
        holder.mails.setText(String.join(", ", meetsArrayList.get(position).getMails()));

    }

    @Override
    public int getItemCount() {
        return meetsArrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView leaderName, mails;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            leaderName = itemView.findViewById(R.id.textView_leader);
            mails = itemView.findViewById(R.id.textview_mails);
        }
    }
}
