package JuegodeMemoria.ui;

import JuegodeMemoria.JuegoMemoria;
import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    private JTextField txtPlayer1;
    private  JTextField txtPlayer2;
    private MainFrame frame;
    private JuegoMemoria juego;
    public LoginPanel(MainFrame frame) {
        juego=new JuegoMemoria();
        this.frame = frame;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        JLabel title = new JLabel("MEMORAMA");
        title.setFont(new Font("Arial", Font.BOLD, 26));

        JLabel label = new JLabel("Usuario 1");
        txtPlayer1 = new JTextField(15);
        txtPlayer2 = new JTextField(15);
        JButton startButton = new JButton("Iniciar Juego");

        gbc.insets = new Insets(10,10,10,10);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Player 1:"), gbc);

        gbc.gridx = 1;
        add(txtPlayer1, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Player 2:"), gbc);

        gbc.gridx = 1;
        add(txtPlayer2, gbc);


        gbc.gridx = 2;
        add(startButton, gbc);

        startButton.addActionListener(e -> startGame());
    }

    private void startGame() {
        if(txtPlayer1.getText().isBlank() || txtPlayer2.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar ambos usuario para poder inciar el juego");
        } else {
            juego.agregarJugadores(txtPlayer1.getText(), txtPlayer2.getText());
            JOptionPane.showMessageDialog(this, "Juego inicio");
            this.frame.showGame();
        }
    }
}
