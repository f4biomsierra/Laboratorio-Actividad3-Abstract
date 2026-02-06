/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JuegodeMemoria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
/**
 *
 * @author Fabio Sierra
 */
public class JuegoMemoria {
    private final int filas = 6;
    private final int columnas = 6;
    private final int totalParejas = (filas * columnas) / 2;
    private final List<String> nombresImagenes;
    private String[][] tablero;
    private boolean[][] cartaRevelada;
    private int puntosJ1;
    private int puntosJ2;
    private boolean turnoJ1;

    public JuegoMemoria() {
        nombresImagenes = new ArrayList<>(Arrays.asList(
                "caterpie.png",
                "charmander.png",
                "chespin.png",
                "ditto.png",
                "dreepy.png",
                "eevee.png",
                "greninja.png",
                "jigglypuff.png",
                "lucario.png",
                "magikarp.png",
                "moltres.png",
                "pikachu.png",
                "psyduck.png",
                "rayquaza.png",
                "riolu.png",
                "togekiss.png",
                "umbreon.png",
                "vaporeon.png"
        ));
        tablero = new String[filas][columnas];
        cartaRevelada = new boolean[filas][columnas];
        puntosJ1 = 0;
        puntosJ2 = 0;
        turnoJ1 = false;
    }
    
    public void generarTablero(){
        ArrayList<String> parejas = new ArrayList<>();

        for (String nombre : nombresImagenes) {
            parejas.add(nombre);
            parejas.add(nombre);
        }
        Collections.shuffle(parejas);

        int index = 0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                tablero[i][j] = parejas.get(index++);
                cartaRevelada[i][j] = false;
            }
        }
    }
    
    public boolean pareja(int fila1, int columna1, int fila2, int columna2){
        if(tablero[fila1][columna1].equals(tablero[fila2][columna2])){
            cartaRevelada[fila1][columna1]=true;
            cartaRevelada[fila2][columna2]=true;
            
            if(turnoJ1)
                puntosJ1++;
            else puntosJ2++;
            return true;
        } else {
            turnoJ1 = !turnoJ1;
            return false;
        }
    }

    public boolean pareja(int posicion1, int posicion2){
        int fila1 = posicion1 / columnas;
        int columna1 = posicion1 % columnas;
        int fila2 = posicion2 / columnas;
        int columna2 = posicion2 % columnas;
        return pareja(fila1, columna1, fila2, columna2);
    }
    
    public boolean terminarJuego(){
        return (puntosJ1+puntosJ2) == totalParejas; 
    }

    public int getPuntosJ1() {
        return puntosJ1;
    }

    public int getPuntosJ2() {
        return puntosJ2;
    }
    
    public String getNombreImagenEnPosicion(int fila, int columna){
        return tablero[fila][columna];
    }

    public String getNombreImagenEnPosicion(int posicion){
        int fila = posicion / columnas;
        int columna = posicion % columnas;
        return getNombreImagenEnPosicion(fila, columna);
    }

    public int getIdEnPosicion(int posicion){ 
        String nombre = getNombreImagenEnPosicion(posicion);
        return nombresImagenes.indexOf(nombre);
    }
    
    public boolean estaRevelada(int fila, int columna){ 
        return cartaRevelada[fila][columna]; 
    }

    public boolean estaRevelada(int posicion){ 
        int fila = posicion / columnas;
        int columna = posicion % columnas;
        return estaRevelada(fila, columna); 
    }

    public List<String> getNombresImagenes(){
        return Collections.unmodifiableList(nombresImagenes);
    }
}
