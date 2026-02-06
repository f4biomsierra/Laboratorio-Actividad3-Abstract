/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JuegodeMemoria;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author emyca
 */
public class CartaEspecial extends Carta{
    public CartaEspecial(String idImagen, ImageIcon parteFrontal, ImageIcon parteTrasera){
        super(idImagen, parteFrontal, parteTrasera);
    }
    
    @Override
    public void mostrarCarta(){
        cartaRevelada=true;
        this.setIcon(parteFrontal);
        JOptionPane.showMessageDialog(this, "¡Encontraste un Legendario!\nCarta Especial: "+getIdImagen());
    }
    
    @Override
    public void ocultarCarta(){
        cartaRevelada=false;
        this.setIcon(parteTrasera);
    }
    
    

}
