package com.example.uas_pwpb_26;

public class Tugas {
    String tugasId;
    String tugasNama;
    String tugasDaftar;

    public Tugas(){

    }

    public Tugas(String tugasId, String tugasNama, String tugasDaftar) {
        this.tugasId = tugasId;
        this.tugasNama = tugasNama;
        this.tugasDaftar = tugasDaftar;
    }

    public String getTugasId() {
        return tugasId;
    }

    public String getTugasNama() {
        return tugasNama;
    }

    public String getTugasDaftar() {
        return tugasDaftar;
    }
}
