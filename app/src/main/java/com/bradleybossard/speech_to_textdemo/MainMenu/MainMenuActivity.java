package com.bradleybossard.speech_to_textdemo.MainMenu;

/**
 * Created by Septiawan Aji P on 8/17/2016.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bradleybossard.speech_to_textdemo.CreateMeeting.CreateMeeting;
import com.bradleybossard.speech_to_textdemo.NotulensiRapat.NotulensiRapat;
import com.bradleybossard.speech_to_textdemo.StartMeeting.MeetingInformation;
import com.bradleybossard.speech_to_textdemo.R;
import com.bradleybossard.speech_to_textdemo.SessionManager.SessionManager;

import java.util.HashMap;


public class MainMenuActivity extends AppCompatActivity{
    ImageButton buatRapat, mulaiRapat,notulensi;
    TextView nama;
    SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_layout);
        sm = new SessionManager(getApplicationContext());

        HashMap<String,String> hashMap= sm.getUserSession();
        buatRapat = (ImageButton)findViewById(R.id.imageButtonBuatRapat);
        mulaiRapat = (ImageButton)findViewById(R.id.imageButtonMulaiRapat);
        notulensi= (ImageButton)findViewById(R.id.imageButtonNotulensi);
        buatRapat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateMeeting.class);
                startActivity(intent);
                finish();
            }
        });
        mulaiRapat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MeetingInformation.class);
                startActivity(intent);
                finish();
            }
        });
        notulensi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NotulensiRapat.class);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if(id==R.id.action_setting_logout){
//            sm.deleteSession();
//            Intent intent = new Intent(getApplicationContext(),SplashScreen.class);
//            startActivity(intent);
//            finish();
//        }
//
//        return super.onOptionsItemSelected(item);
//
//    }
}
