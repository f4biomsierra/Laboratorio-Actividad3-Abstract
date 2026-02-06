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

        // NO generar tablero aquí - se generará cuando se inicie el juego
        setLayout(new GridLayout(6, 6, 5, 5));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // Los botones se inicializarán cuando se genere el tablero
    }
    
    public void inicializarTablero() {
        // Limpiar panel antes de inicializar
        removeAll();
        // Generar tablero
        juego.generarTablero();
        // Inicializar botones con el nuevo tablero
        inicializarBotones();
        // Ocultar todas las cartas inicialmente (mostrar "?")
        ocultarTodasLasCartas();
        // Actualizar UI
        revalidate();
        repaint();
    }

    private void inicializarBotones() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                try {
                    String imagen = juego.getNombreImagenEnPosicion(i, j);
                    System.out.println(imagen);

                    Image img = new ImageIcon(
                            getClass().getResource("/Recursos/" + imagen)
                    ).getImage();

                    if (img == null) {
                        throw new Exception("Imagen no encontrada: " + imagen);
                    }

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
                } catch (Exception e) {
                    System.err.println("Error inicializando botón [" + i + "][" + j + "]: " + e.getMessage());
                    // Crear botón por defecto en caso de error
                    JButton buttonDefault = new JButton("?");
                    buttonDefault.setFont(new Font("Arial", Font.BOLD, 24));
                    botones[i][j] = buttonDefault;
                    add(buttonDefault);
                }
            }
        }
    }

    private void manejarClick(int fila, int columna) {
        System.out.println("Click en fila=" + fila + ", columna=" + columna);
        
        if (bloqueado || !botones[fila][columna].isEnabled()) {
            return;
        }
        // Bloquear click si la carta ya está revelada permanentemente
        if (juego.estaRevelada(fila, columna)) {
            System.out.println("Carta ya revelada, ignorando click");
            return;
        }
        if (filaSeleccionada == fila && columnaSeleccionada == columna) {
            System.out.println("Misma carta seleccionada, ignorando");
            return;
        }

        JButton boton = botones[fila][columna];
        String imagenActual = juego.getNombreImagenEnPosicion(fila, columna);
        System.out.println("Mostrando carta en [" + fila + "][" + columna + "] = " + imagenActual);
        
        boton.setText("");
        boton.setIcon(iconosFrente[fila][columna]);
        if (filaSeleccionada == -1) {
            filaSeleccionada = fila;
            columnaSeleccionada = columna;
            System.out.println("Primera carta seleccionada: [" + fila + "][" + columna + "] = " + imagenActual);
            return;
        }

        bloqueado = true;
        int fila1 = filaSeleccionada;
        int col1 = columnaSeleccionada;
        filaSeleccionada = -1;
        columnaSeleccionada = -1;

        // Debug: imprimir coordenadas
        System.out.println("Coordenadas - Carta 1: [" + fila1 + "][" + col1 + "], Carta 2: [" + fila + "][" + columna + "]");
        
        // Obtener nombres de las imágenes para comparar
        String imagen1 = juego.getNombreImagenEnPosicion(fila1, col1);
        String imagen2 = juego.getNombreImagenEnPosicion(fila, columna);
        
        // Debug: imprimir comparación
        System.out.println("Comparando: " + imagen1 + " ([" + fila1 + "][" + col1 + "]) con " + imagen2 + " ([" + fila + "][" + columna + "]) = " + imagen1.equals(imagen2));
        
        // Verificar pareja - este método actualiza los puntajes internamente
        boolean esPareja = juego.pareja(fila1, col1, fila, columna);
        
        if (esPareja) {
            // Las cartas forman pareja, se quedan visibles y se deshabilitan
            botones[fila1][col1].setEnabled(false);
            botones[fila][columna].setEnabled(false);
            bloqueado = false;
            
            // Actualizar UI después de confirmar pareja
            if (listener != null) {
                listener.onPuntajesCambio();
                listener.onTurnoCambio(juego.getTurnoActual());
            }
            
            // Verificar si el juego terminó
            if (juego.juegoTerminado()) {
                if (listener != null) {
                    listener.onPuntajesCambio(); // Esto mostrará el ganador
                }
            }
        } else {
            // No forman pareja, ocultar después de un delay
            Timer ocultarTimer = new Timer(800, e -> {
                // Solo ocultar si no están reveladas en el juego
                if (!juego.estaRevelada(fila1, col1)) {
                    ocultarCarta(fila1, col1);
                }
                if (!juego.estaRevelada(fila, columna)) {
                    ocultarCarta(fila, columna);
                }
                bloqueado = false;
                
                // Actualizar UI después de ocultar (turno ya cambió en pareja())
                if (listener != null) {
                    listener.onTurnoCambio(juego.getTurnoActual());
                    listener.onPuntajesCambio();
                }
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
                if (!juego.estaRevelada(i, j)) {
                    ocultarCarta(i, j);
                    botones[i][j].setEnabled(true);
                } else {
                    // Si ya está revelada, deshabilitarla para que no se pueda clickear
                    botones[i][j].setEnabled(false);
                }
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
                // Solo habilitar si la carta no está revelada
                if (!juego.estaRevelada(i, j)) {
                    botones[i][j].setEnabled(true);
                } else {
                    botones[i][j].setEnabled(false);
                }
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
