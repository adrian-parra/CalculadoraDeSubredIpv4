package com.example.ip;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class AdapterDatos extends RecyclerView.Adapter<AdapterDatos.ViewHolder> {

        ArrayList<String> list ;
        Context context;
        int valRang;

    public AdapterDatos(ArrayList<String> list) {
        this.list = list;
        valRang = 0;
    }

    public AdapterDatos(ArrayList<String> list,int vr) {
        this.list = list;
        this.valRang = vr;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_list,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvDato.setText(list.get(position));


       holder.tvDato.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Snackbar.make(v, "RED "+((position+1)+valRang), Snackbar.LENGTH_LONG)
                       .setTextColor(v.getResources().getColor(R.color.white))
                       .setBackgroundTint(v.getResources().getColor(R.color.primaryVariant))
                       .show();
               // Toast.makeText(v.getContext(),""+(position+1),Toast.LENGTH_SHORT).show();
              // holder.tvDato.setBackgroundResource(R.color.gray);
              // holder.tvDato.setTextSize(20);
              // holder.tvDato.setTextColor(v.getResources().getColor(R.color.white));
           }
       });
         }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDato;
        RecyclerView rvRangos;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDato = (TextView) itemView.findViewById(R.id.tvDato);
            rvRangos = (RecyclerView)itemView.findViewById(R.id.rvRangos);
        }
    }
}


