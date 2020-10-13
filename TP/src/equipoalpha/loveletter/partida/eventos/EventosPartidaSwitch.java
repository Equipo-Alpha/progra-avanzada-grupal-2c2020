package equipoalpha.loveletter.partida.eventos;

import equipoalpha.loveletter.partida.Jugador;
import equipoalpha.loveletter.partida.Ronda;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EventosPartidaSwitch {
    private final HashMap<EventosPartida.Nombre, EventoObservado> mapaEvento = new HashMap<>();
    private final HashMap<EventoObservado, List<Jugador>> mapaObservadores = new HashMap<>();

    public void registrar(EventosPartida.Nombre nombre, EventoObservado evento){
        mapaEvento.put(nombre, evento);
    }
    public void ejecutar(EventosPartida.Nombre nombre, List<Jugador> jugadores){
        EventoObservado evento = mapaEvento.get(nombre);
        if(evento == null) {
            throw new IllegalStateException("No se registro el evento");
        }
        mapaObservadores.putIfAbsent(evento, jugadores);
        evento.notificar(jugadores);
    }

    public boolean removerObservador(EventosPartida.Nombre nombre, Jugador jugador){
        EventoObservado evento = mapaEvento.get(nombre);
        if(evento == null) {
            throw new IllegalStateException("No se registro el evento");
        }
        List<Jugador> observadores = mapaObservadores.remove(evento);
        observadores.remove(jugador);
        if(observadores.isEmpty()){
            return true;
        }
        else {
            mapaObservadores.putIfAbsent(evento, observadores);
            return false;
        }
    }
}
