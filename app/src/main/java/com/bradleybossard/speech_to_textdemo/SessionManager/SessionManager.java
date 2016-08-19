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

    public void createSessionLogin (String idUser,String name){
        editor.putString(FamiliarWord.getIdUser(), idUser);
        editor.putString(FamiliarWord.getNAME(),name);
        editor.commit();
    }

    public void deleteSession (){
        sharedPreferences.edit().clear().commit();
    }

    public String getNama(){
        String nama = sharedPreferences.getString(FamiliarWord.getNAME(),null);
        return nama;
    }

    public String getIdUser(){
        String idUser = sharedPreferences.getString(FamiliarWord.getIdUser(),null);
        return idUser;
    }

}

