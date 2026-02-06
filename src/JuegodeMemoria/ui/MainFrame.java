package JuegodeMemoria.ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public static final String LOGIN = "LOGIN";
    public static final String GAME = "GAME";
    CardLayout cardLayout;
    JPanel mainPanel;
    JPanel gamePanel;

    public MainFrame() {
        setTitle("Memorizacion");
        setSize(600,600);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        gamePanel = new GamePanel();
        mainPanel.add(new LoginPanel(this), LOGIN);
        mainPanel.add(new GamePanel(), GAME);

        add(mainPanel);

        showLogin();

    }

    public void showLogin() {
        cardLayout.show(mainPanel, LOGIN);
    }

    public void showGame() {
        cardLayout.show(mainPanel, GAME);
    }
}
