package com.example.ecommute;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AdapterHistorial extends RecyclerView.Adapter<AdapterHistorial.MyViewHolder> {
    Context mContext;
    String[] mStrOrigenes, mStrDestinos;
    Integer[] mStrPuntos, mIntIds, mIntFavs;


    public AdapterHistorial(Context context, String[] strOrigenes, String[] strDestinos, Integer[] strPuntos, Integer[] intIds, Integer[] intFavs){
        mContext = context;
        mStrOrigenes = strOrigenes;
        mStrDestinos = strDestinos;
        mStrPuntos = strPuntos;
        mIntIds = intIds;
        mIntFavs = intFavs;
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
        holder.fav = mIntFavs[position];
        holder.id = mIntIds[position];

        holder.setUp();
    }

    @Override
    public int getItemCount() {
        return mStrOrigenes.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public String username = GlobalVariables.username;
        public String password = GlobalVariables.password;
        public TextView mTextOrigen, mTextDestino, mTextPuntos;
        public Integer id;
        public Integer fav = -1;
        //public boolean fav;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextOrigen = itemView.findViewById(R.id.NombreEvento);
            mTextDestino = itemView.findViewById(R.id.HoraEvento);
            mTextPuntos = itemView.findViewById(R.id.puntos);
        }

        private void setUp() {
            Log.d("DEBUG SETUP FAV", String.valueOf(fav));

            Button starB = itemView.findViewById(R.id.starButton);
            if(fav == -1) starB.setBackgroundResource(R.drawable.ic_baseline_error_24);
            if(fav == 1) starB.setBackgroundResource(R.drawable.ic_baseline_star_24);
            else starB.setBackgroundResource(R.drawable.ic_baseline_star_border_24);

            Button iButton = itemView.findViewById(R.id.item_button);
            iButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View vp) {
                    PopUpClass popUpClass = new PopUpClass();
                    popUpClass.showPopupWindow(vp, mContext, id, fav);
                }
            });

            Log.d("RID CLICK BF", String.valueOf(id));


            starB.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View vp) {

                    if(fav == 1){
                        fav = 0;
                        starB.setBackgroundResource(R.drawable.ic_baseline_star_24);

                        Log.d("RID CLICK", String.valueOf(id));
                        //add ruta to favorites
                        final Response[] response = new Response[1];

                        OkHttpClient client = new OkHttpClient().newBuilder().build();

                        MediaType mediaType = MediaType.parse("text/plain");
                        RequestBody body = RequestBody.create("", mediaType);
                        Request request = new Request.Builder()
                                .url("http://10.4.41.35:3000/routes/favourites/add/" + id + "?username=" + username + "&password=" + password)
                                .method("POST", body)
                                .build();
                        try {
                            response[0] = client.newCall(request).execute();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    else{
                        fav = 1;
                        starB.setBackgroundResource(R.drawable.ic_baseline_star_border_24);

                        //remove from favorites
                        final Response[] response2 = new Response[1];

                        OkHttpClient client = new OkHttpClient().newBuilder().build();

                        MediaType mediaType = MediaType.parse("text/plain");
                        RequestBody body = RequestBody.create("", mediaType);
                        Request request = new Request.Builder()
                                .url("http://10.4.41.35:3000/routes/favourites/remove/" + id + "?username=" + username + "&password=" + password)
                                .method("DELETE", body)
                                .build();
                        try {
                            response2[0] = client.newCall(request).execute();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

        }
    }
}
