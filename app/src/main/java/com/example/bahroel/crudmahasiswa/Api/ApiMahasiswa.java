package com.example.bahroel.crudmahasiswa.Api;

public class ApiMahasiswa {
    public static final String BASE_URL = "http://192.168.8.101/CRUD_Android/api/ApiMahasiswa.php?api=";

    public static final String URL_C_MHS = BASE_URL + "createMahasiswa";
    public static final String URL_R_MHS = BASE_URL + "getDataMahasiswa";
    public static final String URL_U_MHS = BASE_URL + "updateMahasiswa";
    public static final String URL_D_MHS = BASE_URL + "deleteMahasiswa&nrp=";
}
