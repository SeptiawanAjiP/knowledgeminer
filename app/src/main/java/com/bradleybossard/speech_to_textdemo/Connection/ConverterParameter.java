package com.bradleybossard.speech_to_textdemo.Connection;

/**
 * Created by Septiawan Aji P on 8/18/2016.
 */
import android.util.Log;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class ConverterParameter {
    public static String getQuery(HashMap<String ,String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for(Map.Entry<String ,String > entry : params.entrySet()){
            if(first)
                first = false;
            else
                result.append("&");
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(),"UTF-8"));
        }
        Log.d("QUERY ", result.toString());
        return result.toString();
    }
}
