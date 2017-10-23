package com.example.destroyer.aplikasi_katalog_perpustakaan_smk_pgri_3;

import android.content.Context;
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

/**
 * Created by RkO on 15/10/2017.
 */

public class AdapterList5 extends RecyclerView.Adapter<AdapterList5.ViewHolder> {

    Context context;
    ArrayList<HashMap<String, String>> list_data4;

    public AdapterList5(PinjamActivity pinjamActivity, ArrayList<HashMap<String, String>> list_data4) {
        this.context = pinjamActivity;
        this.list_data4 = list_data4;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item4, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final HashMap<String, String> name = list_data4.get(position);
        Glide.with(context)
                .load("http://10.0.2.2/KAPER_SKARIGA/img/book/" + list_data4.get(position).get("gambar"))
                .crossFade()
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.imgpinjam22);
        holder.judultxt1.setText(list_data4.get(position).get("judul"));
        holder.stat_pin.setText(list_data4.get(position).get("status_pinjam"));
    }

    private int getAdapterPostion() {
        return 0;
    }

    @Override
    public int getItemCount() {
        return list_data4.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView judultxt1,stat_pin;
        ImageView imgpinjam22;
        public CardView cv;

        public ViewHolder(View itemView) {
            super(itemView);
            imgpinjam22 = (ImageView) itemView.findViewById(R.id.imgbuku4);
            judultxt1 = (TextView) itemView.findViewById(R.id.textBuku_pinjam);
            stat_pin = (TextView) itemView.findViewById(R.id.text_Status);
            cv = (CardView) itemView.findViewById(R.id.cardView4);
        }

    }
}
