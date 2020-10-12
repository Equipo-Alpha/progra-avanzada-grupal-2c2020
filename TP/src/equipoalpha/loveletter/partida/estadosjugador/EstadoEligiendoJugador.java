package equipoalpha.loveletter.partida.estadosjugador;

import equipoalpha.loveletter.carta.Carta;
import equipoalpha.loveletter.partida.Jugador;

public class EstadoEligiendoJugador implements Estado{

    private Jugador jugadorElegido;
    private final Carta cartaDescartada;

    public EstadoEligiendoJugador(Carta cartaDescartada) {
        this.cartaDescartada = cartaDescartada;
    }

    @Override
    public void ejecutar(EstadoJugador estado) {
        cartaDescartada.onElegido(estado.getJugador(), jugadorElegido);
    }

    public void setJugadorElegido(Jugador jugadorElegido) {
        this.jugadorElegido = jugadorElegido;
    }
}
