package com.example.ecommute;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.example.ecommute.databinding.PopUpCocheBinding;

public class PopUpCoche extends Activity {
    private PopUpCocheBinding binding;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        binding = PopUpCocheBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setContentView(R.layout.pop_up_coche);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.6), (int)(height*0.6));

        Button guardar = findViewById(R.id.guardar_coche);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editCoche = binding.nombreCoche;
                EditText editConsumo = binding.consumo;

                //API


            }
        });

    }
}
