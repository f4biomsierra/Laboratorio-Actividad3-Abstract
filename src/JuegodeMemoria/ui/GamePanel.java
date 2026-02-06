package JuegodeMemoria.ui;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    public GamePanel(){
        GridBagConstraints gbc = new GridBagConstraints();
        JLabel title = new JLabel("MEMORAMA");
        title.setFont(new Font("Arial", Font.BOLD, 26));


        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 0;

        add(title, gbc);
    }


}
