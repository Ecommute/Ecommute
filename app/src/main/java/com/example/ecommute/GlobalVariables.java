package com.example.ecommute;

public class GlobalVariables {
    public static String username, password, origen, destino;

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
}