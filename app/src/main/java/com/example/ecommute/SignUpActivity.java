package com.example.ecommute;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommute.databinding.SignupLoginBinding;
//import com.example.ecommute.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {

    private SignupLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = SignupLoginBinding.inflate(getLayoutInflater());
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
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
    }

}