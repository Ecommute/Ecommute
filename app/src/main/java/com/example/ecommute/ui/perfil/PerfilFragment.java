package com.example.ecommute.ui.perfil;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import okhttp3.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.ecommute.GlobalVariables;
import com.example.ecommute.LoginActivity;
import com.example.ecommute.MainActivity;
import com.example.ecommute.R;
import com.example.ecommute.databinding.FragmentPerfilBinding;

import java.io.IOException;

public class PerfilFragment extends Fragment {

    private PerfilViewModel perfilViewModel;
    private FragmentPerfilBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        perfilViewModel =
                new ViewModelProvider(this).get(PerfilViewModel.class);

        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textPerfil;
        perfilViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
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

        return root;
    }

    private void logout(){
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);


    }
    private void eliminarPerfil(){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create("", mediaType);
        Request request = new Request.Builder()
                .url("10.4.41.35:3000/users/remove?username=" +GlobalVariables.username+"&password="+GlobalVariables.password)
                .method("DELETE", body)
                .build();
        try {
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}