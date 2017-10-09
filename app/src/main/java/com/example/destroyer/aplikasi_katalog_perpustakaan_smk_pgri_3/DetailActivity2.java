package com.example.destroyer.aplikasi_katalog_perpustakaan_smk_pgri_3;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

public class DetailActivity2 extends AppCompatActivity implements View.OnClickListener{
    //Mendefinisikan variabel
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private EditText judul_buku;
    private EditText editTextId;
    private EditText jilid_buku;
    private EditText cetakan_buku;
    private EditText edisi_buku;
    private EditText bahasa_buku;
    private ImageView imgbuku;
    private RecyclerView lvhape1;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    ArrayList<HashMap<String, String>> list_data1;

    private Button pesan_buku;
    private String id, gambar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail2);

        Intent intent = getIntent();
        id = intent.getStringExtra("id_buku");
//        gambar = intent.getStringExtra("gambar_buku");
/*
        editTextId = (EditText) findViewById(R.id.editTextId);
*/
        pesan_buku = (Button) findViewById(R.id.btnpesan);
        imgbuku = (ImageView) findViewById(R.id.imgbuku);
        judul_buku  = (EditText) findViewById(R.id.judul_buku);
        jilid_buku  = (EditText) findViewById(R.id.jilid_buku);
        cetakan_buku  = (EditText) findViewById(R.id.cetakan_buku);
        edisi_buku   = (EditText) findViewById(R.id.edisi_buku);
//        bahasa_buku = (EditText) findViewById(R.id.bahasa_buku);

/*
        Glide.with(context)
                .load("http://10.0.2.2/KAPER_SKARIGA/img/book/" + gambar)
                .crossFade()
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.imgbuku);
*/

/*
        judul_buku.setText(id);
        editTextId.setText(id);
*/
        getEmployee();


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
//                    case R.id.navigation4:
//                        detail();
//                        return true;
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

        String url = "http://10.0.2.2/KAPER_SKARIGA_konek/getdata2.php?id=22"+id;
        lvhape1 = (RecyclerView) findViewById(R.id.lvhape1);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        lvhape1.setLayoutManager(llm);
        requestQueue = Volley.newRequestQueue(DetailActivity2.this);

        list_data1 = new ArrayList<HashMap<String, String>>();
        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("jb");
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("id", json.getString("id_buku"));
                        map.put("kode",json.getString("kode_buku"));
                        map.put("stat",json.getString("status_buku"));
                        map.put("gambar",json.getString("gambar_buku"));
                        list_data1.add(map);
                        AdapterList3 adapter = new AdapterList3(DetailActivity2.this, list_data1);
                        lvhape1.setAdapter(adapter);

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
                Toast.makeText(DetailActivity2.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);
    }
    private void Beranda(){
        Intent intent = new Intent(DetailActivity2.this,MenuActivity.class);
        startActivity(intent);
        finish();
    }
    private void setprofil(){
        Intent intent = new Intent(DetailActivity2.this, ProfilActivity.class);
        startActivity(intent);
        finish();
    }
    private void riwayat(){
        Intent intent = new Intent(DetailActivity2.this,RiwayatActivity.class);
        startActivity(intent);
        finish();
    }
    private void detail(){
        Intent intent = new Intent(DetailActivity2.this, DetailActivity.class);
        startActivity(intent);
        finish();
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
                        Intent intent = new Intent(DetailActivity2.this, LoginActivity.class);
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
    private void getEmployee(){
        class GetEmployee extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailActivity2.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showEmployee(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(AppVar.URL_GET_EMP2,id);
                return s;
            }
        }
        GetEmployee ge = new GetEmployee();
        ge.execute();
    }
    private void showEmployee(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(AppVar.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String id    = c.getString(AppVar.TAG_ID);
            String judul = c.getString(AppVar.TAG_JUDUL);
            String jilid = c.getString(AppVar.TAG_JILID);
            String cetak = c.getString(AppVar.TAG_CETAKAN);
            String edisi = c.getString(AppVar.TAG_EDISI);

//                String bahasa= c.getString(AppVar.TAG_Bahasa);

    /*
                editTextId.setText(id);
    */
            judul_buku.setText(judul);
            jilid_buku.setText(jilid);
            edisi_buku.setText(edisi);
            cetakan_buku.setText(cetak);
//          bahasa_buku.setText(bahasa);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {

    }

}
