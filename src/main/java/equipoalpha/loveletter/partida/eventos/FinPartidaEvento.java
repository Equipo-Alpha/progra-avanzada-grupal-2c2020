package equipoalpha.loveletter.partida.eventos;

import com.google.gson.JsonObject;
import equipoalpha.loveletter.common.MensajeTipo;
import equipoalpha.loveletter.jugador.JugadorIA;
import equipoalpha.loveletter.partida.Sala;
import equipoalpha.loveletter.server.JugadorServer;

import java.util.ArrayList;
import java.util.List;

public class FinPartidaEvento implements EventoObservado{
    private final Sala sala;
    private List<JugadorServer> observadores;

    public FinPartidaEvento(Sala sala) {
        this.sala = sala;
    }


    @Override
    public void notificar(List<JugadorServer> observadores) {
        this.observadores = new ArrayList<>(observadores);
        this.observadores.removeIf(jugador -> jugador instanceof JugadorIA);
        for (JugadorServer jugador : this.observadores) {
            jugador.sincronizarSala();
            jugador.sincronizarPartida();
            jugador.getListener().send(MensajeTipo.PartidaTerminada, new JsonObject());
        }
    }

    @Override
    public void removerObservador(JugadorServer jugador) {
        observadores.remove(jugador);
        if (observadores.isEmpty()) {
            sala.partida.initPartida();
            for (JugadorServer j : sala.jugadores) {
                if (j instanceof JugadorIA) continue;
                j.getListener().send(MensajeTipo.PartidaEmpezada, new JsonObject());
            }
            JugadorServer mano = (JugadorServer) sala.getJugadorMano();
            sala.partida.onNuevaRonda(mano == null ? sala.partida.getJugadorMano() : mano);
        }
    }

    @Override
    public void cancelar() {
        JsonObject respuesta = new JsonObject();
        respuesta.addProperty("tipo", true); // lo setteo correctamente
        respuesta.addProperty("id", 4); // 4 es el id del tipo de respuesta
        for (JugadorServer j : sala.jugadores) {
            if (j instanceof JugadorIA) continue;
            j.getListener().send(MensajeTipo.Confirmacion, respuesta);
        }
    }
}
