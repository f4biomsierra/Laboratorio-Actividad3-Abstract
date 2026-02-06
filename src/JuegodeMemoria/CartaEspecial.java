/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JuegodeMemoria;

import javax.swing.ImageIcon;

/**
 *
 * @author Fabio Sierra
 */
public class CartaEspecial extends Carta {
    
    public CartaEspecial(String idImagen, ImageIcon parteFrontal, ImageIcon parteTrasera) {
        super(idImagen, parteFrontal, parteTrasera);
    }
    
    @Override
    public void mostrarCarta() {
        cartaRevelada = true;
        this.setIcon(parteFrontal);
        this.setText(""); // Limpiar texto si existe
    }
    
    @Override
    public void ocultarCarta() {
        cartaRevelada = false;
        if (parteTrasera != null) {
            this.setIcon(parteTrasera);
        } else {
            this.setIcon(null);
            this.setText("?");
        }
    }
}
