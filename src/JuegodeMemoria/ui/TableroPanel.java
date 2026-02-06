package JuegodeMemoria.ui;

import JuegodeMemoria.JuegoMemoria;
import JuegodeMemoria.TableroListener;

import javax.swing.*;
import java.awt.*;

public class TableroPanel extends JPanel {

    private JButton[][] botones = new JButton[6][6];
    private ImageIcon[][] iconosFrente = new ImageIcon[6][6];
    private final int TAMANO_CASILLA = 80;
    private final JuegoMemoria juego;
    private final TableroListener listener;
    private final int previewSegundos = 3;
    private int filaSeleccionada = -1;
    private int columnaSeleccionada = -1;
    private boolean bloqueado;
    public TableroPanel(JuegoMemoria juego, TableroListener listener) {
        this.juego = juego;
        this.listener = listener;
        this.bloqueado = false;

        this.juego.generarTablero();
        setLayout(new GridLayout(6, 6, 5, 5));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inicializarBotones();
    }

    private void inicializarBotones() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {

                String imagen = juego.getNombreImagenEnPosicion(i, j);
                System.out.println(imagen);

                Image img = new ImageIcon(
                        getClass().getResource("/Recursos/" + imagen)
                ).getImage();

                Image imgEscalada = img.getScaledInstance(
                        TAMANO_CASILLA,
                        TAMANO_CASILLA,
                        Image.SCALE_SMOOTH
                );

                JButton button = new JButton();
                ImageIcon iconoFrente = new ImageIcon(imgEscalada);
                iconosFrente[i][j] = iconoFrente;
                button.setIcon(iconoFrente);
                button.setFocusPainted(false);
                button.setBorderPainted(true);
                button.setContentAreaFilled(false);
                button.setFont(new Font("Arial", Font.BOLD, 24));

                int fila = i;
                int columna = j;
                button.addActionListener(e -> manejarClick(fila, columna));

                botones[i][j] = button;
                add(button);
            }

        }
    }

    private void manejarClick(int fila, int columna) {
        if (bloqueado || !botones[fila][columna].isEnabled()) {
            return;
        }
        if (juego.estaRevelada(fila, columna)) {
            return;
        }
        if (filaSeleccionada == fila && columnaSeleccionada == columna) {
            return;
        }

        JButton boton = botones[fila][columna];
        boton.setText("");
        boton.setIcon(iconosFrente[fila][columna]);
        if (filaSeleccionada == -1) {
            filaSeleccionada = fila;
            columnaSeleccionada = columna;
            return;
        }

        bloqueado = true;
        int fila1 = filaSeleccionada;
        int col1 = columnaSeleccionada;
        filaSeleccionada = -1;
        columnaSeleccionada = -1;

        boolean esPareja = juego.pareja(fila1, col1, fila, columna);
        if (listener != null) {
            listener.onTurnoCambio(juego.getTurnoActual());
            listener.onPuntajesCambio();
        }

        if (esPareja) {
            bloqueado = false;
        } else {
            Timer ocultarTimer = new Timer(800, e -> {
                ocultarCarta(fila1, col1);
                ocultarCarta(fila, columna);
                bloqueado = false;
            });
            ocultarTimer.setRepeats(false);
            ocultarTimer.start();
        }
    }

    public void iniciarPreview() {
        mostrarTodasLasCartas();
        deshabilitarClicks();

        int[] segundosRestantes = {previewSegundos};
        if (listener != null) {
            listener.onTiempoCambio(segundosRestantes[0]);
        }

        Timer countdownTimer = new Timer(1000, e -> {
            segundosRestantes[0]--;
            if (listener != null) {
                listener.onTiempoCambio(segundosRestantes[0]);
            }
            if (segundosRestantes[0] <= 0) {
                ((Timer) e.getSource()).stop();
                ocultarTodasLasCartas();
                habilitarClicks();
            }
        });

        countdownTimer.setRepeats(true);
        countdownTimer.start();
    }

    private void mostrarTodasLasCartas() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                botones[i][j].setText("");
                botones[i][j].setIcon(iconosFrente[i][j]);
            }
        }
    }

    private void ocultarTodasLasCartas() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                ocultarCarta(i, j);
            }
        }
    }

    private void ocultarCarta(int fila, int columna) {
        botones[fila][columna].setIcon(null);
        botones[fila][columna].setText("?");
    }

    private void habilitarClicks() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                botones[i][j].setEnabled(true);
            }
        }
    }

    private void deshabilitarClicks() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                botones[i][j].setEnabled(false);
            }
        }
    }
}
