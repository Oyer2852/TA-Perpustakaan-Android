package com.example.destroyer.aplikasi_katalog_perpustakaan_smk_pgri_3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

public class DaftarActivity extends AppCompatActivity {

    private AppCompatButton buttonbatal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
        buttonbatal = (AppCompatButton) findViewById(R.id.buttonbatal);
        buttonbatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DaftarActivity.this, LoginActivity.class);
                DaftarActivity.this.startActivity(intent);
                DaftarActivity.this.finish();
            }
        });

    }
}
