package com.gayo.maru;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DetailMeetGuessMailRVAdapter extends RecyclerView.Adapter<DetailMeetGuessMailRVAdapter.MyViewHolder> {

    List<String> guessMailsList;
    Context mContext;

    public DetailMeetGuessMailRVAdapter(List<String> guessMailsList, Context context) {
        this.guessMailsList = guessMailsList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public DetailMeetGuessMailRVAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.guess_detail_entry, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailMeetGuessMailRVAdapter.MyViewHolder holder, int position) {
        holder.guessMail.setText(guessMailsList.get(position));
    }

    @Override
    public int getItemCount() {
        return guessMailsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView guessMail;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            guessMail = itemView.findViewById(R.id.guess_tv_entry);

        }
    }
}
