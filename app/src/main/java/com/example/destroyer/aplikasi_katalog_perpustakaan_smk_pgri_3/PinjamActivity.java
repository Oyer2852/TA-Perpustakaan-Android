package com.example.destroyer.aplikasi_katalog_perpustakaan_smk_pgri_3;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class PinjamActivity extends AppCompatActivity {
    //Mendefinisikan variabel
    private Toolbar toolbar;
    private RecyclerView lvhapee4;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private StringRequest stringRequest;
    private RequestQueue requestQueue;
    ArrayList<HashMap<String, String>> list_data4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinjam);

        SessionManager sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetails();
        String id_user1 = user.get(AppVar.ID_SHARED_PREF);

        // Menginisiasi Toolbar dan mensetting sebagai actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Menginisiasi  NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        //Mengatur Navigasi View Item yang akan dipanggil untuk menangani item klik menu navigasi
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                //Memeriksa apakah item tersebut dalam keadaan dicek  atau tidak,
                if(menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);
                //Menutup  drawer item klik
                drawerLayout.closeDrawers();
                //Memeriksa untuk melihat item yang akan dilklik dan melalukan aksi
                switch (menuItem.getItemId()){
                    // pilihan menu item navigasi akan menampilkan pesan toast klik kalian bisa menggantinya
                    //dengan intent activity
                    case R.id.navigation1:
                        Beranda();
                        return true;
                    case R.id.navigation2:
                        setprofil();
                        return true;
                    case R.id.navigation3:
                        riwayat();
                        return true;
                    case R.id.navigation4:
                        Pesan();
                        return true;
                    case R.id.navigation5:
                        logout();
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(),"Kesalahan Terjadi ",Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });

        // Menginisasi Drawer Layout dan ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){
            @Override
            public void onDrawerClosed(View drawerView) {
                // Kode di sini akan merespons setelah drawer menutup disini kita biarkan kosong
                super.onDrawerClosed(drawerView);
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                //  Kode di sini akan merespons setelah drawer terbuka disini kita biarkan kosong
                super.onDrawerOpened(drawerView);
            }
        };
        //Mensetting actionbarToggle untuk drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        //memanggil synstate
        actionBarDrawerToggle.syncState();

        String url = "http://10.0.2.2/KAPER_SKARIGA_konek/getdata4.php?id="+id_user1;
        lvhapee4 = (RecyclerView) findViewById(R.id.lvhape4);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        lvhapee4.setLayoutManager(llm);
        requestQueue = Volley.newRequestQueue(PinjamActivity.this);
        list_data4 = new ArrayList<HashMap<String, String>>();
        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("pmnjm");
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("id", json.getString("id_user"));
                        map.put("id2", json.getString("id_peminjaman"));
                        map.put("id3", json.getString("id_detail_buku"));
                        map.put("gambar", json.getString("gambar_buku"));
                        map.put("judul",json.getString("judul_buku"));
                        map.put("status_pinjam",json.getString("status_pinjaman"));
                        list_data4.add(map);
                        AdapterList5 adapter5 = new AdapterList5(PinjamActivity.this, list_data4);
                        lvhapee4.setAdapter(adapter5);
//                        Toast.makeText(PinjamActivity.this, "oyi", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
//                    Toast.makeText(MenuActivity.this, "lala", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PinjamActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);
    }
    private void logout() {
        //Creating an alert dialog to confirm logout
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Yakin Keluar?");
        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        //Getting out sharedpreferences
                        SharedPreferences preferences = getSharedPreferences(AppVar.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        //Getting editor
                        SharedPreferences.Editor editor = preferences.edit();

                        //Puting the value false for loggedin
                        editor.putBoolean(AppVar.LOGGEDIN_SHARED_PREF, false);

                        //Putting blank value to email
                        editor.putString(AppVar.EMAIL_SHARED_PREF, "");

                        //Saving the sharedpreferences
                        editor.commit();


                        //Starting login activity
                        Intent intent = new Intent(PinjamActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

        alertDialogBuilder.setNegativeButton("Tidak",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
    private void Beranda(){
        Intent intent = new Intent(PinjamActivity.this,MenuActivity.class);
        startActivity(intent);
        finish();
    }
    private void setprofil(){
        Intent intent = new Intent(PinjamActivity.this, BuktiPesan.class);
        startActivity(intent);
        finish();
    }
    private void riwayat(){
        Intent intent = new Intent(PinjamActivity.this,RiwayatActivity.class);
        startActivity(intent);
        finish();
    }

    private void Pesan(){
        Intent intent = new Intent(PinjamActivity.this, PinjamActivity.class);
        startActivity(intent);
        finish();
    }
}
