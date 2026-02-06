/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JuegodeMemoria;

import JuegodeMemoria.ui.MainFrame;

/**
 *
 * @author Fabio Sierra
 */
public class MainApp {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() ->  {
            new MainFrame().setVisible(true);
        });
    }
}
