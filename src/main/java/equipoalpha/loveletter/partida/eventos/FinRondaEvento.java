package equipoalpha.loveletter.partida.eventos;

import com.google.gson.JsonObject;
import equipoalpha.loveletter.common.MensajeTipo;
import equipoalpha.loveletter.jugador.JugadorIA;
import equipoalpha.loveletter.partida.Sala;
import equipoalpha.loveletter.server.JugadorServer;

import java.util.ArrayList;
import java.util.List;

public class FinRondaEvento implements EventoObservado{
    private final Sala sala;
    private List<JugadorServer> observadores;

    public FinRondaEvento(Sala sala) {
        this.sala = sala;
    }

    @Override
    public void notificar(List<JugadorServer> observadores) {
        this.observadores = new ArrayList<>(observadores);
        this.observadores.removeIf(jugador -> jugador instanceof JugadorIA);
        for (JugadorServer jugador : this.observadores) {
            jugador.sincronizarSala();
            jugador.sincronizarPartida();
            jugador.getListener().send(MensajeTipo.RondaTerminada, sala.partida.rondaActual.getJsonMotivoFin());
        }
    }

    @Override
    public void removerObservador(JugadorServer jugador) {
        observadores.remove(jugador);
        if (observadores.isEmpty()) {
            JugadorServer mano = sala.partida.rondaActual.getGanadorRonda();
            sala.partida.onNuevaRonda(mano == null ? sala.partida.getJugadorMano() : mano);
        }
    }

    @Override
    public void cancelar() {
    }
}
