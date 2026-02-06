/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JuegodeMemoria;

import javax.swing.*;
import java.awt.*;


public abstract class Carta extends JButton {
    protected String idImagen;
    protected boolean cartaRevelada=false;
    protected ImageIcon parteFrontal;
    protected ImageIcon parteTrasera;
    
    public Carta(String idImagen, ImageIcon parteFrontal, ImageIcon parteTrasera){
        this.idImagen=idImagen;
        this.parteTrasera=parteTrasera;
        this.parteFrontal=parteFrontal;
        if (parteTrasera != null) {
            this.setIcon(parteTrasera);
        } else {
            this.setIcon(null);
            this.setText("?");
        }
    }
    
    public abstract void mostrarCarta();
    
    public abstract void ocultarCarta();
    
    public boolean cartaRevelada(){
        return cartaRevelada;
    }
    
    public String getIdImagen(){
        return idImagen;
    }
}
