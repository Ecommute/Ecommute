package com.example.ecommute;

public class Ruta {
    private String origen;
    private String destino;
    private int consumo;
    private int dist = 990; //Se quita y se cambia por llamada a API

    public Ruta(String origen, String destino){
        this.origen = origen;
        this.destino = destino;
        this.consumo = dist * 143; //(143 g/km coche de gasolina medio)
    }

    public String getOrigen(){
        return this.origen;
    }

    public String getDestino(){
        return this.destino;
    }

    public int getConsumo(){
        return this.consumo;
    }

    public void setOrigen(String or){
        this.origen = or;
    }

    public void setDestino(String dest){
        this.destino = dest;
    }

    public void updateRuta(String origen, String destino){
        this.origen = origen;
        this.destino = destino;
    }
}
