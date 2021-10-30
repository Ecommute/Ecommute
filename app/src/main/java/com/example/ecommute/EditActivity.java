package com.example.ecommute;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommute.databinding.ActivityEditBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EditActivity extends AppCompatActivity{

    private ActivityEditBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        binding = ActivityEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Button buttonGuardar = findViewById(R.id.guardarEdit);
        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText origen = binding.editOrigen;
                EditText destino = binding.editDestino;
                Intent mIntent = getIntent();
                int idRuta = mIntent.getIntExtra("idRuta", 0);

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                final Response[] response = new Response[1];

                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();

                MediaType mediaType = MediaType.parse("text/plain");
                RequestBody body = RequestBody.create("", mediaType);
                Request request = new Request.Builder()
                        .url("http://10.4.41.35:3000/routes/edit/"+ idRuta +"?username="+ GlobalVariables.username +"&password="+ GlobalVariables.password +"&origin="+ origen.getText().toString() +"&destination="+ destino.getText().toString() +"&time=0&mode=walking&points=0&favourite=0&id=1")
                        .method("PUT", body)
                        .build();

                try {
                    response[0] = client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String jsonData = null;
                try {
                    jsonData = response[0].body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                JSONObject Jobject = null;
                try {
                    Jobject = new JSONObject(jsonData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    JSONObject Jarray = new JSONObject(Jobject.getString("result"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Intent intent = new Intent(EditActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
            }
        });
    }
}