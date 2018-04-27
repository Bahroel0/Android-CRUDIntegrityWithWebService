package com.example.bahroel.crudmahasiswa.Model;

public class Mahasiswa {
    private String nrp, nama, angkatan,kelas, telp, alamat;

    public Mahasiswa(String nrp, String nama, String angkatan,String kelas, String telp, String alamat) {
        this.nrp = nrp;
        this.nama = nama;
        this.angkatan = angkatan;
        this.kelas = kelas;
        this.telp = telp;
        this.alamat = alamat;
    }

    public String getNrp() { return nrp; }

    public void setNrp(String nrp) { this.nrp = nrp; }

    public String getNama() { return nama; }

    public void setNama(String nama) { this.nama = nama; }

    public String getAngkatan() { return angkatan; }

    public void setAngkatan(String angkatan) { this.angkatan = angkatan; }

    public String getKelas() { return kelas; }

    public void setKelas(String kelas) { this.kelas = kelas; }

    public String getTelp() { return telp; }

    public void setTelp(String telp) { this.telp = telp; }

    public String getAlamat() { return alamat;}

    public void setAlamat(String alamat) { this.alamat = alamat; }
}
