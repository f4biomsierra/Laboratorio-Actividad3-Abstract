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
    
    private Timer temporizador;
    private int tiempoJ1=0;
    private int tiempoJ2=0;
    private int jugadorActual=-1;
    
    public GamePanel(JuegoMemoria juego){
        this.juego = juego;
        setLayout(new BorderLayout());
        JPanel header = new JPanel(new GridLayout(4, 1));
        JLabel title = new JLabel("MEMORAMA");
        title.setFont(new Font("Arial", Font.BOLD, 26));
        turnoLabel = new JLabel("Turno : ");
        turnoLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        contadorLabel = new JLabel("Tiempo J1: 0s / Tiempo J2: 0s");
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
        crearTemporizador();
    }
    
    private void crearTemporizador() {
        temporizador = new Timer(1000, e -> {
            if (jugadorActual == 0) {
                tiempoJ1++;
            } else if (jugadorActual == 1) {
                tiempoJ2++;
            }

            contadorLabel.setText(
                    "Tiempo " + juego.getNombreJugador(0) + ": " + tiempoJ1 + "s | "
                    + juego.getNombreJugador(1) + ": " + tiempoJ2 + "s"
            );
        });
    }
    
    private void iniciarTemporizador() {
        temporizador = new Timer(1000, e -> {
            if (jugadorActual == 0) {
                tiempoJ1++;
            } else {
                tiempoJ2++;
            }

            contadorLabel.setText(
                    "Tiempo " + juego.getNombreJugador(0) + ": " + tiempoJ1 + "s | "
                    + juego.getNombreJugador(1) + ": " + tiempoJ2 + "s"
            );
        });

        temporizador.start();
    }
    
    @Override
    public void onTurnoCambio(String nombre) {
        actualizarTurno(nombre);
        if (temporizador.isRunning()) {
            temporizador.stop();
        }

        if (nombre.equals(juego.getNombreJugador(0))) {
            jugadorActual = 0;
        } else if (nombre.equals(juego.getNombreJugador(1))) {
            jugadorActual = 1;
        } else {
            jugadorActual = -1;

            if (jugadorActual != -1) {
                temporizador.start();
            }
        }
    }
    public void iniciarPreview(){
        tableroPanel.iniciarPreview();
        actualizarTurno(juego.getTurnoActual());
        actualizarPuntajes();
        crearTemporizador();
        iniciarTemporizador();
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
        
    }

    @Override
    public void onPuntajesCambio() {
        actualizarPuntajes();
    }


}
