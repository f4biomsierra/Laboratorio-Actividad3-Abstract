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
    
    public Jugador(String nombre){
        this.nombre=nombre;
    }
    
    public void agregarAciertos(){
        cant_aciertos++;
    }
}
