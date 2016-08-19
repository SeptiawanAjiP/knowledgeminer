package com.bradleybossard.speech_to_textdemo.Object;

/**
 * Created by Septiawan Aji P on 8/16/2016.
 */
public class Sentences {
    private int id_sentences;
    private String timestamp;
    private String user_name;

    public int getId_sentences() {
        return id_sentences;
    }

    public void setId_sentences(int id_sentences) {
        this.id_sentences = id_sentences;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
