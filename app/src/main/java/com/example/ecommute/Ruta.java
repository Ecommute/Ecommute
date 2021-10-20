package com.example.ecommute;

public class Ruta {
    private String origen;
    private String destino;

    public Ruta(String origen, String destino){
        this.origen = this.origen;
        this.destino = destino;

        //Posibilidad de guardar la hora / distancia (el segundo necesita API o algo por el estilo)
    }

    public String getOrigen(){
        return this.origen;
    }

    public String getDestino(){
        return this.destino;
    }
}
