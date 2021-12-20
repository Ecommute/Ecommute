package com.example.ecommute;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommute.databinding.PopUpArticuloBinding;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PopUpArticulo extends AppCompatActivity {

    private PopUpArticuloBinding binding;
    private int id;
    private boolean liked;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        binding = PopUpArticuloBinding.inflate(getLayoutInflater());
        setContentView(R.layout.pop_up_articulo);

        /*DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8), (int)(height*0.5));*/

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            id = Integer.valueOf(extras.getInt("id"));
            liked = extras.getBoolean("liked");
        }
        Button cerrar = findViewById(R.id.buttonCerrar);
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopUpArticulo.this.finish();
            }
        });

        try {
            setUpArticulo();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        setUpLike();



    }

    public void setUpArticulo() throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://10.4.41.35:3000/articles/" + id + "?username=" + GlobalVariables.username + "&password=" + GlobalVariables.password)
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        JSONObject respuesta = new JSONObject(response.body().string());
        JSONObject articulo = new JSONObject(respuesta.getString("article"));
        TextView titulo = findViewById(R.id.title);
        titulo.setText(articulo.getString("title"));
        TextView contenido = findViewById(R.id.content);
        contenido.setText(articulo.getString("content"));
        contenido.setMovementMethod(new ScrollingMovementMethod());
        System.out.println(articulo.getString("title"));
        TextView autor = findViewById(R.id.author);
        autor.setText(articulo.getString("author"));
        TextView numLikes = findViewById(R.id.likes);
        int mg = articulo.getInt("numLikes");
        String s = null;
        if(mg == 1) s = mg + " like";
        else s = mg + " likes";
        numLikes.setText(s);
        TextView fecha = findViewById(R.id.date);
        fecha.setText(articulo.getString("createdAt").substring(0, 10));
    }

    public void setUpLike() {
        ImageButton likedButton = findViewById(R.id.likeButton);
        TextView likes = findViewById(R.id.likes);
        String username = GlobalVariables.username;
        String password = GlobalVariables.password;
        if(liked == true){
            likedButton.setImageResource(R.drawable.ic_baseline_favorite_24);
        }
        else likedButton.setImageResource(R.drawable.ic_baseline_favorite_border_24);

        likedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = likes.getText().toString();
                String[] cosas = s.split(" ");
                int mg = Integer.valueOf(cosas[0]);

                if(liked == true){
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
                likes.setText(s);
            }
        });
    }

}
