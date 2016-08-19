package com.bradleybossard.speech_to_textdemo.StartMeeting;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bradleybossard.speech_to_textdemo.DatabaseHandler.DatabaseHandler;
import com.bradleybossard.speech_to_textdemo.MainMenu.MainMenuActivity;
import com.bradleybossard.speech_to_textdemo.R;

import java.util.ArrayList;


public class StartMeeting extends ActionBarActivity {
    private DatabaseHandler databaseHandler;
    protected static final int RESULT_SPEECH = 1;

    private Button btnSpeak,btnOk;
    private TextView txtText,notulensi;
    private String judulRapat,idRapat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mulai_rapat);
        Intent intent = getIntent();
        judulRapat= intent.getStringExtra("judulRapat");
        idRapat = intent.getStringExtra("idRapat");
        Log.d("id Rapat",idRapat);

        txtText = (TextView) findViewById(R.id.judul_rapat_dimulai);
        btnOk = (Button)findViewById(R.id.ok_rekam);
        notulensi = (TextView)findViewById(R.id.notulensi_rapat);
        btnSpeak = (Button) findViewById(R.id.mulai_rekam);

        txtText.setText(judulRapat);
        notulensi.setText("Ketika tombol MULAI ditekan maka device siap untuk merekam pembicaraan, pastikan device terhubung dengan jaringan Internet");
        notulensi.setTextColor(Color.BLUE);
        databaseHandler = new DatabaseHandler(getApplicationContext());

        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(
                        RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

                try {
                    startActivityForResult(intent, RESULT_SPEECH);
                    txtText.setText("");
                } catch (ActivityNotFoundException a) {
                    Toast t = Toast.makeText(getApplicationContext(),
                            "Opps! Your device doesn't support Speech to Text",
                            Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_SPEECH: {
                if (resultCode == RESULT_OK && null != data) {

                    final ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtText.setText(judulRapat);
                    btnSpeak.setVisibility(View.GONE);
                    btnOk.setVisibility(View.VISIBLE);
                    notulensi.setText(text.get(0).toUpperCase());
                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            databaseHandler.inputNotulensi(Integer.parseInt(idRapat), text.get(0));
                            databaseHandler.updateStatusRapat(Integer.parseInt(idRapat));
                            Toast.makeText(StartMeeting.this, "Rekaman telah tersimpan di Notulensi Rapat", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
//                    Toast.makeText(StartMeeting.this, databaseHandler.selectRow("Septiawan"), Toast.LENGTH_SHORT).show();
                }
                break;
            }

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(StartMeeting.this,MeetingInformation.class);
        startActivity(i);
        finish();
    }

}
