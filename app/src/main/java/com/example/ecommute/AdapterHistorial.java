package com.example.ecommute;

import static com.example.ecommute.R.drawable.ic_baseline_star_24;
import static com.example.ecommute.R.drawable.ic_baseline_star_border_24;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.ecommute.databinding.ItemHistorialBinding;

public class AdapterHistorial extends RecyclerView.Adapter<AdapterHistorial.MyViewHolder> {
    Context mContext;
    String[] mStrOrigenes, mStrDestinos;
    Integer[] mStrPuntos, mIntIds;
    private ItemHistorialBinding bindingI;

    public AdapterHistorial(Context context, String[] strOrigenes, String[] strDestinos, Integer[] strPuntos, Integer[] intIds){
        mContext = context;
        mStrOrigenes = strOrigenes;
        mStrDestinos = strDestinos;
        mStrPuntos = strPuntos;
        mIntIds = intIds;
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
        holder.setId(mIntIds[position]);
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
        Integer id;
        boolean fav = false;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextOrigen = itemView.findViewById(R.id.origen);
            mTextDestino = itemView.findViewById(R.id.destino);
            mTextPuntos = itemView.findViewById(R.id.puntos);

            Button iButton = itemView.findViewById(R.id.item_button);
            iButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View vp) {

                    PopUpClass popUpClass = new PopUpClass();
                    popUpClass.showPopupWindow(vp, mContext, id);
                }
            });

            Button starB = itemView.findViewById(R.id.starButton);
            starB.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View vp) {
                    if(!fav){
                        fav = true;
                        starB.setBackgroundResource(ic_baseline_star_24);
                        //add ruta to favorites
                    }
                    else{
                        fav = false;
                        starB.setBackgroundResource(ic_baseline_star_border_24);
                        //remove from favorites
                    }

                }
            });



        }

        public void setId(Integer id) {
            this.id = id;
        }
    }
}
