package com.example.ecommute;

import static android.content.ContentValues.TAG;
import static android.webkit.ConsoleMessage.MessageLevel.LOG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

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

        Log.d("RID BINDING", String.valueOf(mIntIds[position]));
        Log.d("RID BINDING F", String.valueOf(mIntFavs[position]));
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
            mTextOrigen = itemView.findViewById(R.id.origen);
            mTextDestino = itemView.findViewById(R.id.destino);
            mTextPuntos = itemView.findViewById(R.id.puntos);

            setUp();

            /*final Response[] response = new Response[1];

            OkHttpClient client = new OkHttpClient().newBuilder().build();
            Request request = new Request.Builder()
                    .url("http://10.4.41.35:3000/routes/show/" + id + "?username=" + username + "&password=" + password)
                    .method("GET", null)
                    .build();
            try {
                response[0] = client.newCall(request).execute();
            } catch (IOException e) {

                e.printStackTrace();
            }
            Log.d("RID:", String.valueOf(id));
            String jsonData;

            fav = -1;
            try {
                jsonData = Objects.requireNonNull(response[0].body()).string();
                JSONObject jo = new JSONObject((jsonData));
                JSONObject routes = jo.getJSONObject("route");

                fav = Integer.parseInt(routes.getString("favourite"));

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }*/
        }

        private void setUp() {
            Log.d("RID FAV", String.valueOf(fav));

            Button starB = itemView.findViewById(R.id.starButton);
            if(fav == -1) starB.setBackgroundResource(R.drawable.ic_baseline_error_24);
            if(fav == 1) starB.setBackgroundResource(R.drawable.ic_baseline_star_24);
            else starB.setBackgroundResource(R.drawable.ic_baseline_star_border_24);

            Button iButton = itemView.findViewById(R.id.item_button);
            iButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View vp) {
                    PopUpClass popUpClass = new PopUpClass();
                    popUpClass.showPopupWindow(vp, mContext, id);
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
