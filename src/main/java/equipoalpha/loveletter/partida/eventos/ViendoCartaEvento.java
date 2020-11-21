package equipoalpha.loveletter.partida.eventos;

import equipoalpha.loveletter.jugador.EstadosJugador;
import equipoalpha.loveletter.jugador.Jugador;
import equipoalpha.loveletter.jugador.JugadorIA;
import equipoalpha.loveletter.partida.Sala;
import equipoalpha.loveletter.server.JugadorServer;

import java.util.ArrayList;
import java.util.List;

public class ViendoCartaEvento implements EventoObservado {
    private final Sala sala;
    private List<JugadorServer> observadores;
    private List<JugadorServer> copiaObservadores;

    public ViendoCartaEvento(Sala sala) {
        this.sala = sala;
    }

    @Override
    public void notificar(List<JugadorServer> observadores) {
        this.observadores = new ArrayList<>(observadores);
        this.copiaObservadores = new ArrayList<>(observadores);
        JugadorServer jugador1 = this.observadores.get(0);
        JugadorServer jugador2 = this.observadores.get(1);
        jugador1.getEstado().viendoCarta(jugador2.carta1);
        jugador2.getEstado().viendoCarta(jugador1.carta1);
        if (jugador1 instanceof JugadorIA) {
            removerObservador(jugador1);
        } else jugador1.sincronizar();
        if (jugador2 instanceof JugadorIA) {
            removerObservador(jugador2);
        } else jugador2.sincronizar();
    }

    @Override
    public void removerObservador(JugadorServer jugador) {
        observadores.remove(jugador);
        jugador.getEstado().setEstadoActual(EstadosJugador.ESPERANDO);
        if (observadores.isEmpty()) {
            sala.partida.rondaActual.determinarCartaMayor(copiaObservadores.get(0), copiaObservadores.get(1));
            sala.partida.rondaActual.onFinalizarDescarte(sala.partida.rondaActual.jugadorEnTurno);
        }
    }

    @Override
    public void cancelar() {

    }
}
