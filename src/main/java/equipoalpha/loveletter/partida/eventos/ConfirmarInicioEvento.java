package equipoalpha.loveletter.partida.eventos;

import equipoalpha.loveletter.LoveLetter;
import equipoalpha.loveletter.jugador.EstadosJugador;
import equipoalpha.loveletter.jugador.Jugador;
import equipoalpha.loveletter.jugador.JugadorIA;
import equipoalpha.loveletter.partida.Partida;
import equipoalpha.loveletter.partida.Sala;

import java.util.ArrayList;
import java.util.List;

public class ConfirmarInicioEvento implements EventoObservado {
    private final Sala sala;
    private List<Jugador> observadores;

    public ConfirmarInicioEvento(Sala sala) {
        this.sala = sala;
    }

    @Override
    public void notificar(List<Jugador> observadores) {
        this.observadores = new ArrayList<>(observadores);
        this.observadores.removeIf(jugador -> jugador instanceof JugadorIA);
        for (Jugador jugador : observadores) {
            jugador.getEstado().setEstadoActual(EstadosJugador.CONFIRMANDOINICIO);
        }
    }

    @Override
    public void removerObservador(Jugador jugador) {
        observadores.remove(jugador);
        jugador.getEstado().setEstadoActual(EstadosJugador.ESPERANDO);
        if (observadores.isEmpty()) {
            sala.crearPartida();
            if(LoveLetter.getInstance().ventana == null) {
                sala.partida.initPartida();
                return;
            }
            LoveLetter.getInstance().ventana.onPartidaEmpezada();
        }
    }

    @Override
    public void cancelar() {
        for (Jugador jugador : observadores) {
            jugador.getEstado().setEstadoActual(EstadosJugador.ESPERANDO);
        }
        this.observadores = null;
    }
}
