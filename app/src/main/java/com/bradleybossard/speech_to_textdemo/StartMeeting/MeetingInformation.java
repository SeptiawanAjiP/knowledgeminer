package com.bradleybossard.speech_to_textdemo.StartMeeting;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.bradleybossard.speech_to_textdemo.DatabaseHandler.DatabaseHandler;
import com.bradleybossard.speech_to_textdemo.MainMenu.MainMenuActivity;
import com.bradleybossard.speech_to_textdemo.Object.Meeting;
import com.bradleybossard.speech_to_textdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Septiawan Aji P on 8/18/2016.
 */
public class MeetingInformation extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Meeting> meetingList = new ArrayList<>();
    private RapatAdapter rapatAdapter;
    private TextView tidakAdaMeeting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_mulai_rapat);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        tidakAdaMeeting = (TextView)findViewById(R.id.tidak_punya_rapat);

        rapatAdapter= new RapatAdapter(meetingList);

        recyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(rapatAdapter);


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Meeting meeting = meetingList.get(position);
                Intent intent = new Intent(getApplicationContext(), StartMeeting.class);
                intent.putExtra("judulRapat", meeting.getJudulRapat());
                intent.putExtra("idRapat",meeting.getIdRapat());
                startActivity(intent);
                finish();
            }

            @Override
            public void onLongClick(View view, int position) {
//                showDialog();
            }
        }));

        prepareMeetingData();

    }

    private void prepareMeetingData() {
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        ArrayList<Meeting> arrayListMeeting = db.keteranganSemuaRapatBelumDimulai();
        if(arrayListMeeting.isEmpty()){
            recyclerView.setVisibility(View.GONE);
            tidakAdaMeeting.setVisibility(View.VISIBLE);
        }else{
            for(int i=0;i<arrayListMeeting.size();i++){
                Meeting meeting = new Meeting(arrayListMeeting.get(i).getIdRapat(),arrayListMeeting.get(i).getJudulRapat().toUpperCase(),arrayListMeeting.get(i).getPemimpinRapat(),arrayListMeeting.get(i).getWaktuRapat().toUpperCase());
                Log.d("Pemimpin Rapat",arrayListMeeting.get(i).getPemimpinRapat().toString());
                meetingList.add(meeting);
            }
        }

        Log.d("MeetingListUtama",meetingList.toString());

        rapatAdapter.notifyDataSetChanged();
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private MeetingInformation.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final MeetingInformation.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public void showDialog(){

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialView = inflater.inflate(R.layout.dialog_upload, null);

        dialogBuilder.setView(dialView);

        dialogBuilder.setTitle("Upload");
        dialogBuilder.setMessage("Upload Keterangan dan Notulensi Rapat ?");
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        alertDialog.setCancelable(true);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(MeetingInformation.this,MainMenuActivity.class);
        startActivity(i);
        finish();
    }
}
