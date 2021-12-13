package com.example.ecommute;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

            Button comments = itemView.findViewById(R.id.comments);
            comments.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //abrir post / view de comentar?
                }
            });

            /*ImageView background = itemView.findViewById(R.id.background);
            background.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   //abrir post
                }
            });*/
        }

        public void setUpLike() {
            Button likedButton = itemView.findViewById(R.id.liked2);
            if(liked == true) likedButton.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
            else likedButton.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);

            likedButton.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {
                    if(liked == true){
                        liked = false;
                        likedButton.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
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
                        likedButton.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
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
                }
            });
        }
    }
}
