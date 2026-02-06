package JuegodeMemoria.ui;

import JuegodeMemoria.JuegoMemoria;
import JuegodeMemoria.TableroListener;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements TableroListener {
    private final TableroPanel tableroPanel;
    private final JLabel turnoLabel;
    private final JLabel contadorLabel;
    private final JLabel puntajesLabel;
    private final JuegoMemoria juego;
    public GamePanel(JuegoMemoria juego){
        this.juego = juego;
        setLayout(new BorderLayout());
        JPanel header = new JPanel(new GridLayout(4, 1));
        JLabel title = new JLabel("MEMORAMA");
        title.setFont(new Font("Arial", Font.BOLD, 26));
        turnoLabel = new JLabel("Turno : ");
        turnoLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        contadorLabel = new JLabel("Tiempo: 3");
        contadorLabel.setFont(new Font("Arial", Font.BOLD, 22));
        puntajesLabel = new JLabel();
        puntajesLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        header.add(title);
        header.add(turnoLabel);
        header.add(contadorLabel);
        header.add(puntajesLabel);
        add(header, BorderLayout.NORTH);
        tableroPanel = new TableroPanel(juego, this);
        add(tableroPanel, BorderLayout.CENTER);
        actualizarTurno(juego.getTurnoActual());
        actualizarPuntajes();
    }

    public void iniciarPreview() {
        tableroPanel.iniciarPreview();
    }

    public void refrescarTurno() {
        actualizarTurno(juego.getTurnoActual());
    }

    private void actualizarTurno(String nombre) {
        turnoLabel.setText("Turno : " + nombre);
    }

    private void actualizarPuntajes() {
        String j1 = juego.getNombreJugador(0);
        String j2 = juego.getNombreJugador(1);
        puntajesLabel.setText(
                j1 + ": " + juego.getPuntosJ1() + "  |  " + j2 + ": " + juego.getPuntosJ2()
        );
    }

    @Override
    public void onTiempoCambio(int segundos) {
        contadorLabel.setText("Tiempo: " + segundos);
    }

    @Override
    public void onTurnoCambio(String nombre) {
        actualizarTurno(nombre);
    }

    @Override
    public void onPuntajesCambio() {
        actualizarPuntajes();
    }


}
