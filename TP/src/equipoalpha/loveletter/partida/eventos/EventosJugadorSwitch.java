package equipoalpha.loveletter.partida.eventos;

import equipoalpha.loveletter.partida.Jugador;

import java.util.HashMap;

public class EventosJugadorSwitch {
    private final HashMap<EventosJugador.Nombre, Evento> mapaEvento = new HashMap<>();

    public void registrar(EventosJugador.Nombre nombre, Evento evento){
        mapaEvento.put(nombre, evento);
    }

    public void ejecutar(EventosJugador.Nombre nombre, Jugador jugador){
        Evento evento = mapaEvento.get(nombre);
        if(evento == null){
            throw new IllegalStateException("No se registro el evento");
        }
        evento.ejecutar(jugador);
    }
}
