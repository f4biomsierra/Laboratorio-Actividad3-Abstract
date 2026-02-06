package JuegodeMemoria.ui;

import javax.swing.*;
import java.awt.*;

public class TableroPanel extends JPanel {

    private JButton[][] botones = new JButton[6][6];

    public TableroPanel() {
        setLayout(new GridLayout(6, 6, 5, 5));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inicializarBotones();
    }

    private void inicializarBotones() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                JButton button = new JButton();
                button.setFocusPainted(false);
                button.setFont(new Font("Arial", Font.BOLD, 12));
                button.setSize(36, 36);
                int fila = i;
                int columna = j;
                button.addActionListener(e -> manejarClick(fila, columna));

                botones[i][j] = button;
                add(button);
            }
        }
    }

    private void manejarClick(int fila, int columna) {
        JOptionPane.showMessageDialog(
                this,
                "Botón en fila " + fila + " columna " + columna
        );
    }
}
