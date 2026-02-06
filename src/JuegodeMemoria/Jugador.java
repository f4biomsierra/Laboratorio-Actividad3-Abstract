/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JuegodeMemoria;

/**
 *
 * @author Fabio Sierra
 */
public class Jugador {
    private String nombre;
    private int cant_aciertos;
    private long tiempoInicio;
    private long tiempoTotal; // en milisegundos
    
    public Jugador(String nombre){
        this.nombre=nombre;
        this.tiempoTotal = 0;
    }
    
    public void agregarAciertos(){
        cant_aciertos++;
    }
    
    public int getAciertos(){
        return cant_aciertos;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public void iniciarTiempo() {
        tiempoInicio = System.currentTimeMillis();
    }
    
    public void detenerTiempo() {
        if (tiempoInicio > 0) {
            tiempoTotal += (System.currentTimeMillis() - tiempoInicio);
            tiempoInicio = 0;
        }
    }
    
    public long getTiempoTotal() {
        if (tiempoInicio > 0) {
            return tiempoTotal + (System.currentTimeMillis() - tiempoInicio);
        }
        return tiempoTotal;
    }
    
    public String getTiempoFormateado() {
        long tiempo = getTiempoTotal();
        long segundos = tiempo / 1000;
        long minutos = segundos / 60;
        segundos = segundos % 60;
        return String.format("%02d:%02d", minutos, segundos);
    }
}
