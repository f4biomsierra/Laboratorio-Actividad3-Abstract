package JuegodeMemoria;

public interface TableroListener {
    void onTiempoCambio(int segundos);
    void onTurnoCambio(String nombre);
    void onPuntajesCambio();
}
