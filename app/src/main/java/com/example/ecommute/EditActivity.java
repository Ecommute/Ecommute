package com.example.ecommute;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommute.databinding.ActivityEditBinding;
import com.example.ecommute.databinding.ActivityLoginBinding;
import com.example.ecommute.databinding.ActivityMainBinding;

public class EditActivity extends AppCompatActivity {

    private ActivityEditBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                //Editar Ruta mitjançant crida backend + id!
            }
        });
    }
}