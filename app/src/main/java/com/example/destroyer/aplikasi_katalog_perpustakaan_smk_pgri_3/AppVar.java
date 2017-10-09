package com.example.destroyer.aplikasi_katalog_perpustakaan_smk_pgri_3;


public class AppVar {
    //URL to our login.php file, url bisa diganti sesuai dengan alamat server kita
    public static final String LOGIN_URL = "http://10.0.2.2/KAPER_SKARIGA_konek/login.php";
    public static final String URL_GET_EMP = "http://10.0.2.2/KAPER_SKARIGA_konek/tampilBuku.php?id=";
    public static final String URL_GET_EMP2 = "http://10.0.2.2/KAPER_SKARIGA_konek/tampilBuku2.php?id=";

    //Keys for email and password as defined in our $_POST['key'] in login.php
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";

    //Dibawah ini merupakan Kunci yang akan digunakan untuk mengirim permintaan ke Skrip PHP
    public static final String KEY_EMP_ID = "id";
    public static final String KEY_EMP_JUDUL = "judul_buku";
    public static final String KEY_EMP_JILID = "jilid";
    public static final String KEY_EMP_EDISI = "edisi";

    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_ID = "id";
    public static final String TAG_KOTA = "kota";
    public static final String TAG_PENERBIT = "penerbit";
    public static final String TAG_TAHUN = "tahun";
    public static final String TAG_JUDUL = "judul";
    public static final String TAG_JILID = "jilid";
    public static final String TAG_CETAKAN = "cetakan";
    public static final String TAG_EDISI = "edisi";

//    public static final String TAG_ISBN = "isbn";
//    public static final String TAG_Bahasa = "bahasa";


    //If server response is equal to this that means login is successfulo
    public static final String LOGIN_SUCCESS = "success";
    public static final String SHARED_PREF_NAME = "myloginapp";
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";
    public static final String EMAIL_SHARED_PREF = "email";
    public static final String EMP_ID = "emp_id";

}
