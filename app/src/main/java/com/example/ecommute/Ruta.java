package com.example.ecommute;

public class Ruta {
    private String origen;
    private String destino;
    private int consumo;
    private int dist = 990; //Se quita y se cambia por llamada a API
    private String comparacion;

    public Ruta(String origen, String destino){
        this.origen = origen;
        this.destino = destino;
        this.consumo = dist * 143; //(143 g/km coche de gasolina medio)
        this.comparacion = getComparacio(consumo);
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

    public String getComparacion(){
        return this.comparacion;
    }

    public String getComparacio(int cons){
        String comp = "La cantidad de CO2 expulsada en este viaje equivale a: "
                + cons/200 + " botellas de plastico de 1.5L, o a "
                + cons/10 + " bolsas de plastico, y ademas se necesitarian "
                + cons/110 + " dias para que un arbol absorbiera esa cantidad de CO2";
        return comp;
    }
}
