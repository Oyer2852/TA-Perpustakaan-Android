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

public class AdapterList extends RecyclerView.Adapter<AdapterList.ViewHolder> {

    Context context;
    ArrayList<HashMap<String, String>> list_data;

    public AdapterList(MenuActivity menuActivity, ArrayList<HashMap<String, String>> list_data) {
        this.context = menuActivity;
        this.list_data = list_data;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final HashMap<String, String> name = list_data.get(position);
        Glide.with(context)
                .load("http://10.0.2.2/KAPER_SKARIGA/img/book/" + list_data.get(position).get("gambar"))
                .crossFade()
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.imgbuku);
        holder.txtbuku.setText(list_data.get(position).get("judul"));
//        holder.txtsub.setText(list_data.get(position).get("sub"));
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent;
                switch (getAdapterPostion()) {
                    case 0:
                        intent = new Intent(context, DetailActivity.class);
                        intent.putExtra("id_buku",list_data.get(position).get("id"));
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
        TextView txtbuku, txtsub,txtsub2;
        ImageView imgbuku;
        public CardView cv;

        public ViewHolder(View itemView) {
            super(itemView);
            txtbuku = (TextView) itemView.findViewById(R.id.txtbuku);
            imgbuku = (ImageView) itemView.findViewById(R.id.imgbuku);
//            txtsub = (TextView) itemView.findViewById(R.id.textsubyek);
            cv = (CardView) itemView.findViewById(R.id.cardView);
        }

    }
}
