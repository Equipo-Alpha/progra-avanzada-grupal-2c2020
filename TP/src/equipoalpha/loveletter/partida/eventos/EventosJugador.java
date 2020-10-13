package equipoalpha.loveletter.partida.eventos;

import equipoalpha.loveletter.partida.Jugador;

public class EventosJugador {
    public enum Nombre{
        CARTADESCARTADA, JUGADORELEGIDO, CARTAADIVINADA
    }

    public void onCartaDescartada(Jugador jugador){
        jugador.getEstado().getCartaDescartada().descartar(jugador);
    }

    public void onJugadorElegido(Jugador jugador){
        jugador.getEstado().getCartaDescartada().onElegido(jugador, jugador.getEstado().getJugadorElegido());
    }

    public void onCartaAdivinada(Jugador jugador){
        jugador.getEstado().getCartaDescartada().onCartaElegida(jugador, jugador.getEstado().getJugadorElegido(), jugador.getEstado().getCartaAdivinada());
    }
}
