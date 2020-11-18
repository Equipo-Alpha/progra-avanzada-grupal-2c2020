package equipoalpha.loveletter.partida;

import equipoalpha.loveletter.LoveLetter;
import equipoalpha.loveletter.jugador.EstadosJugador;
import equipoalpha.loveletter.jugador.Jugador;
import equipoalpha.loveletter.partida.eventos.*;
import equipoalpha.loveletter.server.JugadorServer;
import equipoalpha.loveletter.util.Tickable;

import java.util.ArrayList;

public class Sala implements Tickable {
    public final String nombre;
    public final EventosPartidaManager eventos;
    public JugadorServer creador;
    public ArrayList<JugadorServer> jugadores;
    public Partida partida;
    private Integer cantSimbolosAfecto;
    private JugadorServer jugadorMano;
    /**
     * Determina si la partida debe terminar si el creador la abandona, por defecto true
     */
    private Boolean creadorNull = true;
    public boolean tieneBot = false;

    public Sala(String nombre, JugadorServer creador) {
        this.nombre = nombre;
        this.creador = creador;
        this.creador.salaActual = this;
        this.jugadores = new ArrayList<>();
        this.jugadores.add(creador);
        this.eventos = new EventosPartidaManager();
        EventoObservado confirmarInicio = new ConfirmarInicioEvento(this);
        EventoObservado viendoCarta = new ViendoCartaEvento(this);
        this.eventos.registrar(EventosPartida.PEDIRCONFIRMACION, confirmarInicio);
        this.eventos.registrar(EventosPartida.VIENDOCARTA, viendoCarta);
        if (LoveLetter.handler != null) // malditos tests
            registrar();
    }

    public void crearPartida() {
        this.partida = new Partida(jugadores, jugadorMano, cantSimbolosAfecto);
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

    public void setJugadorMano(JugadorServer jugadorMano) {
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
    public boolean agregarJugador(JugadorServer jugadorAAgregar) {
        if (this.jugadores.size() < 4 && !this.jugadores.contains(jugadorAAgregar)) {
            this.jugadores.add(jugadorAAgregar);
            jugadorAAgregar.salaActual = this;
            return true;
        }
        return false;
    }

    public void eliminarJugador(JugadorServer jugadorAEliminar) {
        if (this.partida != null && this.partida.rondaActual.jugadoresEnLaRonda.contains(jugadorAEliminar)) {
            if (jugadorAEliminar.getEstado().getEstadoActual() != EstadosJugador.ESPERANDO)
                this.partida.rondaActual.eliminarJugadorEnTurno(jugadorAEliminar);
            else
                this.partida.rondaActual.eliminarJugador(jugadorAEliminar);
        }
        if (this.partida == null && this.jugadorMano != null && this.jugadorMano.equals(jugadorAEliminar)) {
            this.jugadorMano = null;
        }
        this.jugadores.remove(jugadorAEliminar);
        jugadorAEliminar.partidaJugando = null;
        jugadorAEliminar.salaActual = null;
        if (this.creador == jugadorAEliminar) {
            this.creador = null;
            if (!this.creadorNull) {
                this.creador = this.jugadores.get(0);
            }
        }
    }

    @Override
    public void tick() {
        if (this.creador == null) {
            // TODO para cuando sea server avisarle a cada jugador porque termino la partida
            ArrayList<JugadorServer> nJugadores = new ArrayList<>(this.jugadores);
            nJugadores.forEach(JugadorServer::terminarAcciones);
            nJugadores.forEach(this::eliminarJugador);
            nJugadores.clear();
        }
    }
}
