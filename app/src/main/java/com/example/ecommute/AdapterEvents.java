package com.example.ecommute;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterEvents extends RecyclerView.Adapter<AdapterEvents.ViewHolder> {

    Context mContext;
    String[] mstrTitulos;
    String[] mstrLocalitzacio;

    public AdapterEvents(Context context, String[] strTitulos, String[] strLocalitzacio){
        mContext = context;
        mstrTitulos = strTitulos;
        mstrLocalitzacio = strLocalitzacio;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.item_evento, parent, false);
        return new AdapterEvents.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textTitulo.setText(mstrTitulos[position]);

        holder.textLocalització.setText(mstrLocalitzacio[position]);

    }

    @Override
    public int getItemCount() {
        return mstrTitulos.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textTitulo;
        public TextView textLocalització;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textTitulo = itemView.findViewById(R.id.NombreEvento);
            textLocalització = itemView.findViewById(R.id.Localitzacio);


        }
    }
}
