package com.example.ecommute;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommute.databinding.ActivitySignupBinding;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class SignUpActivity extends AppCompatActivity{

    private ActivitySignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Button signupb = findViewById(R.id.botonRegistro);

        signupb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    validate();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Button signupG = findViewById(R.id.botonRegistro);
    }

    private void validate() throws IOException {


        String pass = binding.regPassword.getText().toString();
        String email = binding.regEmail.getText().toString();
        String nombre = binding.regNombre.getText().toString();
        String apellidos = binding.regApellidos.getText().toString();
        String username = binding.regUsername.getText().toString();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String urlParameters  = "name="+nombre+ "&surname="+apellidos+ "&username="+username+ "&password="+pass+ "&email="+email;

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create("", mediaType);
        Request request = new Request.Builder()
                .url("http://10.4.41.35:3000/users/register?"+urlParameters)
                .method("POST", body)
                .build();
        final Response[] response = new Response[1];
        response[0] = client.newCall(request).execute();

        GlobalVariables.password = pass;
        GlobalVariables.username = username;
        GlobalVariables.nombre = nombre;
        GlobalVariables.profilepic = "3";

        //binding.regApellidos.setText(response[0].body().string());

        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);

    }


}