package com.example.ecommute;

public class GlobalVariables {
    public static String username, password, origen, destino, nombre, profilepic;

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

    public String getNombre() {
        return nombre;
    }

    public String getProfilepic() {
        return profilepic;
    }
}