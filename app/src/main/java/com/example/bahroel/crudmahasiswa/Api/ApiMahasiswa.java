package com.example.bahroel.crudmahasiswa.Api;

public class ApiMahasiswa {
    public static final String BASE_URL = "http://192.168.8.101/crud_mahasiswa/api/apiMahasiswa.php?apicall=";

    public static final String URL_C_MHS = BASE_URL + "create_mahasiswa";
    public static final String URL_R_MHS = BASE_URL + "get_mahasiswa";
    public static final String URL_U_MHS = BASE_URL + "update_mahasiswa";
    public static final String URL_D_MHS = BASE_URL + "delete_mahasiswa&nrp=";
}
