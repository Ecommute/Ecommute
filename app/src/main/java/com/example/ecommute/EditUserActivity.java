package com.example.ecommute;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommute.databinding.ActivityEdituserBinding;
import com.example.ecommute.databinding.ActivitySignupBinding;
import com.example.ecommute.ui.perfil.PerfilFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class EditUserActivity extends AppCompatActivity {

    private @NonNull ActivityEdituserBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEdituserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Button bedit = findViewById(R.id.edituser);

        bedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    validate();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void validate() throws IOException {


        String newpass = binding.regPassword.getText().toString();
        String email = binding.regEmail.getText().toString();
        String nombre = binding.regNombre.getText().toString();
        String apellidos = binding.regApellidos.getText().toString();
        String newusername = binding.regUsername.getText().toString();
        String fuel_cons = binding.fuelConsumption.getText().toString(); //creo que tiene que ser un int
        String fuel_type = binding.TipoCombustible.getText().toString(); //creo que tiene que ser un int
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String urlParameters  = "username="+GlobalVariables.username + "password="+GlobalVariables.password;

        if(newpass != "") urlParameters += "&newpassword="+newpass;
        if(email != "") urlParameters += "&email="+email;
        if(nombre != "") urlParameters += "name="+nombre;
        if(apellidos != "") urlParameters += "&surname="+apellidos;
        if(newusername != "") urlParameters += "&newusername="+newusername;
        if(fuel_cons != "") urlParameters += "&fuelConsumption="+ fuel_cons;
        if(fuel_type != "") urlParameters += "&fuelType=" + fuel_type;

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("10.4.41.35:3000/users/edit?" + urlParameters)
                .method("POST", body)
                .build();
        Response response = client.newCall(request).execute();


        Intent intent = new Intent(EditUserActivity.this, PerfilFragment.class);
        startActivity(intent);

    }

}