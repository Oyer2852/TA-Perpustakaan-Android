package com.example.destroyer.aplikasi_katalog_perpustakaan_smk_pgri_3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import java.util.HashMap;

public class SessionManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    int mode = 0;

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(AppVar.SHARED_PREF_NAME, mode);
        editor = pref.edit();
    }

    public void createSession(String iduser, String username, String email, String sp_id){
        editor.putBoolean(AppVar.LOGGEDIN_SHARED_PREF, true);
        editor.putString(AppVar.ID_SHARED_PREF, iduser);
        editor.putString(AppVar.USERNAME_SHARED_PREF, username);
        editor.putString(AppVar.EMAIL_SHARED_PREF, email);
        editor.putString(AppVar.IDSP, sp_id);
        editor.commit();
    }
    public void checkLogin(){
        if (!this.is_login()){
            Intent i = new Intent(context, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }else {
            Intent i = new Intent(context, MenuActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }

    private boolean is_login() {
        return pref.getBoolean(AppVar.LOGGEDIN_SHARED_PREF, false);
    }

    public void logout(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(AppVar.SHARED_PREF_NAME, pref.getString(AppVar.SHARED_PREF_NAME, null));
        user.put(AppVar.ID_SHARED_PREF, pref.getString(AppVar.ID_SHARED_PREF, null));
        user.put(AppVar.USERNAME_SHARED_PREF, pref.getString(AppVar.USERNAME_SHARED_PREF, null));
        user.put(AppVar.EMAIL_SHARED_PREF, pref.getString(AppVar.EMAIL_SHARED_PREF, null));
        user.put(AppVar.IDSP, pref.getString(AppVar.IDSP, null));

        return user;
    }

}
