package com.example.ecommute.ui.perfil;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.ecommute.EditProfilePicActivity;
import com.example.ecommute.EditUserActivity;
import com.example.ecommute.GlobalVariables;
import com.example.ecommute.LoginActivity;
import com.example.ecommute.PopUpCoche;
import com.example.ecommute.MainActivity;
import com.example.ecommute.R;
import com.example.ecommute.SignUpActivity;
import com.example.ecommute.databinding.FragmentPerfilBinding;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PerfilFragment extends Fragment {

    private PerfilViewModel perfilViewModel;
    private FragmentPerfilBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        perfilViewModel =
                new ViewModelProvider(this).get(PerfilViewModel.class);

        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        
        /*perfilViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        TextView nombre = binding.textPerfil;
        nombre.setText((GlobalVariables.nombre));

        TextView nombre_user = binding.username;
        nombre_user.setText((GlobalVariables.username));
        String image = GlobalVariables.profilepic;
        switch (image) {
            case("@drawable/Asset_2.png"):
                binding.imageView.setImageResource(R.drawable.asset_2);
                break;
            case("@drawable/Asset_3.png"):
                binding.imageView.setImageResource(R.drawable.asset_3);
                break;
        }

        Button change_profile_pic = binding.profilepic;

        change_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditProfilePicActivity.class);
                startActivity(intent);
            }
        });

        Button logoutb = binding.logout;

        logoutb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        Button eliminarb = binding.eliminarPerfil;

        eliminarb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarPerfil();
            }
        });

        Button linkGoogle = binding.linkGoogle;
        linkGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //API
                Toast.makeText(getActivity(), "Account linked succsesfully!", Toast.LENGTH_SHORT).show();
            }
        });

        Button car = binding.car;
        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PopUpCoche.class);
                startActivity(intent);
            }
        });

        Button edit = binding.EditProfile;
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditUserActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

    private void logout(){
        GlobalVariables.password = "";
        GlobalVariables.username = "";
        GlobalVariables.nombre = "";
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }
    private void eliminarPerfil(){


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create("", mediaType);
        Request request = new Request.Builder()
                .url("http://10.4.41.35:3000/users/remove?username=" +GlobalVariables.username+"&password="+GlobalVariables.password)
                .method("DELETE", body)
                .build();
        try {
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        GlobalVariables.password = "";
        GlobalVariables.username = "";
        GlobalVariables.nombre = "";
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}