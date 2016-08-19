package com.bradleybossard.speech_to_textdemo.NotulensiRapat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bradleybossard.speech_to_textdemo.Connection.Connection;
import com.bradleybossard.speech_to_textdemo.Connection.ConverterParameter;
import com.bradleybossard.speech_to_textdemo.Connection.JSONParser;
import com.bradleybossard.speech_to_textdemo.DatabaseHandler.DatabaseHandler;
import com.bradleybossard.speech_to_textdemo.MainMenu.MainMenuActivity;
import com.bradleybossard.speech_to_textdemo.Object.FamiliarWord;
import com.bradleybossard.speech_to_textdemo.Object.Meeting;
import com.bradleybossard.speech_to_textdemo.R;
import com.bradleybossard.speech_to_textdemo.SessionManager.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Septiawan Aji P on 8/19/2016.
 */
public class DetailNotulensi extends AppCompatActivity {
    private String idRapat;
    private DatabaseHandler db;
    private TextView judulRapat,tanggalRapat,pemimpinRapat,tujuanRapat,pesertaRapat,notulensiRapat,sudahDiupload;
    private Meeting meeting;
    private ArrayList<String> arrayPeserta;
    String namaPeserta,notulensi;
    private SessionManager sm;
    private Button upload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_rapat);
        db = new DatabaseHandler(getApplicationContext());
        Intent intent = getIntent();
        idRapat=intent.getStringExtra("idRapat");
        upload = (Button)findViewById(R.id.btn_upload);

        judulRapat=(TextView)findViewById(R.id.detail_judul_rapat);
        tanggalRapat = (TextView)findViewById(R.id.detail_tanggal_rapat);
        pemimpinRapat = (TextView)findViewById(R.id.detail_pemimpin_rapat);
        tujuanRapat = (TextView)findViewById(R.id.detail_tujuan_rapat);
        pesertaRapat = (TextView)findViewById(R.id.detail_peserta_rapat);
        notulensiRapat = (TextView)findViewById(R.id.detail_notulensi_rapat);
        sudahDiupload = (TextView)findViewById(R.id.sudah_diupload);

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
        notulensiRapat.setText("Notulensi Rapat: " + notulensi);
        Log.d("Status Upload",meeting.getStatusUpload());
        if(meeting.getStatusUpload().equals("Sudah Diupload")){
            upload.setVisibility(View.GONE);
            sudahDiupload.setVisibility(View.VISIBLE);
        }else{
            upload.setVisibility(View.VISIBLE);
            sudahDiupload.setVisibility(View.GONE);
        }
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new uploadKeterangan().execute();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(DetailNotulensi.this,NotulensiRapat.class);
        startActivity(i);
        finish();
    }

    private class uploadKeterangan extends AsyncTask<String,Void,String> {
        ProgressDialog pDialog;
        JSONObject json;
        JSONArray data;
        String berhasil="";

        String tangkapError = "";
        String respon;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DetailNotulensi.this);
            pDialog.setMessage("Loading");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            sm = new SessionManager(getApplicationContext());
            HashMap<String,String> parameter = new HashMap<>();
            parameter.put(Connection.getKODE(),Connection.getInputRapat());
            parameter.put(FamiliarWord.getJudulRapat(),meeting.getJudulRapat());
            parameter.put(FamiliarWord.getTujuanRapat(),meeting.getTujuanRapat());
            parameter.put(FamiliarWord.getPemimpinRapat(),meeting.getPemimpinRapat());
            parameter.put(FamiliarWord.getWaktuRapat(), meeting.getWaktuRapat());
            parameter.put(FamiliarWord.getIdUser(), sm.getIdUser());
            Log.d("Parameter 1",parameter.toString());
            JSONParser jsonParser = new JSONParser();
            try{
                json = jsonParser.getJSONFromUrl(Connection.getURL() + ConverterParameter.getQuery(parameter));
                respon = json.getString(Connection.getRESPON());
                Log.d("Respon", respon);
                if(respon!=Connection.getSATU()){
                    parameter.remove(Connection.getKODE());
                    parameter.remove(FamiliarWord.getTujuanRapat());
                    parameter.remove(FamiliarWord.getPemimpinRapat());
                    parameter.remove(FamiliarWord.getIdUser());

                    parameter.put(Connection.getKODE(), Connection.getGetIdRapat());
                    Log.d("Parameter 2",parameter.toString());
                    json = jsonParser.getJSONFromUrl(Connection.getURL() + ConverterParameter.getQuery(parameter));
                    data = json.getJSONArray(Connection.getRESPON());
                    for(int i = 0;i<data.length();i++){
                        JSONObject c = data.getJSONObject(i);
                        meeting.setIdRapat(c.getString("id_rapat"));
                    }
                    Log.d("ID MEETING",meeting.getIdRapat());
                    parameter.remove(Connection.getKODE());
                    parameter.remove(FamiliarWord.getJudulRapat());
                    parameter.remove(FamiliarWord.getWaktuRapat());

                    parameter.put(Connection.getKODE(), Connection.getInputNotulensi());
                    parameter.put(FamiliarWord.getNOTULENSI(), notulensi);
                    parameter.put(FamiliarWord.getIdRapat(),meeting.getIdRapat());
                    Log.d("Parameter 3", parameter.toString());
                    json = jsonParser.getJSONFromUrl(Connection.getURL() + ConverterParameter.getQuery(parameter));
                    respon = json.getString(Connection.getRESPON());
                    Log.d("Apakie",respon);
                }

            }catch (Exception e){
                tangkapError = e.getMessage();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("Berhasil", berhasil);
            if (tangkapError == ("")) {

//                    sessionManager.createSessionLogin(akun.getUser_name(), akun.getName());
//                    Toast.makeText(LoginActivity.this, user.getNama(), Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(getApplicationContext(),MainMenuActivity.class);
//                    startActivity(intent);
//                    finish();
                db.updateStatusUpload(Integer.parseInt(idRapat));
                Toast.makeText(getApplicationContext(), "Upload Sukses", Toast.LENGTH_SHORT).show();
                upload.setVisibility(View.GONE);
                sudahDiupload.setVisibility(View.VISIBLE);
            }else{
                Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_SHORT).show();
            }
            pDialog.dismiss();

        }
    }
}
