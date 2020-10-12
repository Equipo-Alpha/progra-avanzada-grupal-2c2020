package equipoalpha.loveletter.partida.estadosjugador;

import equipoalpha.loveletter.partida.Jugador;

public class EstadoJugador {

    /**
     * el jugador al que pertenece esta instancia
     */
    private final Jugador jugador;
    /**
     * estado actual del jugador
     */
    private Estado estado;

    public EstadoJugador(Jugador jugador) {
        this.estado = null;
        this.jugador = jugador;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Estado getEstado() {
        return estado;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void ejecutar(){
        if(estado != null){
            estado.ejecutar(this);
        }
    }
}
