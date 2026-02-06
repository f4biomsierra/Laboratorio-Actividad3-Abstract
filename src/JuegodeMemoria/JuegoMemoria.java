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


public class JuegoMemoria implements ControlesJuego, LogicaJuego{
    private final int filas = 6;
    private final int columnas = 6;
    private final int totalParejas = (filas * columnas) / 2;
    private final List<String> nombresImagenes;
    private String[][] tablero;
    private boolean[][] cartaRevelada;
    private int puntosJ1;
    private int puntosJ2;
    private boolean turnoJ1;
    Jugador[] jugadores;

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
        turnoJ1 = true;
        jugadores=new Jugador[2];
    }
    
    public void agregarJugadores(String player1, String player2){
        Jugador Player1= new Jugador(player1);
        Jugador Player2= new Jugador(player2);
        jugadores[0]=Player1;
        jugadores[1]=Player2;
        
    }
    
    public String getNombreJugador(int index){
        if (jugadores == null || index < 0 || index >= jugadores.length || jugadores[index] == null) {
            return "Jugador " + (index + 1);
        }
        return jugadores[index].getNombre();
    }
    public void generarTablero(){
        ArrayList<String> parejas = new ArrayList<>();
        for (String nombre : nombresImagenes) {
            parejas.add(nombre);
            parejas.add(nombre);
        }
        Collections.shuffle(parejas);

        int index = 0;
        System.out.println("=== TABLERO GENERADO ===");
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                tablero[i][j] = parejas.get(index++);
                cartaRevelada[i][j] = false;
                System.out.print("[" + i + "][" + j + "]=" + tablero[i][j].substring(0, Math.min(8, tablero[i][j].length())) + " ");
            }
            System.out.println();
        }
        System.out.println("========================");
    }
    
     public String getTurnoActual(){
    return turnoJ1 ? getNombreJugador(0) : getNombreJugador(1);
    }
    
    public boolean isTurnoJ1() {
    return turnoJ1;
    }
    
    public boolean pareja(int fila1, int columna1, int fila2, int columna2){
        // Validar que no sean la misma carta
        if (fila1 == fila2 && columna1 == columna2) {
            return false;
        }
        
        // Validar que las cartas no estén ya reveladas
        if (cartaRevelada[fila1][columna1] || cartaRevelada[fila2][columna2]) {
            return false;
        }
        
        // Obtener nombres de las imágenes
        String imagen1 = tablero[fila1][columna1];
        String imagen2 = tablero[fila2][columna2];
        
        // Comparar las imágenes (sin espacios en blanco)
        boolean sonIguales = imagen1 != null && imagen2 != null && 
                            imagen1.trim().equals(imagen2.trim());
        
        if(sonIguales){
            // Son pareja - marcar como reveladas
            cartaRevelada[fila1][columna1] = true;
            cartaRevelada[fila2][columna2] = true;
            
            // Actualizar puntajes según el turno actual
            if(turnoJ1) {
                puntosJ1++;
                if (jugadores != null && jugadores[0] != null) {
                    jugadores[0].agregarAciertos();
                }
            } else {
                puntosJ2++;
                if (jugadores != null && jugadores[1] != null) {
                    jugadores[1].agregarAciertos();
                }
            }
            // NO cambiar turno cuando se forma pareja - el jugador sigue
            return true;
        } else {
            // No son pareja - cambiar turno
            cambiarTurno();
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
    
    @Override
    public void iniciarJuego() {
        this.puntosJ1 = 0;
        this.puntosJ2 = 0;
        this.turnoJ1 = true;
        generarTablero();
    }

    @Override
    public void cambiarTurno() {
        this.turnoJ1 = !this.turnoJ1;
    }

    @Override
    public void finalizarJuego() {
        // Método para finalizar el juego, la lógica de mostrar ganador está en la UI
    }
    
    public Jugador getGanador() {
        if (!juegoTerminado()) {
            return null;
        }
        if (jugadores == null || jugadores[0] == null || jugadores[1] == null) {
            return null;
        }
        int aciertosJ1 = jugadores[0].getAciertos();
        int aciertosJ2 = jugadores[1].getAciertos();
        if (aciertosJ1 > aciertosJ2) {
            return jugadores[0];
        } else if (aciertosJ2 > aciertosJ1) {
            return jugadores[1];
        }
        return null; // Empate
    }
    
    public boolean hayEmpate() {
        if (!juegoTerminado() || jugadores == null || jugadores[0] == null || jugadores[1] == null) {
            return false;
        }
        return jugadores[0].getAciertos() == jugadores[1].getAciertos();
    }

    @Override
    public boolean verificarParejas(Carta carta1, Carta carta2) {
        if (carta1 == null || carta2 == null) {
            return false;
        }
        return carta1.getIdImagen().equals(carta2.getIdImagen());
    }

    @Override
    public boolean juegoTerminado() {
        return (puntosJ1+puntosJ2) == totalParejas; 
    }
}
