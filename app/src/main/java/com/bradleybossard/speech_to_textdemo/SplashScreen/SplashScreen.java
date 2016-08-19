package com.bradleybossard.speech_to_textdemo.SplashScreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bradleybossard.speech_to_textdemo.LoginActivity.LoginActivity;
import com.bradleybossard.speech_to_textdemo.MainMenu.MainMenuActivity;
import com.bradleybossard.speech_to_textdemo.Object.FamiliarWord;
import com.bradleybossard.speech_to_textdemo.R;
import com.bradleybossard.speech_to_textdemo.SessionManager.SessionManager;

import java.util.HashMap;

/**
 * Created by Septiawan Aji P on 8/19/2016.
 */
public class SplashScreen extends Activity {
    SessionManager sessionManager;
    String nama ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        sessionManager = new SessionManager(getApplicationContext());
        nama = sessionManager.getNama();
//        Log.d("HM ", nama.toString());
        Thread splash = new Thread(){
            public void run(){
                try{
                    sleep(1500);
                    if(nama==null){
                        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        splash.start();
    }
}
