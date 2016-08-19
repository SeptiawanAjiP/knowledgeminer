package com.bradleybossard.speech_to_textdemo.SessionManager;

/**
 * Created by Septiawan Aji P on 8/16/2016.
 */
import android.content.Context;
import android.content.SharedPreferences;

import com.bradleybossard.speech_to_textdemo.Connection.Connection;
import com.bradleybossard.speech_to_textdemo.Object.FamiliarWord;

import java.util.HashMap;

public class SessionManager {

    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SessionManager(Context context){
        this.context  = context;
        sharedPreferences = context.getSharedPreferences(FamiliarWord.getSESSION(),Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void createSessionLogin (String user_name,String name){
        editor.putString(FamiliarWord.getUserName(),user_name);
        editor.putString(FamiliarWord.getNAME(),name);
        editor.commit();
    }

    public void saveUrlServer(String url){
        editor.putString(Connection.getURL(),"http://"+url+"/km/koneksi.php?");
        editor.commit();
    }

    public void deleteSession (){
        sharedPreferences.edit().clear().commit();
    }

    public HashMap<String,String> getUserSession(){
        HashMap<String,String> hm = new HashMap<>();
        hm.put(FamiliarWord.getUserName(),sharedPreferences.getString(FamiliarWord.getUserName(), null));
        hm.put(FamiliarWord.getNAME(), sharedPreferences.getString(FamiliarWord.getNAME(), null));
        return hm;
    }

    public String getAlamatServer(){
        return sharedPreferences.getString(Connection.getURL(),null);
    }
}

