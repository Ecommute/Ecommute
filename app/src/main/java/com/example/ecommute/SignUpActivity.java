package com.example.ecommute;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommute.databinding.ActivitySignupBinding;

public class SignUpActivity extends AppCompatActivity {

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
                validate();
            }
        });
    }

    private void validate(){


        EditText pass = binding.regPassword;
        EditText email = binding.regEmail;
        EditText nombre = binding.regNombre;
        EditText apellidos = binding.regApellidos;
        Usuario usuario = new Usuario(1, nombre.toString(), pass.toString(), email.toString(), nombre.toString(),
                                        apellidos.toString());

        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
    }

}