package equipoalpha.loveletter.partida.eventos;

import com.google.gson.JsonObject;
import equipoalpha.loveletter.common.MensajeTipo;
import equipoalpha.loveletter.jugador.JugadorIA;
import equipoalpha.loveletter.partida.Sala;
import equipoalpha.loveletter.server.JugadorServer;

import java.util.ArrayList;
import java.util.List;

public class ComienzoRondaEvento implements EventoObservado{
    private final Sala sala;
    private List<JugadorServer> observadores;

    public ComienzoRondaEvento(Sala sala) {
        this.sala = sala;
    }

    @Override
    public void notificar(List<JugadorServer> observadores) {
        this.observadores = new ArrayList<>(observadores);
        this.observadores.removeIf(jugador -> jugador instanceof JugadorIA);
        for (JugadorServer jugador : this.observadores) {
            jugador.sincronizar();
            jugador.sincronizarSala();
            jugador.sincronizarPartida();
            jugador.getListener().send(MensajeTipo.RondaEmpezada, new JsonObject());
        }
    }

    @Override
    public void removerObservador(JugadorServer jugador) {
        observadores.remove(jugador);
        if (observadores.isEmpty()) {
            sala.partida.rondaActual.initTurnos();
        }
    }

    @Override
    public void cancelar() {

    }
}
