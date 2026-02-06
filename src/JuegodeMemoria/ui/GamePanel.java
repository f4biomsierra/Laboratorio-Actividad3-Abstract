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
    
    @Override
    public void onTurnoCambio(String nombre) {
        actualizarTurno(nombre);
        
        // Detener temporizador anterior
        if (temporizador != null && temporizador.isRunning()) {
            temporizador.stop();
        }

        // Determinar jugador actual basándose en el nombre
        String nombreJ1 = juego.getNombreJugador(0);
        String nombreJ2 = juego.getNombreJugador(1);
        
        if (nombre.equals(nombreJ1)) {
            jugadorActual = 0;
        } else if (nombre.equals(nombreJ2)) {
            jugadorActual = 1;
        } else {
            // Si no coincide, usar el estado del juego
            jugadorActual = juego.isTurnoJ1() ? 0 : 1;
        }

        // Iniciar temporizador del nuevo jugador
        if (jugadorActual != -1 && temporizador != null) {
            temporizador.start();
        }
    }
    public void inicializarTablero() {
        // Inicializar el tablero (generar y crear botones)
        tableroPanel.inicializarTablero();
    }
    
    public void iniciarPreview(){
        // El tablero ya está inicializado, solo iniciar el preview
        tableroPanel.iniciarPreview();
        actualizarTurno(juego.getTurnoActual());
        actualizarPuntajes();
        crearTemporizador();
        // Iniciar temporizador del primer jugador
        jugadorActual = juego.isTurnoJ1() ? 0 : 1;
        if (temporizador != null) {
            temporizador.start();
        }
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
        // Verificar si el juego terminó
        if (juego.juegoTerminado()) {
            mostrarGanador();
        }
    }
    
    private void mostrarGanador() {
        try {
            juego.finalizarJuego();
            
            // Detener temporizador
            if (temporizador != null && temporizador.isRunning()) {
                temporizador.stop();
            }
            
            String mensaje;
            String j1 = juego.getNombreJugador(0);
            String j2 = juego.getNombreJugador(1);
            int puntosJ1 = juego.getPuntosJ1();
            int puntosJ2 = juego.getPuntosJ2();
            
            if (juego.hayEmpate()) {
                mensaje = String.format(
                    "¡EMPATE!\n\n%s: %d aciertos - Tiempo: %ds\n%s: %d aciertos - Tiempo: %ds",
                    j1, puntosJ1, tiempoJ1,
                    j2, puntosJ2, tiempoJ2
                );
            } else {
                var ganador = juego.getGanador();
                if (ganador != null) {
                    int tiempoGanador = ganador.getNombre().equals(j1) ? tiempoJ1 : tiempoJ2;
                    mensaje = String.format(
                        "¡GANADOR!\n\n%s\n\nAciertos: %d\nTiempo: %ds",
                        ganador.getNombre(),
                        ganador.getAciertos(),
                        tiempoGanador
                    );
                } else {
                    mensaje = "Juego terminado";
                }
            }
            
            JOptionPane.showMessageDialog(this, mensaje, "Fin del Juego", 
                JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            System.err.println("Error mostrando ganador: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Error al determinar ganador", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


}
