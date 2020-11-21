package equipoalpha.loveletter.partida.eventos;

import equipoalpha.loveletter.jugador.Jugador;
import equipoalpha.loveletter.server.JugadorServer;

import java.util.HashMap;
import java.util.List;

public class EventosPartidaManager {
    private final HashMap<EventosPartida, EventoObservado> mapaEvento = new HashMap<>();

    public void registrar(EventosPartida nombre, EventoObservado evento) {
        mapaEvento.put(nombre, evento);
    }

    public void ejecutar(EventosPartida nombre, List<JugadorServer> jugadores) {
        EventoObservado evento = mapaEvento.get(nombre);
        if (evento == null) {
            throw new IllegalStateException("No se registro el evento");
        }

        evento.notificar(jugadores);
    }

    public void removerObservador(EventosPartida nombre, JugadorServer jugador) {
        EventoObservado evento = mapaEvento.get(nombre);
        if (evento == null) {
            throw new IllegalStateException("No se registro el evento");
        }
        evento.removerObservador(jugador);
    }

    public void cancelarEvento(EventosPartida nombre) {
        EventoObservado evento = mapaEvento.get(nombre);
        if (evento == null) {
            throw new IllegalStateException("No se registro el evento");
        }
        evento.cancelar();
    }
}
