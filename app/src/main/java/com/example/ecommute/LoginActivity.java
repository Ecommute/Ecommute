package com.example.ecommute;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommute.databinding.ActivityLoginBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    SignInButton signInButton;
    private GoogleSignInClient mSignInClient;
    private static final int SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail().requestScopes(new Scope("https://www.googleapis.com/auth/calendar"), new Scope("https://www.googleapis.com/auth/calendar.events"))
                .build();

        mSignInClient = GoogleSignIn.getClient(this, gso);

         signInButton = findViewById(R.id.registroGoogle);
         signInButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = mSignInClient.getSignInIntent();
                 startActivityForResult(intent, SIGN_IN);
             }
         });

        Button loginb = findViewById(R.id.login);
        Button signupb = findViewById(R.id.botonRegistro);

        loginb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    validateLogin();
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        EditText editUsuario = binding.usuario;
        editUsuario.setText("marcelurpi");
        EditText editPassword = binding.password;
        editPassword.setText("password");

        signupb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateSignup();
            }
        });
    }

    private void validateLogin() throws IOException, JSONException {
        EditText editUsuario = binding.usuario;
        EditText editPassword = binding.password;
        TextView aviso = binding.aviso;

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://10.4.41.35:3000/users/islogged?username="+editUsuario.getText().toString()+"&password="+editPassword.getText().toString())
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();

        JSONObject respuesta2 = new JSONObject(response.body().string());
        if(respuesta2.getString("result").equals("Success")) {
            GlobalVariables.password = editPassword.getText().toString();
            GlobalVariables.username = editUsuario.getText().toString();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);

        }else{
            aviso.setText("Las credenciales de acceso son incorrectas");
        }
    }

    private void validateSignup(){
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SIGN_IN){
            Task<GoogleSignInAccount> task =
                    GoogleSignIn.getSignedInAccountFromIntent(data);

            if(task.isSuccessful()) {
                GoogleSignInAccount account = task.getResult();
                Toast.makeText(this, account.getEmail(), Toast.LENGTH_SHORT).show();
                Toast.makeText(this, account.getServerAuthCode(), Toast.LENGTH_LONG).show();
                Log.e("Scopes", account.getRequestedScopes().toString());
                //API
                //Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                //startActivity(intent);
                mSignInClient.signOut();
            } else{
                Toast.makeText(this, "Algo no ha salido como se esperaba!", Toast.LENGTH_SHORT).show();
            }
        }
    }

}