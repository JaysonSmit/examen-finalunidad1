package pe.edu.upeu.calcfx.modelo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Match {
    private final StringProperty partido;
    private final StringProperty jugador1;
    private final StringProperty jugador2;
    private final StringProperty ganador;
    private final StringProperty puntuacion;
    private final StringProperty estado;
    private static int contador = 1;
    public Match(String jugador1, String jugador2, String ganador, String puntuacion) {
        this.partido = new SimpleStringProperty("Partido " + contador++);
        this.jugador1 = new SimpleStringProperty(jugador1);
        this.jugador2 = new SimpleStringProperty(jugador2);
        this.ganador = new SimpleStringProperty(ganador);
        this.puntuacion = new SimpleStringProperty(puntuacion);
        this.estado = new SimpleStringProperty("Jugando");
    }


    public StringProperty partidoProperty() { return partido; }
    public StringProperty jugador1Property() { return jugador1; }
    public StringProperty jugador2Property() { return jugador2; }
    public StringProperty ganadorProperty() { return ganador; }
    public StringProperty puntuacionProperty() { return puntuacion; }
    public StringProperty estadoProperty() { return estado; }

    public void setEstado(String estado) { this.estado.set(estado); }
    public String getEstado() { return estado.get(); }
}
