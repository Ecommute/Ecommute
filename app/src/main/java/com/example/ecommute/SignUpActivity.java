package com.example.ecommute;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommute.databinding.ActivitySignupBinding;

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
                    binding.regApellidos.setText(e.toString());
                }
            }
        });
    }

    private void validate() throws IOException {


        String pass = binding.regPassword.getText().toString();
        String email = binding.regEmail.getText().toString();
        String nombre = binding.regNombre.getText().toString();
        String apellidos = binding.regApellidos.getText().toString();
        String username = binding.regUsername.getText().toString();
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

        /*
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

         */
        /*
            RequestBody formBody = new FormBody.Builder()
                    .add("name", "prueba")
                    .add("surname", "prueba")
                    .add("username", "prueba")
                    .add("password", "prueba")
                    .add("email", "prueba")
                    .build();

            Request request = new Request.Builder()
                    .url("https://10.4.41.35:3000/users/register")
                    .post(formBody)
                    .build();

            OkHttpClient client = new OkHttpClient();
            Call call = client.newCall(request);
            Response response = call.execute();

            //if(response.code()==200);

         */
/*
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "name=aguacate&surname=aguacate&username=aguacate&password=aguacate&email=aguacate@gmail.com");
        Request request = new Request.Builder()
                .url("http://10.4.41.35:3000/users/register?")
                .method("POST", body)
                .build();
        Response response = client.newCall(request).execute();
        //response.body().toString();

        JSONObject respuesta = null;
        try {
            respuesta = new JSONObject(response.body().string());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //binding.textView4.setText(response.body().string());

 */

        /*
        final Response[] response = new Response[1];

        OkHttpClient client = new OkHttpClient().newBuilder().build();

        Request request = new Request.Builder().url("http://10.4.41.35:3000/users/register?name=aguacate&surname=aguacate&username=aguacate&password=aguacate&email=aguacate@gmail.com")
                .method("POST", null).build();
        response[0] = client.newCall(request).execute();

        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);

         */
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String urlParameters  = "name="+nombre+ "&surname="+apellidos+ "&username="+username+ "&password="+pass+ "&email="+email;

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create("", mediaType);
        Request request = new Request.Builder()
                .url("http://10.4.41.35:3000/users/register?"+urlParameters)
                .method("POST", body)
                .build();
        final Response[] response = new Response[1];
        response[0] = client.newCall(request).execute();

        GlobalVariables.password = pass;
        GlobalVariables.username = username;

        binding.regApellidos.setText(response[0].body().string());

    }

}