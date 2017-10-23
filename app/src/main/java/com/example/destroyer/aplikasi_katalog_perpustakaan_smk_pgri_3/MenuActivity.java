package com.example.destroyer.aplikasi_katalog_perpustakaan_smk_pgri_3;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MenuActivity extends AppCompatActivity {

    MaterialSearchView searchView;
    SessionManager sessionManager;
    private TextView txtprofil;
    private String username;
    private TextView nametxt;
    private TextView emailtxt;
    private String idUSP;
    private RecyclerView lvhape;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    ArrayList<HashMap<String, String>> list_data;
    private int position;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));

        searchView = (MaterialSearchView) findViewById(R.id.sv);
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener(){
            public boolean onQueryTextSubmit(String query){
                return false;
            }
            public boolean onQueryTextChange(String search){
                if(search != null && !search.isEmpty()){
                    String url = "http://10.0.2.2/KAPER_SKARIGA_konek/getdata.php?id="+search;

                    lvhape = (RecyclerView) findViewById(R.id.lvhape);
                    LinearLayoutManager llm = new LinearLayoutManager(MenuActivity.this);
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    lvhape.setLayoutManager(llm);
                    requestQueue = Volley.newRequestQueue(MenuActivity.this);
                    list_data = new ArrayList<HashMap<String, String>>();
                    stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("response", response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("buku_buku");
                                for (int a = 0; a < jsonArray.length(); a++) {
                                    JSONObject json = jsonArray.getJSONObject(a);
                                    HashMap<String, String> map = new HashMap<String, String>();
                                    map.put("id", json.getString("id_buku"));
                                    map.put("judul", json.getString("judul_buku"));
                                    map.put("gambar", json.getString("gambar_buku"));
                                    map.put("sub",json.getString("subyek"));
                                    map.put("stat",json.getString("status_buku"));
                                    list_data.add(map);
                                    AdapterList adapter = new AdapterList(MenuActivity.this, list_data);
                                    lvhape.setAdapter(adapter);
//                        Toast.makeText(MenuActivity.this, "oyi", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
//                    Toast.makeText(MenuActivity.this, "nop", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MenuActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    requestQueue.add(stringRequest);
                }
                else{
                    reload_data();
                }
                return true;
            }
        });




        reload_data();
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.refresh1,R.color.refresh2,R.color.refresh3);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        reload_data();
                    }
                },500);
            }
        });


        txtprofil = (TextView)findViewById(R.id.txtprofil);
        // Menginisiasi Toolbar dan mensetting sebagai actionbar
/*        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/


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

        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetails();
//        id_lala = user.get(AppVar.ID_SHARED_PREF);
//        email = user.get(AppVar.EMAIL_SHARED_PREF);
        username = user.get(AppVar.USERNAME_SHARED_PREF);
        idUSP = user.get(AppVar.IDSP);


        txtprofil.setText("Selamat Datang "+ username +" di Aplikasi Katalog");

    }
    public void reload_data(){
        String url = "http://10.0.2.2/KAPER_SKARIGA_konek/getdata.php";
        lvhape = (RecyclerView) findViewById(R.id.lvhape);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        lvhape.setLayoutManager(llm);
        requestQueue = Volley.newRequestQueue(MenuActivity.this);
        list_data = new ArrayList<HashMap<String, String>>();
        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("buku_buku");
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("id", json.getString("id_buku"));
                        map.put("judul", json.getString("judul_buku"));
                        map.put("gambar", json.getString("gambar_buku"));
                        map.put("sub",json.getString("subyek"));
                        map.put("stat",json.getString("status_buku"));
                        list_data.add(map);
                        AdapterList adapter = new AdapterList(MenuActivity.this, list_data);
                        lvhape.setAdapter(adapter);
//                        Toast.makeText(MenuActivity.this, "oyi", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
//                    Toast.makeText(MenuActivity.this, "nop", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MenuActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);
    }
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_item,menu);
        MenuItem item = menu.findItem(R.id.action_bar);
        searchView.setMenuItem(item);
        return  true;
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
                        Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
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
        Intent intent = new Intent(MenuActivity.this,MenuActivity.class);
        startActivity(intent);
        finish();
    }
    private void setprofil(){
        Intent viewIntent = new Intent(MenuActivity.this,Profil.class);
        startActivity(viewIntent);

    }
    private void riwayat(){
        Intent intent = new Intent(MenuActivity.this, RiwayatActivity.class);
        startActivity(intent);
        finish();
    }
    private void Pesan(){
        Intent intent = new Intent(MenuActivity.this, PinjamActivity.class);
        startActivity(intent);
        finish();
    }



}
