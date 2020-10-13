package equipoalpha.loveletter.partida.eventos;

import equipoalpha.loveletter.partida.EstadosJugador;
import equipoalpha.loveletter.partida.Jugador;

import java.util.List;

public class EventosPartida {

    public enum Nombre{
        PEDIRCONFIRMACION
    }

    public void onPedirConfirmacion(List<Jugador> jugadores){
        for(Jugador jugador : jugadores){
            jugador.getEstado().setEstadoActual(EstadosJugador.CONFIRMANDOINICIO);
        }
    }


}
