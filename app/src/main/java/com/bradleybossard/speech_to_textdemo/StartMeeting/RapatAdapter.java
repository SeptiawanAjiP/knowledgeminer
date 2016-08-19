package com.bradleybossard.speech_to_textdemo.StartMeeting;

/**
 * Created by Septiawan Aji P on 8/18/2016.
 */

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bradleybossard.speech_to_textdemo.Object.Meeting;
import com.bradleybossard.speech_to_textdemo.R;

import java.util.List;

public class RapatAdapter extends RecyclerView.Adapter<RapatAdapter.MyViewHolder> {

    private List<Meeting> meetingList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView judul, pemipinRapat, tanggal;

        public MyViewHolder(View view) {
            super(view);
            judul = (TextView) view.findViewById(R.id.judul_rapat_row_mulai);
            pemipinRapat = (TextView)view.findViewById(R.id.pemimpin_rapat_mulai);
            tanggal = (TextView) view.findViewById(R.id.tanggal_rapat_row_mulai);
        }
    }
    public RapatAdapter(List<Meeting> meetingList) {
        this.meetingList = meetingList;
        Log.d("MeetingListAdapter",meetingList.toString());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mulai_rapat_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Meeting meeting = meetingList.get(position);
        holder.judul.setText(meeting.getJudulRapat());
        holder.pemipinRapat.setText("Pemimpin Rapat: "+meeting.getPemimpinRapat());
        holder.tanggal.setText(meeting.getWaktuRapat());
    }

    @Override
    public int getItemCount() {
        return meetingList.size();
    }
}