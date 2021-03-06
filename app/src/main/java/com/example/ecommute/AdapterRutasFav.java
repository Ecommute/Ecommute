package com.example.ecommute;

import static com.example.ecommute.R.drawable.*;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommute.R.drawable;
import com.example.ecommute.ui.ruta.RutaFragment;

public class AdapterRutasFav extends RecyclerView.Adapter<AdapterRutasFav.ViewHolderRF> {
    Context mContext;
    String[] mStrOrigenes, mStrDestinos;
    Integer[] mIntIds;

    public AdapterRutasFav(Context context, String[] strOrigenes, String[] strDestinos, Integer[] intIds){
        mContext = context;
        mStrOrigenes = strOrigenes;
        mStrDestinos = strDestinos;
        mIntIds = intIds;
    }

    @NonNull
    @Override
    public AdapterRutasFav.ViewHolderRF onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.item_rutasfavs, parent, false);

        return new ViewHolderRF(v, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRutasFav.ViewHolderRF holder, int position) {
        holder.id = (mIntIds[position]);
        holder.mTextOrigen.setText(mStrOrigenes[position]);
        holder.mTextDestino.setText(mStrDestinos[position]);
        holder.origen = mStrOrigenes[position];
        holder.destino = mStrDestinos[position];
    }

    @Override
    public int getItemCount() {
        if(mStrOrigenes != null) return mStrOrigenes.length;
        else return 0;
    }

    public class ViewHolderRF extends RecyclerView.ViewHolder {
        TextView mTextOrigen, mTextDestino;
        Integer id;
        String origen, destino;


        public ViewHolderRF(@NonNull View itemView, @NonNull View parent) {
            super(itemView);
            mTextOrigen = itemView.findViewById(R.id.origen2);
            mTextDestino = itemView.findViewById(R.id.destino2);

            Button starB = itemView.findViewById(R.id.item_button2);
            starB.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View vp) {
                    RutaFragment.setRutaFromFavs(origen, destino);
                }
            });
        }
    }
}
