package equipoalpha.loveletter.partida;

import equipoalpha.loveletter.jugador.Jugador;
import equipoalpha.loveletter.partida.eventos.ConfirmarInicioEvento;
import equipoalpha.loveletter.partida.eventos.EventoObservado;
import equipoalpha.loveletter.partida.eventos.EventosPartida;
import equipoalpha.loveletter.partida.eventos.EventosPartidaManager;

import java.util.ArrayList;

public class Sala {
    public final String nombre;
    public final EventosPartidaManager eventos;
    public Jugador creador;
    public ArrayList<Jugador> jugadores = new ArrayList<>();
    public Partida partida;
    private Integer cantSimbolosAfecto;
    private Jugador jugadorMano;

    public Sala(String nombre, Jugador creador) {
        this.nombre = nombre;
        this.creador = creador;
        creador.salaActual = this;
        jugadores.add(creador);
        this.eventos = new EventosPartidaManager();
        EventoObservado confirmarInicio = new ConfirmarInicioEvento(this);
        eventos.registrar(EventosPartida.PEDIRCONFIRMACION, confirmarInicio);
    }

    public void empezarPartida() {
        this.partida = new Partida(creador, jugadores, jugadorMano, cantSimbolosAfecto);
        partida.initPartida();
    }

    public Integer getCantSimbolosAfecto() {
        return cantSimbolosAfecto;
    }

    public void setCantSimbolosAfecto(Integer cantSimbolosAfecto) {
        if (cantSimbolosAfecto < 2 || cantSimbolosAfecto > 7)
            return;
        this.cantSimbolosAfecto = cantSimbolosAfecto;
    }

    public Jugador getJugadorMano() {
        return jugadorMano;
    }

    public void setJugadorMano(Jugador jugadorMano) {
        if (!this.jugadores.contains(jugadorMano)) {
            return;
        }
        this.jugadorMano = jugadorMano;
    }

    public boolean isConfigurada() {
        return jugadorMano != null && cantSimbolosAfecto != null;
    }

    /**
     * Agrega jugadores a la sala.
     *
     * @return false cuando ya hay 4 jugadores en la sala
     */
    public boolean agregarJugador(Jugador jugadorAAgregar) {
        if (jugadores.size() < 4 && !jugadores.contains(jugadorAAgregar)) {
            jugadores.add(jugadorAAgregar);
            jugadorAAgregar.salaActual = this;
            return true;
        }
        return false;
    }

    public void eliminarJugador(Jugador jugadorAEliminar) {
        jugadores.remove(jugadorAEliminar);
        jugadorAEliminar.salaActual = null;
    }

}
