package com.example.ecommute;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommute.databinding.ActivitySignupBinding;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Button signupb = findViewById(R.id.botonRegistro);

        signupb.setOnClickListener(new View.OnClickListener() {
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


        String pass = binding.regPassword.toString();
        String email = binding.regEmail.toString();
        String nombre = binding.regNombre.toString();
        String apellidos = binding.regApellidos.toString();
        String username = binding.regUsername.toString();
        //Usuario usuario = new Usuario(1, nombre.toString(), pass.toString(), email.toString(), nombre.toString(), apellidos.toString());

        // Create a neat value object to hold the URL
        //URL url = null;
            /*url = new URL("10.4.41.35:3000/users/register?name="+nombre+
                    "&surname="+apellidos+
                    "&username="+username+
                    "&password="+pass+
                    "&email="+email
                    );

             */
        /*
        url = new URL("10.4.41.35:3000/users/register");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        String jsonInputString = "{ name: "+ nombre+ ", "+
                                    "surname: "+ apellidos + ", "+
                                    "username: "+ username + ", "+
                                    "password: "+ pass + ", "+
                                    "email: "+ email +
                                "}";
        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
        }
        */
        //String urlParameters  = "name="+nombre+ "&surname="+apellidos+ "&username="+username+ "&password="+pass+ "&email="+email;
        String urlParameters  = "name=tatihard&surname=tatihard&username=tatihard&password=tatihard&email=g@gmail.com";
        byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
        int    postDataLength = postData.length;
        String request        = "10.4.41.35:3000/users/register?";
        URL    url            = new URL( request );
        HttpURLConnection conn= (HttpURLConnection) url.openConnection();
        conn.setDoOutput( true );
        conn.setInstanceFollowRedirects( false );
        conn.setRequestMethod( "POST" );
        conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty( "charset", "utf-8");
        conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
        conn.setUseCaches( false );
        try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
            wr.write( postData );
        }

        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
    }

}