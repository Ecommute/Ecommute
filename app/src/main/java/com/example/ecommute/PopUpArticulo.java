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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommute.databinding.PopUpArticuloBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class  PopUpArticulo extends AppCompatActivity {

    private PopUpArticuloBinding binding;
    private int id;
    private boolean liked;

    //COMMENT STUFF
    private RecyclerView comments;
    private RecyclerView.LayoutManager mLayoutManager;

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
        System.out.println("a");
        System.out.println("b");
        System.out.println("c");
        System.out.println("d");
        System.out.println("e");
        System.out.println("f");
        System.out.println("g");
        System.out.println("h");
        try {
            setUpCommentsRecycler();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setUpCommentsRecycler() throws IOException, JSONException {
        String username = GlobalVariables.username;
        String password = GlobalVariables.password;

        final Response[] response = new Response[1];
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        Request request = new Request.Builder()
                .url("http://10.4.41.35:3000/articles/" + id + "?username=" + username + "&password=" + password)
                .method("GET", null)
                .build();
        response[0] = client.newCall(request).execute();

        String jsonData = response[0].body().string();
        JSONObject Jobject = new JSONObject(jsonData);

        JSONObject info = new JSONObject(Jobject.getString("article"));
        JSONArray Jarray = info.getJSONArray("comments");
        System.out.println(Jarray);

        int n = Jarray.length();

        Integer[] arrayIds = new Integer[n];
        String[] arrayAuthors = new String[n];
        String[] arrayContents = new String[n];
        String[] arrayCreatedAts = new String[n];
        Boolean[] arrayOwns = new Boolean[n];
        Boolean[] arrayReporteds = new Boolean[n];

        for(int i = 0; i < n; i++){
            JSONObject object = Jarray.getJSONObject(i);
            arrayIds[i] = Integer.valueOf((object.getString("id")));
            arrayAuthors[i] = object.getString("author");
            arrayContents[i] = object.getString("content");
            arrayCreatedAts[i] = object.getString("createdAt");
            arrayOwns[i] = Boolean.valueOf(object.getString("own"));
            arrayReporteds[i] = Boolean.valueOf(object.getString("reported"));
        }

        //FALLA AQUI EL GET ACTIVITY I NO SE COM FERHO, SI AIXO S'ARREGLA LO ALTRE FUNCIONA
        comments = binding.commentsRV;
        AdapterComments mAdapter = new AdapterComments(this, arrayIds, arrayAuthors, arrayContents, arrayCreatedAts, arrayOwns, arrayReporteds);
        comments.setAdapter(mAdapter);

        mLayoutManager = new LinearLayoutManager(this);
        comments.setLayoutManager(mLayoutManager);
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
