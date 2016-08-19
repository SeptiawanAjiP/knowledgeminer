package com.bradleybossard.speech_to_textdemo.DatabaseHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.bradleybossard.speech_to_textdemo.Object.Meeting;

import java.util.ArrayList;

/**
 * Created by Septiawan Aji P on 8/16/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "db_sentences";
    private static final String TABLE_SENTENCES = "table_sentences";

    private static final String ID_SENTENCES = "id_sentences";
    private static final String USER_NAME ="user_name";
    private static final String TITLE = "title";
    private static final String SENTENCES = "sentences";
    private static final String UPLOAD_STATUS = "upload_status";

    //tabel RAPAT
    private static final String TABLE_RAPAT = "table_rapat";
    private static final String ID_RAPAT = "id_rapat";
    private static final String JUDUL_RAPAT = "judul_rapat";
    private static final String TUJUAN_RAPAT = "tujuan_rapat";
    private static final String WAKTU_RAPAT = "waktu_rapat";
    private static final String PEMIPIN_RAPAT = "pemimpin_rapat";
    private static final String STATUS_RAPAT = "status_rapat";
    private static final String STATUS_UPLOAD = "status_upload";
    static final String BELUM_DIMULAI = "Belum Dimulai";
    static final String SUDAH_BERAKHIR = "Sudah Berakhir";
    private static final String SUDAH_UPLOAD = "Sudah Diupload";
    private static final String BELUM_UPLOAD = "Belum Diupload";

    //table NOTULENSI
    private static final String TABLE_NOTULENSI="table_notulensi";
    private static final String ID_NOTULENSI="id_notulensi";
    private static final String NOTULENSI ="notulensi";

    //tabel PESERTA RAPAT
    private static final String TABLE_PESERTA_RAPAT = "table_peserta_rapat";
    private static final String ID_PESERTA_RAPAT = "id_peserta_rapat";
    private static final String NAMA_PESERTA_RAPAT = "nama_peserta_rapat";


    public DatabaseHandler(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RAPAT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PESERTA_RAPAT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTULENSI);
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RAPAT_TABLE = "CREATE TABLE "+ TABLE_RAPAT +" ("
                +ID_RAPAT+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +JUDUL_RAPAT+" TEXT,"
                +TUJUAN_RAPAT+" TEXT,"
                +PEMIPIN_RAPAT+" TEXT,"
                +WAKTU_RAPAT+" TEXT,"
                +STATUS_RAPAT+" TEXT,"
                +STATUS_UPLOAD+" TEXT)";

        String CREATE_PESERTA_RAPAT_TABLE = "CREATE TABLE "+ TABLE_PESERTA_RAPAT +" ("
                +ID_PESERTA_RAPAT+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +ID_RAPAT+" INTEGER,"
                +NAMA_PESERTA_RAPAT+" TEXT)";

        String CREATE_NOTULENSI = "CREATE TABLE "+TABLE_NOTULENSI+" ("
                +ID_NOTULENSI+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +ID_RAPAT+" INTEGER,"
                +NOTULENSI+" TEXT)";


        db.execSQL(CREATE_RAPAT_TABLE);
        db.execSQL(CREATE_PESERTA_RAPAT_TABLE);
        db.execSQL(CREATE_NOTULENSI);
    }


    public String inputKeteranganRapat(String judul,String tujuan,String pemimpin,String waktu){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(JUDUL_RAPAT,judul);
        values.put(TUJUAN_RAPAT, tujuan);
        values.put(PEMIPIN_RAPAT,pemimpin);
        values.put(WAKTU_RAPAT, waktu);
        values.put(STATUS_RAPAT,BELUM_DIMULAI);
        values.put(STATUS_UPLOAD,BELUM_UPLOAD);
        db.insert(TABLE_RAPAT, null, values);

        SQLiteDatabase dbs = this.getReadableDatabase();
        Cursor c = dbs.rawQuery("SELECT " + ID_RAPAT + " FROM " + TABLE_RAPAT + " WHERE " + JUDUL_RAPAT + "='" + judul + "'"+ " AND "+WAKTU_RAPAT+"='"+waktu+"'", null);
        String idRapat="";
        if(c!=null&&c.moveToFirst()){
            idRapat=c.getString(0);
        }else{
            idRapat="";
        }
        c.close();
        return idRapat;
    }

    public void inputPesertaRapat(int idRapat,String namaPeserta){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID_RAPAT,idRapat);
        values.put(NAMA_PESERTA_RAPAT,namaPeserta);

        db.insert(TABLE_PESERTA_RAPAT, null, values);
        db.close();
    }

    public void inputNotulensi(int idRapat,String notulensi){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID_RAPAT,idRapat);
        values.put(NOTULENSI,notulensi);
        db.insert(TABLE_NOTULENSI, null, values);
        db.close();
    }

    public void updateStatusRapat(int idRapat){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_RAPAT + " SET " + STATUS_RAPAT + "='" + SUDAH_BERAKHIR + "'" + " WHERE " + ID_RAPAT + "='" + idRapat + "'");
    }

    public void updateStatusUpload(int idRapat){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE "+TABLE_RAPAT+" SET "+STATUS_UPLOAD+"='"+SUDAH_UPLOAD+"'"+" WHERE "+ID_RAPAT+"='"+idRapat+"'");
    }

    public ArrayList<Meeting> keteranganSemuaRapatBelumDimulai(){
        ArrayList<Meeting> arrayMeeting = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + ID_RAPAT + "," + JUDUL_RAPAT + "," + PEMIPIN_RAPAT + "," + TUJUAN_RAPAT + "," + WAKTU_RAPAT + "," + STATUS_UPLOAD + " FROM " + TABLE_RAPAT + " WHERE " + STATUS_RAPAT + "='" + BELUM_DIMULAI + "'", null);
        Meeting meeting;
        if(c.moveToFirst()){
            do{
                meeting = new Meeting();

                meeting.setIdRapat(c.getString(0));
                meeting.setJudulRapat(c.getString(1));
                meeting.setPemimpinRapat(c.getString(2));
                meeting.setTujuanRapat(c.getString(3));
                meeting.setWaktuRapat(c.getString(4));
                meeting.setStatusUpload(c.getString(5));

                arrayMeeting.add(meeting);
            }while(c.moveToNext());
        }
        return arrayMeeting;
    }

    public ArrayList<Meeting> keteranganSemuaRapatSudahBerakhir(){
        ArrayList<Meeting> arrayMeeting = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + ID_RAPAT + "," + JUDUL_RAPAT + "," + PEMIPIN_RAPAT + "," + TUJUAN_RAPAT + "," + WAKTU_RAPAT + "," + STATUS_UPLOAD + " FROM " + TABLE_RAPAT + " WHERE " + STATUS_RAPAT + "='" + SUDAH_BERAKHIR + "'", null);
        Meeting meeting;
        if(c.moveToFirst()){
            do{
                meeting = new Meeting();

                meeting.setIdRapat(c.getString(0));
                meeting.setJudulRapat(c.getString(1));
                meeting.setPemimpinRapat(c.getString(2));
                meeting.setTujuanRapat(c.getString(3));
                meeting.setWaktuRapat(c.getString(4));
                meeting.setStatusUpload(c.getString(5));

                arrayMeeting.add(meeting);
            }while(c.moveToNext());
        }
        return arrayMeeting;
    }

    public Meeting getDetailRapat(int idRapat){
        Meeting meeting = new Meeting();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT "+JUDUL_RAPAT+","+PEMIPIN_RAPAT+","+TUJUAN_RAPAT+","+WAKTU_RAPAT+","+STATUS_UPLOAD+" FROM "+TABLE_RAPAT+" WHERE "+ID_RAPAT+"='"+idRapat+"'",null);
        if(c.moveToFirst()){
            do{
                meeting.setJudulRapat(c.getString(0));
                meeting.setPemimpinRapat(c.getString(1));
                meeting.setTujuanRapat(c.getString(2));
                meeting.setWaktuRapat(c.getString(3));
                meeting.setStatusUpload(c.getString(4));
            }while (c.moveToNext());
        }
        return meeting;
    }

    public String getNotulensiRapat(int idRapat){
        String notulensiRapat;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT "+NOTULENSI+" FROM "+TABLE_NOTULENSI+" WHERE "+ID_RAPAT+"='"+idRapat+"'",null);
        if(c!=null&&c.moveToFirst()){
            notulensiRapat=c.getString(0);
        }else{
            notulensiRapat="";
        }
        return notulensiRapat;
    }


    public ArrayList<String> pesertaRapat(int idRapat){
        ArrayList<String> arrayPeserta = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT "+NAMA_PESERTA_RAPAT+" FROM "+TABLE_PESERTA_RAPAT+" WHERE "+ID_RAPAT+"='"+idRapat+"'",null);
        String namaPeserta;
        if(c.moveToFirst()){
            do{
                namaPeserta=c.getString(0);
                arrayPeserta.add(namaPeserta);
            }while (c.moveToNext());
        }
        return arrayPeserta;
    }



//    public void insertSentences(String user_name,String sentences,String title){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(USER_NAME,user_name);
//        values.put(TITLE,title);
//        values.put(SENTENCES,sentences);
//        values.put(UPLOAD_STATUS,FALSE);
//
//        db.insert(TABLE_SENTENCES, null, values);
//        db.close();
//    }

    public String selectRow (String user_name){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + SENTENCES + " FROM " + TABLE_SENTENCES + " WHERE " + USER_NAME + "='" + user_name + "'", null);
        String title ="";
        if(c!=null&&c.moveToFirst()){
            title=c.getString(0);
        }else{
            title="";
        }
        c.close();
        return title;
    }

}
