package equipoalpha.loveletter.partida;

import equipoalpha.loveletter.LoveLetter;
import equipoalpha.loveletter.jugador.Jugador;
import equipoalpha.loveletter.partida.eventos.ConfirmarInicioEvento;
import equipoalpha.loveletter.partida.eventos.EventoObservado;
import equipoalpha.loveletter.partida.eventos.EventosPartida;
import equipoalpha.loveletter.partida.eventos.EventosPartidaManager;
import equipoalpha.loveletter.util.Tickable;

import java.util.ArrayList;

public class Sala implements Tickable {
    public final String nombre;
    public final EventosPartidaManager eventos;
    public Jugador creador;
    public ArrayList<Jugador> jugadores = new ArrayList<>();
    public Partida partida;
    private Integer cantSimbolosAfecto;
    private Jugador jugadorMano;
    /**
     * Determina si la partida debe terminar si el creador la abandona, por defecto true
     */
    private Boolean creadorNull = true;

    public Sala(String nombre, Jugador creador) {
        this.nombre = nombre;
        this.creador = creador;
        creador.salaActual = this;
        jugadores.add(creador);
        this.eventos = new EventosPartidaManager();
        EventoObservado confirmarInicio = new ConfirmarInicioEvento(this);
        eventos.registrar(EventosPartida.PEDIRCONFIRMACION, confirmarInicio);
        if (LoveLetter.handler != null) // malditos tests
            registrar();
    }

    public void crearPartida() {
        this.partida = new Partida(creador, jugadores, jugadorMano, cantSimbolosAfecto);
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

    public void setCreadorNull(Boolean creadorNull) {
        this.creadorNull = creadorNull;
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
        if (partida != null && partida.rondaActual.jugadoresEnLaRonda.contains(jugadorAEliminar)) {
            partida.rondaActual.eliminarJugador(jugadorAEliminar);
        }
        jugadores.remove(jugadorAEliminar);
        jugadorAEliminar.partidaJugando = null;
        jugadorAEliminar.salaActual = null;
        if (this.creador == jugadorAEliminar) {
            this.creador = null;
            if (!this.creadorNull) {
                this.creador = jugadores.get(0);
            }
        }
    }

    @Override
    public void tick() {
        if (this.creador == null) {
            // TODO para cuando sea server avisarle a cada jugador porque termino la partida
            ArrayList<Jugador> nJugadores = new ArrayList<>(this.jugadores);
            nJugadores.forEach(Jugador::terminarAcciones);
            nJugadores.forEach(this::eliminarJugador);
            nJugadores.clear();
        }
    }
}
