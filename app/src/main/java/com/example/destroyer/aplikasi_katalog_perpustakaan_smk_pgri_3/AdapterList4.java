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

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterList4 extends RecyclerView.Adapter<AdapterList4.ViewHolder> {

    Context context;
    ArrayList<HashMap<String, String>> list_data3;

    public AdapterList4(RiwayatActivity riwayatActivity, ArrayList<HashMap<String, String>> list_data3) {
        this.context = riwayatActivity;
        this.list_data3 = list_data3;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item3, null);
        return new ViewHolder(view);
}

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final HashMap<String, String> name = list_data3.get(position);
//        Glide.with(context)
//                .load("http://10.0.2.2/KAPER_SKARIGA/img/book/" + list_data.get(position).get("gambar"))
//                .crossFade()
//                .placeholder(R.mipmap.ic_launcher)
//                .into(holder.imgbuku);
        holder.txtrwyt.setText(list_data3.get(position).get("riwayat_keg"));
        holder.tgl_txt.setText(list_data3.get(position).get("tgl_keg"));
//        holder.txtsub.setText(list_data.get(position).get("sub"));
    }
        private int getAdapterPostion() {
            return 0;
        }

    @Override
    public int getItemCount() {
        return list_data3.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtrwyt,tgl_txt;
        public CardView cv;

        public ViewHolder(View itemView) {
            super(itemView);
            txtrwyt = (TextView) itemView.findViewById(R.id.riwayattxt);
            tgl_txt = (TextView) itemView.findViewById(R.id.tgl_riwayat);
            cv = (CardView) itemView.findViewById(R.id.cardView);
        }

    }
}
