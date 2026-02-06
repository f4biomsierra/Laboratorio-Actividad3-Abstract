package JuegodeMemoria.ui;

import JuegodeMemoria.JuegoMemoria;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public static final String LOGIN = "LOGIN";
    public static final String GAME = "GAME";
    CardLayout cardLayout;
    JPanel mainPanel;
    private final JuegoMemoria juego;
    private final GamePanel gamePanel;

    public MainFrame() {
        setTitle("Memorizacion");
        setSize(600,600);

        juego = new JuegoMemoria();
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.add(new LoginPanel(this), LOGIN);
        gamePanel = new GamePanel(juego);
        mainPanel.add(gamePanel, GAME);

        add(mainPanel);

        showLogin();

    }

    public void showLogin() {
        cardLayout.show(mainPanel, LOGIN);
    }

    public void showGame() {
        try {
            // Inicializar juego y tablero ANTES de mostrar la vista
            juego.iniciarJuego();
            gamePanel.inicializarTablero();
            gamePanel.refrescarTurno();
            
            // Ahora mostrar el juego (el tablero ya está listo)
            cardLayout.show(mainPanel, GAME);
            
            // Iniciar preview después de un pequeño delay para que se vea el tablero primero
            SwingUtilities.invokeLater(() -> {
                gamePanel.iniciarPreview();
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al iniciar el juego: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public JuegoMemoria getJuego() {
        return juego;
    }
}
