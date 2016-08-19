package com.bradleybossard.speech_to_textdemo.CreateMeeting;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bradleybossard.speech_to_textdemo.DatabaseHandler.DatabaseHandler;
import com.bradleybossard.speech_to_textdemo.MainMenu.MainMenuActivity;
import com.bradleybossard.speech_to_textdemo.Object.Meeting;
import com.bradleybossard.speech_to_textdemo.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Septiawan Aji P on 8/18/2016.
 */
public class CreateMeeting extends AppCompatActivity {
    private EditText inputJudul,inputTujuan,inputPesertaRapat,pemimpinRapat;
    private TextView tanggalRapat;
    private Button tanggalRapatButton;
    private Button submit;

    private DatePicker dpResult;
    private int tahun,bulan,tanggal;

    private Meeting meeting;

    private DatabaseHandler db;

    static final int DATE_DIALOG_ID = 999;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_keterangan_rapat);
        setCurrentDateonView();
        meeting = new Meeting();

        db=new DatabaseHandler(getApplicationContext());

        tanggalRapat = (TextView)findViewById(R.id.tanggal_rapat);
        inputJudul=(EditText)findViewById(R.id.input_judul_rapat);
        inputTujuan = (EditText)findViewById(R.id.input_tujuan_rapat);
        inputPesertaRapat = (EditText)findViewById(R.id.input_peserta_rapat);
        pemimpinRapat = (EditText)findViewById(R.id.input_pemimpin_rapat);
        submit = (Button)findViewById(R.id.submit);
        addListenerOnButton();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meeting.setJudulRapat(inputJudul.getText().toString());
                meeting.setTujuanRapat(inputTujuan.getText().toString());
                meeting.setWaktuRapat(tanggalRapat.getText().toString());
                meeting.setPemimpinRapat(pemimpinRapat.getText().toString());
                meeting.setPesertaRapat(inputPesertaRapat.getText().toString());

                if(meeting.getJudulRapat().isEmpty()){
                    Toast.makeText(CreateMeeting.this, "Judul Rapat Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }else{
                    if(meeting.getPemimpinRapat().isEmpty()){
                        Toast.makeText(CreateMeeting.this, "Pemimpin Rapat Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                    }else{
                        if(meeting.getTujuanRapat().isEmpty()){
                            Toast.makeText(CreateMeeting.this, "Tujuan Rapat Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                        }else{
                            if(meeting.getWaktuRapat().isEmpty()){
                                Toast.makeText(CreateMeeting.this, "Waktu Rapat Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                            }else{
                                if(meeting.getPesertaRapat().isEmpty()){
                                    Toast.makeText(CreateMeeting.this, "Peserta Rapat Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                                }else{
                                    meeting.setIdRapat(db.inputKeteranganRapat(meeting.getJudulRapat(), meeting.getTujuanRapat(),meeting.getPemimpinRapat(), meeting.getWaktuRapat()));
                                    String arrayPeserta[] = namaPeserta(meeting.getPesertaRapat());

                                    for(int i=0;i<arrayPeserta.length;i++){
                                        db.inputPesertaRapat(Integer.parseInt(meeting.getIdRapat()),arrayPeserta[i]);
                                    }
                                    Toast.makeText(CreateMeeting.this,"Data Rapat Disimpan", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }
                    }

                }


            }
        });


    }

    public void setCurrentDateonView(){
        dpResult = (DatePicker)findViewById(R.id.date_picker);
        final Calendar c = Calendar.getInstance();
        tahun = c.get(Calendar.YEAR);
        bulan = c.get(Calendar.MONTH);
        tanggal = c.get(Calendar.DAY_OF_MONTH);

        dpResult.init(tahun, bulan, tanggal, null);
    }

    public void addListenerOnButton(){
        tanggalRapatButton = (Button)findViewById(R.id.buttonTanggal);

        tanggalRapatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id){
            case DATE_DIALOG_ID:
                return  new DatePickerDialog(this,datePickerListener,tahun,bulan,tanggal);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        private String namaBulan="";
        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            tahun = selectedYear;
            bulan = selectedMonth+1;
            tanggal = selectedDay;

            if(bulan==1){
                namaBulan="Januari";
            }else if(bulan==2){
                namaBulan="Februari";
            }else if(bulan==3){
                namaBulan="Maret";
            }else if(bulan==4){
                namaBulan="April";
            }else if(bulan==5){
                namaBulan="Mei";
            }else if(bulan==6){
                namaBulan="Juni";
            }else if(bulan==7){
                namaBulan="Juli";
            }else if(bulan==8){
                namaBulan="Agustus";
            }else if(bulan==9){
                namaBulan="September";
            }else if(bulan==10){
                namaBulan="Oktober";
            }else if(bulan==11){
                namaBulan="November";
            }else{
                namaBulan="Desember";
            }
            tanggalRapat.setText(new StringBuilder().append(tanggal)
                    .append("-").append(namaBulan).append("-").append(tahun)
                    .append(" "));
            dpResult.init(tahun, bulan, tanggal, null);

        }
    };

    public String[] namaPeserta(String text){
        String split[]= text.split("-");
        return split;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(CreateMeeting.this,MainMenuActivity.class);
        startActivity(i);
        finish();
    }

}
