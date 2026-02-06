/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JuegodeMemoria;

import javax.swing.ImageIcon;

/**
 *
 * @author emyca
 */
public class CartaPokemon extends Carta{
    public CartaPokemon(String idImagen, ImageIcon parteFrontal, ImageIcon parteTrasera){
        super(idImagen, parteFrontal, parteTrasera);
    }
    
    @Override
    public void mostrarCarta(){
        cartaRevelada=true;
        this.setIcon(parteFrontal);
        this.setText(""); // Limpiar texto si existe
        // No mostrar JOptionPane para no interrumpir el juego
    }
    
    @Override
    public void ocultarCarta(){
        cartaRevelada=false;
        if (parteTrasera != null) {
            this.setIcon(parteTrasera);
            this.setText("");
        } else {
            this.setIcon(null);
            this.setText("?");
        }
    }
}
