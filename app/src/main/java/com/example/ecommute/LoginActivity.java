package com.example.ecommute;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommute.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Button loginb = findViewById(R.id.login);
        Button signupb = findViewById(R.id.botonRegistro);

        loginb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateLogin();
            }
        });

        signupb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateSignup();
            }
        });
    }

    private void validateLogin(){
        //Revisar si los elementos deberían estar en otro sitio
        /*EditText editUsuario = binding.usuario;
        EditText editPassword = binding.password;
        TextView aviso = binding.aviso;

        if(editUsuario.getText().toString().equals("ecommute") && editPassword.getText().toString().equals("1234")) {*/
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        /*}else{
            aviso.setText("Los datos de acceso son incorrectos");
        }*/
    }

    private void validateSignup(){
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

}