package com.example.destroyer.aplikasi_katalog_perpustakaan_smk_pgri_3;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import android.support.design.widget.Snackbar;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterList2 extends RecyclerView.Adapter<AdapterList2.ViewHolder> {

    Context context;
    ArrayList<HashMap<String, String>> list_data;

    public AdapterList2(DetailActivity detailActivity, ArrayList<HashMap<String, String>> list_data) {
        this.context = detailActivity;
        this.list_data = list_data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item2, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final HashMap<String, String> name = list_data.get(position);
/*
        Glide.with(context)
                .load("http://10.0.2.2/KAPER_SKARIGA/img/book/" + list_data.get(position).get("gambar"))
                .crossFade()
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.imgbuku);
*/
        holder.imgbuku.setVisibility(View.GONE);
        holder.txtbuku.setText(list_data.get(position).get("kode"));
        holder.txtsub.setText(list_data.get(position).get("stat"));
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent;
                switch (getAdapterPostion()) {
                    case 0:
                        intent = new Intent(context, DetailActivity2.class);
                        intent.putExtra("id_buku",list_data.get(position).get("id"));
                        intent.putExtra("id_detail_buku",list_data.get(position).get("id_detail_buku"));
                        break;
                    default:
                        intent = new Intent(context, MenuActivity.class);
                        break;
                }
                context.startActivity(intent);

            }
        });
    }
    private int getAdapterPostion() {
        return 0;
    }

    @Override
    public int getItemCount() {
        return list_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtbuku, txtsub;
        ImageView imgbuku;
        public CardView cv;

        public ViewHolder(View itemView) {
            super(itemView);
            txtbuku = (TextView) itemView.findViewById(R.id.txtjudul);
            imgbuku = (ImageView) itemView.findViewById(R.id.imgbuku1);
            txtsub = (TextView) itemView.findViewById(R.id.txtstat);
            cv = (CardView) itemView.findViewById(R.id.cardView2);
        }

    }
}
