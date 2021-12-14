package com.example.ecommute;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommute.databinding.ActivityEditprofilepicBinding;
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


public class EditProfilePicActivity extends AppCompatActivity {

    private @NonNull ActivityEditprofilepicBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEditprofilepicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ImageButton photo1 = binding.Asset2;

        photo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    validate("2");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        ImageButton photo2 = binding.Asset3;

        photo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    validate("3");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        ImageButton photo3 = binding.Asset4;
        photo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    validate("4");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        ImageButton photo4 = binding.Asset5;
        photo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    validate("5");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        ImageButton photo5 = binding.Asset6;
        photo5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    validate("6");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        ImageButton photo6 = binding.Asset7;
        photo6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    validate("7");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        ImageButton photo7 = binding.Asset8;
        photo7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    validate("8");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void validate(String asset) throws IOException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Log.d("llamadauseredit", "hola");
        String urlParameters  = "username="+GlobalVariables.username + "&password="+GlobalVariables.password + "&profilePic="+asset;


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

        GlobalVariables.profilepic = asset;

        Intent intent = new Intent(EditProfilePicActivity.this, PerfilFragment.class);
        startActivity(intent);

    }

}