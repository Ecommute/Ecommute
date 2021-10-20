package com.example.ecommute;

public class Ruta {
    private String origen;
    private String destino;
    private int consumo;
    private int dist = 990; //Se quita y se cambia por llamada a API
    private String comparacion;

    public Ruta(){

    }

    public Ruta(String origen, String destino){
        this.origen = origen;
        this.destino = destino;

        //Posibilidad de guardar la hora / distancia (el segundo necesita API o algo por el estilo)
        this.consumo = dist * 143; //(143 g/km coche de gasolina medio)
        this.comparacion = comparar(consumo);
    }

    public String getOrigen(){
        return this.origen;
    }

    public String getDestino(){
        return this.destino;
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

    public int getConsumo(){
        return this.consumo;
    }

    public String getComparacion(){
        return this.comparacion;
    }

    public String comparar(int cons){
        String comp = "La cantidad de CO2 expulsada en este viaje equivale a: "
                + cons/200 + " botellas de plastico de 1.5L, o a "
                + cons/10 + " bolsas de plastico, y ademas se necesitarian "
                + cons/110 + " dias para que un arbol absorbiera esa cantidad de CO2";
        return comp;
    }
}
