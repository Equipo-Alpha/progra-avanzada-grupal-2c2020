package equipoalpha.loveletter.partida.eventos;

import com.google.gson.JsonObject;
import equipoalpha.loveletter.client.LoveLetter;
import equipoalpha.loveletter.common.MensajeTipo;
import equipoalpha.loveletter.jugador.EstadosJugador;
import equipoalpha.loveletter.jugador.JugadorIA;
import equipoalpha.loveletter.partida.Sala;
import equipoalpha.loveletter.server.JugadorServer;
import equipoalpha.loveletter.server.LoveLetterServidor;

import java.util.ArrayList;
import java.util.List;

public class ConfirmarInicioEvento implements EventoObservado {
    private final Sala sala;
    private List<JugadorServer> observadores;

    public ConfirmarInicioEvento(Sala sala) {
        this.sala = sala;
    }

    @Override
    public void notificar(List<JugadorServer> observadores) {
        this.observadores = new ArrayList<>(observadores);
        this.observadores.removeIf(jugador -> jugador instanceof JugadorIA);
        for (JugadorServer jugador : observadores) {
            jugador.getEstado().setEstadoActual(EstadosJugador.CONFIRMANDOINICIO);
            jugador.sincronizar();
        }
    }

    @Override
    public void removerObservador(JugadorServer jugador) {
        observadores.remove(jugador);
        jugador.getEstado().setEstadoActual(EstadosJugador.ESPERANDO);
        jugador.sincronizar();
        if (observadores.isEmpty()) {
            sala.crearPartida();
            if(LoveLetterServidor.getINSTANCE() == null) {
                sala.partida.initPartida();
                return;
            }
            sala.partida.initPartida();
            for (JugadorServer j : sala.jugadores) {
                j.getListener().send(MensajeTipo.PartidaEmpezada, new JsonObject());
                j.sincronizarPartida();
            }
            sala.partida.rondaActual.initTurnos();
        }
    }

    @Override
    public void cancelar() {
        for (JugadorServer jugador : observadores) {
            jugador.getEstado().setEstadoActual(EstadosJugador.ESPERANDO);
            jugador.sincronizar();
        }
        this.observadores = null;
    }
}
