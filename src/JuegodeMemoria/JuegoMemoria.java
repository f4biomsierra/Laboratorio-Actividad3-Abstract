/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JuegodeMemoria;

import java.util.ArrayList;
import java.util.Collections;
/**
 *
 * @author Fabio Sierra
 */
public class JuegoMemoria {
    private final int size = 36;
    private int[] tablero;
    private boolean[] cartaRevelada;
    private int puntosJ1;
    private int puntosJ2;
    private boolean turnoJ1;

    public JuegoMemoria() {
        tablero = new int [size];
        cartaRevelada = new boolean[size];
        puntosJ1 = 0;
        puntosJ2 = 0;
        turnoJ1 = false;
    }
    
    public void generarTablero(){
        ArrayList <Integer> IDlista = new ArrayList<>();
        
        for(int i=0; i<18; i++){
            int id = i%12;
            IDlista.add(id);
            IDlista.add(id);
        }
        Collections.shuffle(IDlista);
        
        for(int i=0; i<size; i++){
            tablero[i] = IDlista.get(i);
            cartaRevelada[i]=false; 
        }
    }
    
    public boolean pareja(int posicion1, int posicion2){
        if(tablero[posicion1] == tablero[posicion2]){
            cartaRevelada[posicion1]=true;
            cartaRevelada[posicion2]=true;
            
            if(turnoJ1)
                puntosJ1++;
            else puntosJ2++;
            return true;
        } else {
            turnoJ1 = !turnoJ1;
            return false;
        }
    }
    
    public boolean terminarJuego(){
        return (puntosJ1+puntosJ2) == 18; 
    }

    public int getPuntosJ1() {
        return puntosJ1;
    }

    public int getPuntosJ2() {
        return puntosJ2;
    }
    
    public int getIdEnPosicion(int posicion){ 
        return tablero[posicion]; 
    }
    
    public boolean estaRevelada(int posicion){ 
        return cartaRevelada[posicion]; 
    }
}
