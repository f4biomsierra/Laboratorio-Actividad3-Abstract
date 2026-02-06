package JuegodeMemoria.ui;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    public GamePanel(){
        setLayout(new BorderLayout());
        JLabel title = new JLabel("MEMORAMA");
        title.setFont(new Font("Arial", Font.BOLD, 26));
        add(title, BorderLayout.NORTH);
        add(new TableroPanel(), BorderLayout.CENTER);
    }


}
