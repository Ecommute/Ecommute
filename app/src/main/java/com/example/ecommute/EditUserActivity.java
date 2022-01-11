package com.example.ecommute;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Log;
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

        Button bedit = binding.edituser;

        bedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    validate();
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void validate() throws IOException, JSONException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String newpass = binding.regPassword.getText().toString();
        String email = binding.regEmail.getText().toString();
        String nombre = binding.regNombre.getText().toString();
        String apellidos = binding.regApellidos.getText().toString();
        String newusername = binding.regUsername.getText().toString();
        String fuel_cons = binding.fuelConsumption.getText().toString(); //creo que tiene que ser un int
        String fuel_type = binding.TipoCombustible.getText().toString(); //creo que tiene que ser un int
        boolean new_user = false;

        if (newusername != null) {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url("http://10.4.41.35:3000/users/islogged?username="+newusername+"&password="+GlobalVariables.password)
                    .method("GET", null)
                    .build();
            Response response = client.newCall(request).execute();

            JSONObject respuesta2 = new JSONObject(response.body().string());

            if (respuesta2.getString("result").equals("Success")) {
                binding.aviso.setText("Este nombre de usuario ya existe");
            }

            else new_user = true;
        }


        Log.d("llamadauseredit", "hola");
        String urlParameters  = "username="+GlobalVariables.username + "&password="+GlobalVariables.password;

        if(!newpass.equals("")) urlParameters += "&newPassword="+newpass;
        if(!email.equals("")) urlParameters += "&email="+email;
        if(!nombre.equals("")) {
            urlParameters += "&name="+nombre;
            GlobalVariables.nombre = nombre;
        }
        if(!apellidos.equals("")) urlParameters += "&surname="+apellidos;
        if(!newusername.equals("") && new_user) {
            urlParameters += "&newUsername="+newusername;
            GlobalVariables.username = newusername;
        }
        if(!fuel_cons.equals("")) urlParameters += "&fuelConsumption="+ fuel_cons;
        if(!fuel_type.equals("")) urlParameters += "&fuelType=" + fuel_type;
        Log.d("llamadauseredit", urlParameters);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create("", mediaType);
        Request request = new Request.Builder()
                .url("http://10.4.41.35:3000/users/edit?" + urlParameters)
                .method("POST", body)
                .build();
        Response response = client.newCall(request).execute();
        Log.d("llamadauseredit", response.toString());
        if(new_user) {
            Intent intent = new Intent(EditUserActivity.this, MainActivity.class);
            intent.putExtra("page", 5);
            startActivity(intent);
        }
    }

}