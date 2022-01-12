package com.example.ecommute;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import androidx.annotation.Nullable;

public class PopUpEditComment extends Activity {

    int id = 0;
    int idArticle = 0;
    boolean liked;
    TextView content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.pop_up_editcomment);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        Button confirmar = findViewById(R.id.confirmButton);

        getWindow().setLayout((int)(width*0.8), (int)(height*0.2));

        content = findViewById(R.id.editCommentInput);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            id = Integer.valueOf(extras.getInt("id"));
            idArticle = Integer.valueOf(extras.getInt("idArticle"));
            liked = extras.getBoolean("liked");
        }

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    confirm();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }


public void confirm() throws IOException {
    final Response[] response = new Response[1];
    OkHttpClient client = new OkHttpClient().newBuilder()
            .build();
    MediaType mediaType = MediaType.parse("text/plain");
    RequestBody body = RequestBody.create("", mediaType);
    Request request = new Request.Builder()
            .url("http://10.4.41.35:3000/articles/" + idArticle + "/comment/" + id +
                    "?username=" + GlobalVariables.username + "&password=" + GlobalVariables.password + "&content=" + content.getText())
            .method("PUT", body)
            .build();
    response[0] = client.newCall(request).execute();

    //PopUpEditComment.this.finish();

    Intent intent = new Intent(PopUpEditComment.this, PopUpArticulo.class);
    intent.putExtra("id", idArticle);
    intent.putExtra("liked", liked);
    startActivity(intent);
}

}