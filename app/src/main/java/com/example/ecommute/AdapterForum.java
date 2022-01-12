package com.example.ecommute;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AdapterForum  extends RecyclerView.Adapter<AdapterForum.ViewHolder> {
    Context mContext;
    String[] mStrTitulos, mStrContenido;
    Integer[] mIntIds, mNumLikes;
    Boolean[] mBoolLiked;
    TextView textLikes;

    public AdapterForum(Context context, String[] strTitulos, String[] strContenido, Boolean[] boolLiked, Integer[] intIds, Integer[] numLikes){
        mContext = context;
        mStrTitulos = strTitulos;
        mStrContenido = strContenido;
        mBoolLiked = boolLiked;
        mIntIds = intIds;
        mNumLikes = numLikes;
    }

    @NonNull
    @Override
    public AdapterForum.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.item_forum, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterForum.ViewHolder holder, int position) {
        holder.textTitulo.setText(mStrTitulos[position]);
        holder.textContenido.setText(mStrContenido[position]);
        holder.id = mIntIds[position];
        holder.liked = mBoolLiked[position];

        Integer nlikes = mNumLikes[position];
        String s = null;
        if(nlikes == 1) s = nlikes + " like";
        else s = nlikes + " likes";
        holder.textNumLikes.setText(s);
        textLikes = holder.textNumLikes;

        holder.setUpLike();

    }

    @Override
    public int getItemCount() {
        return mStrTitulos.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public String username = GlobalVariables.username;
        public String password = GlobalVariables.password;

        public TextView textTitulo, textContenido, textNumLikes;
        public Integer id;
        public Boolean liked;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitulo = itemView.findViewById(R.id.titulo1);
            textContenido = itemView.findViewById(R.id.contenido1);
            textNumLikes = itemView.findViewById(R.id.numLikes);
            textTitulo.bringToFront();
            textContenido.bringToFront();


            ImageView background = itemView.findViewById(R.id.background);
            background.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent((Activity) mContext, PopUpArticulo.class);
                    intent.putExtra("id", id);
                    intent.putExtra("liked", liked);
                    mContext.startActivity(intent);
                }
            });
        }

        public void setUpLike() {
            ImageButton likedButton = itemView.findViewById(R.id.liked2);
            if(liked){
                likedButton.setImageResource(R.drawable.ic_baseline_favorite_24);
            }
            else likedButton.setImageResource(R.drawable.ic_baseline_favorite_border_24);

            likedButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = textNumLikes.getText().toString();
                    String[] cosas = s.split(" ");
                    int mg = Integer.parseInt(cosas[0]);

                    if(liked){
                        liked = false;
                        mg--;

                        likedButton.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                        //add ruta to favorites
                        final Response[] response = new Response[1];

                        OkHttpClient client = new OkHttpClient().newBuilder().build();

                        MediaType mediaType = MediaType.parse("text/plain");
                        RequestBody body = RequestBody.create("", mediaType);
                        Request request = new Request.Builder()
                                .url("http://10.4.41.35:3000/articles/" + id + "/unlike?username=" + username + "&password=" + password)
                                .method("PUT", body)
                                .build();
                        try {
                            response[0] = client.newCall(request).execute();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    else{
                        liked = true;
                        mg++;
                        likedButton.setImageResource(R.drawable.ic_baseline_favorite_24);
                        //remove from favorites
                        final Response[] response2 = new Response[1];

                        OkHttpClient client = new OkHttpClient().newBuilder().build();

                        MediaType mediaType = MediaType.parse("text/plain");
                        RequestBody body = RequestBody.create("", mediaType);
                        Request request = new Request.Builder()
                                .url("http://10.4.41.35:3000/articles/" + id + "/like?username=" + username + "&password=" + password)
                                .method("PUT", body)
                                .build();
                        try {
                            response2[0] = client.newCall(request).execute();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(mg == 1) s = mg + " like";
                    else s = mg + " likes";
                    textNumLikes.setText(s);
                }
            });
        }
    }
}
