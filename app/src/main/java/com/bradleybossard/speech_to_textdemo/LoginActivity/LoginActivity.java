package com.bradleybossard.speech_to_textdemo.LoginActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bradleybossard.speech_to_textdemo.Connection.Connection;
import com.bradleybossard.speech_to_textdemo.Connection.ConverterParameter;
import com.bradleybossard.speech_to_textdemo.Connection.JSONParser;
import com.bradleybossard.speech_to_textdemo.MainMenu.MainMenuActivity;
import com.bradleybossard.speech_to_textdemo.Object.Account;
import com.bradleybossard.speech_to_textdemo.Object.FamiliarWord;
import com.bradleybossard.speech_to_textdemo.R;
import com.bradleybossard.speech_to_textdemo.SessionManager.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Septiawan Aji P on 8/16/2016.
 */
public class LoginActivity extends Activity {

    private EditText userName,password;
    private Button login;
    private Account akun;


    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = (EditText)findViewById(R.id.input_user_name);
        password = (EditText)findViewById(R.id.input_password);
        login = (Button)findViewById(R.id.login);

        akun = new Account();
        sessionManager = new SessionManager(getApplicationContext());

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                akun.setUser_name(userName.getText().toString());
                akun.setPassword(password.getText().toString());

                if (!akun.getUser_name().isEmpty()) {
                    if (!akun.getPassword().isEmpty()) {
                        new getStatusLogin().execute();
                    } else {
                        Toast.makeText(LoginActivity.this, "Masukan Password", Toast.LENGTH_SHORT).show();
                    }
                } else
                    Toast.makeText(LoginActivity.this, "Masukan Username", Toast.LENGTH_SHORT).show();


            }
        });
    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_login, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if(id==R.id.action_settings){
//            showDialog();
//        }
//
//        return super.onOptionsItemSelected(item);
//
//    }


    private class getStatusLogin extends AsyncTask<String,Void,String> {
        ProgressDialog pDialog;
        JSONObject json;
        JSONArray data;

        String tangkapError = "";
        String respon;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Loading");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            HashMap<String,String> parameter = new HashMap<>();
            parameter.put(Connection.getKODE(),Connection.getGetStatusLogin());
            parameter.put(FamiliarWord.getUserName(), akun.getUser_name());
            parameter.put(FamiliarWord.getPASSWORD(),akun.getPassword());

            JSONParser jsonParser = new JSONParser();

            try{
                json = jsonParser.getJSONFromUrl(Connection.getURL() + ConverterParameter.getQuery(parameter));
                respon = json.getString(Connection.getRESPON());
                Log.d("Respon", respon);
                if(!respon.equals(Connection.getNOL())){
                    parameter.remove(Connection.getKODE());
                    parameter.put(Connection.getKODE(), Connection.getGetNama());
                    json = jsonParser.getJSONFromUrl(Connection.getURL() + ConverterParameter.getQuery(parameter));
                    data = json.getJSONArray(Connection.getRESPON());
                    for(int i = 0;i<data.length();i++){
                        JSONObject c = data.getJSONObject(i);

                        akun.setName(c.getString(FamiliarWord.getNAME()));
                        akun.setId_user(c.getInt(FamiliarWord.getIdUser()));
                    }
                }

            }catch (Exception e){
                tangkapError = e.getMessage();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (tangkapError == ("")) {
                if(respon.equals("1")){
                    sessionManager.createSessionLogin(Integer.toString(akun.getId_user()), akun.getName());
//                    Toast.makeText(LoginActivity.this, user.getNama(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),MainMenuActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "Username dan Password Salah", Toast.LENGTH_SHORT).show();
                }

            }else{
                Toast.makeText(LoginActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
            pDialog.dismiss();

        }
    }
}
