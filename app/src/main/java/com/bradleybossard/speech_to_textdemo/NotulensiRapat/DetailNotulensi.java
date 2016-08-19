package com.bradleybossard.speech_to_textdemo.NotulensiRapat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.bradleybossard.speech_to_textdemo.DatabaseHandler.DatabaseHandler;
import com.bradleybossard.speech_to_textdemo.MainMenu.MainMenuActivity;
import com.bradleybossard.speech_to_textdemo.Object.Meeting;
import com.bradleybossard.speech_to_textdemo.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Septiawan Aji P on 8/19/2016.
 */
public class DetailNotulensi extends AppCompatActivity {
    private String idRapat;
    private DatabaseHandler db;
    private TextView judulRapat,tanggalRapat,pemimpinRapat,tujuanRapat,pesertaRapat,notulensiRapat;
    private Meeting meeting;
    private ArrayList<String> arrayPeserta;
    private String namaPeserta,notulensi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_rapat);
        db = new DatabaseHandler(getApplicationContext());
        Intent intent = getIntent();
        idRapat=intent.getStringExtra("idRapat");

        judulRapat=(TextView)findViewById(R.id.detail_judul_rapat);
        tanggalRapat = (TextView)findViewById(R.id.detail_tanggal_rapat);
        pemimpinRapat = (TextView)findViewById(R.id.detail_pemimpin_rapat);
        tujuanRapat = (TextView)findViewById(R.id.detail_tujuan_rapat);
        pesertaRapat = (TextView)findViewById(R.id.detail_peserta_rapat);
        notulensiRapat = (TextView)findViewById(R.id.detail_notulensi_rapat);

        meeting = db.getDetailRapat(Integer.parseInt(idRapat));
        arrayPeserta = db.pesertaRapat(Integer.parseInt(idRapat));
        notulensi = db.getNotulensiRapat(Integer.parseInt(idRapat));

        namaPeserta="";
        for(int i=0;i<arrayPeserta.size();i++){
            namaPeserta = namaPeserta+", "+arrayPeserta.get(i).toUpperCase();
        }
        judulRapat.setText(meeting.getJudulRapat());
        tanggalRapat.setText(meeting.getWaktuRapat());
        pemimpinRapat.setText("Pemimpin Rapat: "+meeting.getPemimpinRapat());
        tujuanRapat.setText("Tujuan Rapat: "+meeting.getTujuanRapat());
        pesertaRapat.setText("Peserta Rapat: "+namaPeserta.substring(1));
        notulensiRapat.setText("Notulensi Rapat: "+notulensi);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(DetailNotulensi.this,NotulensiRapat.class);
        startActivity(i);
        finish();
    }
}
