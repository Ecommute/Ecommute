package com.example.ecommute;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommute.databinding.ActivityLoginBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Button loginb = findViewById(R.id.login);
        Button signupb = findViewById(R.id.botonRegistro);

        loginb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    validateLogin();
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        signupb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateSignup();
            }
        });
    }

    private void validateLogin() throws IOException, JSONException {
        EditText editUsuario = binding.usuario;
        EditText editPassword = binding.password;
        TextView aviso = binding.aviso;

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://10.4.41.35:3000/users/islogged?username="+editUsuario.getText().toString()+"&password="+editPassword.getText().toString())
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();

        JSONObject respuesta2 = new JSONObject(response.body().string());
        if(respuesta2.getString("result").equals("Success")) {
            GlobalVariables.password = editPassword.getText().toString();
            GlobalVariables.username = editUsuario.getText().toString();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);

        }else{
            aviso.setText("Las credenciales de acceso son incorrectas");
        }
    }

    private void validateSignup(){
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

}