package com.example.bahroel.crudmahasiswa.Model;

public class Mahasiswa {
    private String nrp;
    private String nama;
    private String jurusan;
    private String kelas;
    private String telp;
    private String alamat;

    public Mahasiswa(String nrp, String nama, String jurusan, String kelas, String telp, String alamat) {
        this.nrp = nrp;
        this.nama = nama;
        this.jurusan = jurusan;
        this.kelas = kelas;
        this.telp = telp;
        this.alamat = alamat;
    }

    public String getNrp() {
        return nrp;
    }

    public void setNrp(String nrp) {
        this.nrp = nrp;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}