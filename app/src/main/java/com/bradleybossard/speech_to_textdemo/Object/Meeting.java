package com.bradleybossard.speech_to_textdemo.Object;

/**
 * Created by Septiawan Aji P on 8/18/2016.
 */
public class Meeting {
    private String idRapat;
    private String judulRapat;
    private String tujuanRapat;
    private String waktuRapat;
    private String pesertaRapat;
    private String pemimpinRapat;
    private String statusRapat;
    private String statusUpload;
    private String tempatRapat;

    public Meeting(){

    }
    public Meeting(String idRapat,String judulRapat,String pemimpinRapat,String waktuRapat){
        this.idRapat=idRapat;
        this.judulRapat=judulRapat;
        this.pemimpinRapat=pemimpinRapat ;
        this.waktuRapat=waktuRapat;

    }

    public String getIdRapat() {
        return idRapat;
    }

    public void setIdRapat(String idRapat) {
        this.idRapat = idRapat;
    }

    public String getJudulRapat() {
        return judulRapat;
    }

    public void setJudulRapat(String judulRapat) {
        this.judulRapat = judulRapat;
    }

    public String getTujuanRapat() {
        return tujuanRapat;
    }

    public void setTujuanRapat(String tujuanRapat) {
        this.tujuanRapat = tujuanRapat;
    }

    public String getWaktuRapat() {
        return waktuRapat;
    }

    public void setWaktuRapat(String waktuRapat) {
        this.waktuRapat = waktuRapat;
    }

    public String getPesertaRapat() {
        return pesertaRapat;
    }

    public void setPesertaRapat(String pesertaRapat) {
        this.pesertaRapat = pesertaRapat;
    }

    public String getPemimpinRapat() {
        return pemimpinRapat;
    }

    public void setPemimpinRapat(String pemimpinRapat) {
        this.pemimpinRapat = pemimpinRapat;
    }

    public String getStatusRapat() {
        return statusRapat;
    }

    public void setStatusRapat(String statusRapat) {
        this.statusRapat = statusRapat;
    }

    public String getStatusUpload() {
        return statusUpload;
    }

    public void setStatusUpload(String statusUpload) {
        this.statusUpload = statusUpload;
    }

    public String getTempatRapat() {
        return tempatRapat;
    }

    public void setTempatRapat(String tempatRapat) {
        this.tempatRapat = tempatRapat;
    }
}
