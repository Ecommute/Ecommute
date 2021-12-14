package com.example.ecommute.ui.perfil;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.ecommute.EditUserActivity;
import com.example.ecommute.GlobalVariables;
import com.example.ecommute.LoginActivity;
import com.example.ecommute.PopUpCoche;
import com.example.ecommute.R;
import com.example.ecommute.databinding.FragmentPerfilBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PerfilFragment extends Fragment {

    private PerfilViewModel perfilViewModel;
    private FragmentPerfilBinding binding;
    SignInButton signInButton;
    private static final int SIGN_IN = 1;

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

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id)).requestServerAuthCode(getString(R.string.server_client_id))
                .requestEmail().requestScopes(new Scope("https://www.googleapis.com/auth/calendar"), new Scope("https://www.googleapis.com/auth/calendar.events"))
                .build();

        GlobalVariables.setClient(GoogleSignIn.getClient(getActivity(), gso));

        signInButton = binding.linkGoogle;
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = GlobalVariables.getClient().getSignInIntent();
                startActivityForResult(intent, SIGN_IN);
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

        Button car = binding.car;
        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PopUpCoche.class);
                startActivity(intent);
            }
        });

        Button editb = binding.EditProfile;

        editb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateedit();
            }
        });

        return root;
    }

    private void logout(){
        GlobalVariables.password = "";
        GlobalVariables.username = "";
        GlobalVariables.getClient().signOut();
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
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    private void validateedit(){
        Intent intent = new Intent(getActivity(), EditUserActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SIGN_IN){
            Task<GoogleSignInAccount> task =
                    GoogleSignIn.getSignedInAccountFromIntent(data);

            if(task.isSuccessful()) {
                GoogleSignInAccount account = task.getResult();
                //API
                JSONObject comprobacion = new JSONObject();

                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                Request request = new Request.Builder()
                        .url("http://10.4.41.35:3000/users/googleSync?username=" + GlobalVariables.username
                                + "&password=" + GlobalVariables.password + "&code=" + account.getIdToken())
                        .method("GET", null)
                        .build();
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    comprobacion = new JSONObject(response.body().string());
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
                try {
                    if(comprobacion.getString("result").equals("Success")) {
                        Toast.makeText(getActivity(), "Cuenta linkeada con exito!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(), "Algo no ha salido como se esperaba!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else{
                Toast.makeText(getActivity(), "Algo no ha salido como se esperaba!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}