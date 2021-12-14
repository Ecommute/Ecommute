package com.example.ecommute;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public class GlobalVariables {
    public static String username, password, origen, destino;
    public static GoogleSignInClient mSignInClient;

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public void setdestino(String destino) {
        this.destino = destino;
    }

    public static void setClient(GoogleSignInClient client) {
        mSignInClient = client;
    }


    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getOrigen() {
        return origen;
    }

    public String getDestino() {
        return destino;
    }

    public static GoogleSignInClient getClient() {
        return mSignInClient;
    }
}