package com.example.ecommute;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public class GlobalVariables {
    public static GoogleSignInClient mSignInClient;
    public static String username, password, origen, destino, nombre, profilepic;
    public GlobalVariables instance;

    /**
     * Declara la clase Controlador como Singleton
     */
    private static class GlobalVariablesHelper{
        private static final GlobalVariables singletonObject = new GlobalVariables();
    }

    /**
     * Facilita el uso del singleton controladorUsuario
     * @return instancia Singleton del Controlador Usuario
     */
    public static GlobalVariables getInstance() {
        return GlobalVariablesHelper.singletonObject;
    }
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

    public void setnombre(String nombre) { this.nombre = nombre; }

    public void setprofilepic(String profilepic) { this.profilepic = profilepic; }


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

    public String getNombre() {
        return nombre;
    }

    public String getProfilepic() {
        return profilepic;
    }
}