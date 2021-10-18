package com.example.ecommute;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterHistorial extends RecyclerView.Adapter<AdapterHistorial.MyViewHolder> {
    Context mContext;
    String[] mStrOrigenes, mStrDestinos, mStrPuntos;

    public AdapterHistorial(Context context, String[] strOrigenes, String[] strDestinos, String[] strPuntos){
        mContext = context;
        mStrOrigenes = strOrigenes;
        mStrDestinos = strDestinos;
        mStrPuntos = strPuntos;
    }

    @NonNull
    @Override
    public AdapterHistorial.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.item_historial, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHistorial.MyViewHolder holder, int position) {
        holder.mTextOrigen.setText(mStrOrigenes[position]);
        holder.mTextDestino.setText(mStrDestinos[position]);
        holder.mTextPuntos.setText(new StringBuilder().append(mStrPuntos[position]).append(" kg de CO2").toString());
    }

    @Override
    public int getItemCount() {
        return mStrOrigenes.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTextOrigen, mTextDestino, mTextPuntos;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextOrigen = itemView.findViewById(R.id.origen);
            mTextDestino = itemView.findViewById(R.id.destino);
            mTextPuntos = itemView.findViewById(R.id.puntos);
        }
    }
}
