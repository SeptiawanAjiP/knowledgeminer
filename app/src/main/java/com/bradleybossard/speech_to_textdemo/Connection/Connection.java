package com.bradleybossard.speech_to_textdemo.Connection;

/**
 * Created by Septiawan Aji P on 8/16/2016.
 */
public class Connection {
    private static final String URL = "http://datamining.hol.es/koneksi.php?";
    private static final String KODE = "kode";
    private static final String GET_STATUS_LOGIN = "getStatusLogin";
    private static final String GET_NAMA = "getNama";
    private static final String INPUT_RAPAT = "inputRapat";
    private static final String GET_ID_RAPAT = "getIdRapat";
    private static final String INPUT_PESERTA = "inputPeserta";
    private static final String RESPON = "respon";
    private static final String INPUT_NOTULENSI = "inputNotulensi";
    private static final String NOL="0";
    private static final String SATU ="1";

    public static String getURL() {
        return URL;
    }

    public static String getKODE() {
        return KODE;
    }

    public static String getGetStatusLogin() {
        return GET_STATUS_LOGIN;
    }

    public static String getGetNama() {
        return GET_NAMA;
    }

    public static String getRESPON() {
        return RESPON;
    }

    public static String getNOL() {
        return NOL;
    }

    public static String getSATU() {
        return SATU;
    }

    public static String getInputRapat() {
        return INPUT_RAPAT;
    }

    public static String getGetIdRapat() {
        return GET_ID_RAPAT;
    }

    public static String getInputPeserta() {
        return INPUT_PESERTA;
    }

    public static String getInputNotulensi() {
        return INPUT_NOTULENSI;
    }
}
