package equipoalpha.loveletter.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import equipoalpha.loveletter.carta.Carta;
import equipoalpha.loveletter.carta.CartaTipo;
import equipoalpha.loveletter.common.MensajeTipo;
import equipoalpha.loveletter.common.PartidaInfo;
import equipoalpha.loveletter.common.PlayerDummy;
import equipoalpha.loveletter.common.SalaInfo;
import equipoalpha.loveletter.jugador.EstadosJugador;
import equipoalpha.loveletter.jugador.Jugador;
import equipoalpha.loveletter.jugador.JugadorFacade;
import equipoalpha.loveletter.partida.Partida;
import equipoalpha.loveletter.partida.Ronda;
import equipoalpha.loveletter.partida.Sala;
import equipoalpha.loveletter.partida.eventos.EventosPartida;

public class JugadorServer extends Jugador {
    // el jugador, pero del lado del servidor
    // este es el que tiene el facade y procesa los comandos del jugador cliente
    protected final JugadorFacade facade;
    private final ClientListener listener;
    private final Integer id;
    private final PlayerDummy sincHelper;
    private final SalaInfo salaActualInfo;
    private final PartidaInfo partidaActualInfo;
    public Sala salaActual;
    public Partida partidaJugando;
    public Ronda rondaJugando;
    public boolean estaProtegido;
    public int cantSimbolosAfecto;

    public JugadorServer(ClientListener listener, int id) {
        super("tesmp");
        this.listener = listener;
        this.facade = new JugadorFacade(this);
        this.id = id;
        this.sincHelper = new PlayerDummy(this.nombre, this.iconoNombre, this.id);
        this.salaActualInfo = new SalaInfo();
        this.partidaActualInfo = new PartidaInfo();
    }

    public Integer getId() {
        return id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void onComienzoTurno(Carta cartaRobada) {
        this.estaProtegido = false;
        System.out.println("Turno de " + nombre);
        robarCarta(cartaRobada);
    }

    public void robarCarta(Carta cartaRobada) {
        carta2 = cartaRobada;

        if (tieneCarta(CartaTipo.CONDESA) && (tieneCarta(CartaTipo.REY) || tieneCarta(CartaTipo.PRINCIPE))) {
            this.facade.setEstadoActual(EstadosJugador.DESCARTANDOCONDESA);
        } else
            this.facade.setEstadoActual(EstadosJugador.DESCARTANDO);

        sincronizar();// sincronizar
    }

    @Override
    public void crearSala(String nombre) {
        if (this.salaActual != null)
            return;

        Sala sala = new Sala(nombre, this);
        LoveLetterServidor.getINSTANCE().agregarSala(sala);
        sincronizar();
    }

    public void unirseASala(Sala sala) {
        if (this.salaActual != null)
            return;

        this.facade.setEstadoActual(EstadosJugador.ESPERANDO);
        sala.agregarJugador(this);
        sincronizar();
    }

    @Override
    public void iniciarPartida() {
        if (salaActual == null || !salaActual.creador.equals(this) || !salaActual.isConfigurada()) {
            return;
        }

        salaActual.eventos.ejecutar(EventosPartida.PEDIRCONFIRMACION, salaActual.jugadores);
    }

    @Override
    public void confirmarInicio() {
        if (this.facade.getEstadoActual() == EstadosJugador.CONFIRMANDOINICIO) {
            this.salaActual.eventos.removerObservador(EventosPartida.PEDIRCONFIRMACION, this);
            //sincronizar();
        }
    }

    @Override
    public void cancelarInicio() {
        if (this.facade.getEstadoActual() == EstadosJugador.CONFIRMANDOINICIO) {
            this.salaActual.eventos.cancelarEvento(EventosPartida.PEDIRCONFIRMACION);
            sincronizar();// sincronizar
        }
    }

    @Override
    public void descartarCarta1() {
        if (this.facade.getEstadoActual() == EstadosJugador.DESCARTANDO
                || (this.facade.getEstadoActual() == EstadosJugador.DESCARTANDOCONDESA
                && carta1.getTipo() == CartaTipo.CONDESA)) {
            Carta cartaJugada = carta1;
            carta1 = carta2;
            carta2 = null;
            sincronizar();// sincronizar
            this.facade.cartaDescartada(cartaJugada);
        }
    }

    @Override
    public void descartarCarta2() {
        if (this.facade.getEstadoActual() == EstadosJugador.DESCARTANDO
                || (this.facade.getEstadoActual() == EstadosJugador.DESCARTANDOCONDESA
                && carta2.getTipo() == CartaTipo.CONDESA)) {
            Carta cartaJugada = carta2;
            carta2 = null;
            sincronizar();// sincronizar
            this.facade.cartaDescartada(cartaJugada);
        }
    }

    public void elegirJugador(Jugador jugador) {
        if (this.facade.getEstadoActual() == EstadosJugador.ELIGIENDOJUGADOR) {
            this.facade.jugadorElegido((JugadorServer) jugador);
            sincronizar();// sincronizar
        }
    }

    @Override
    public void elegirCarta(CartaTipo cartaAdivinada) {
        if (this.facade.getEstadoActual() == EstadosJugador.ADIVINANDOCARTA) {
            this.facade.cartaAdivinada(cartaAdivinada);
            sincronizar();// sincronizar
        }
    }

    /**
     * LLamado por el sacerdote al ser descartado. Ve la carta que el jugador
     * pasado por parametro tiene.
     *
     * @param jugador jugador al cual this le ve las cartas
     */
    public void verCarta(Jugador jugador) {
        this.facade.viendoCarta(jugador.carta1);
        sincronizar();// sincronizar
    }

    public void terminarDeVer() {
        this.facade.terminarDeVer();
        sincronizar();// sincronizar
    }

    public void sincronizar() {
        JsonObject serverData = new JsonObject();
        this.serializarData(serverData);
        serverData.addProperty("estado", this.facade.getEstadoActual().toString());
        if (this.getEstado().getEstadoActual() == EstadosJugador.ELIGIENDOJUGADOR) {
            serverData.addProperty("elegirseASiMismo",
                    this.getEstado().getCartaDescartada().getTipo() == CartaTipo.PRINCIPE);
        }
        if (this.getEstado().getEstadoActual() == EstadosJugador.VIENDOCARTA) {
            serverData.add("cartaViendo", (new Gson().toJsonTree(this.getEstado().getCartaViendo(), Carta.class)));
        }
        this.listener.send(MensajeTipo.SincJugador, serverData);
    }

    public void sincronizarPartida() {
        actualizarPartida();
        JsonObject partidaData = new JsonObject();
        this.partidaActualInfo.serializarData(partidaData);
        this.listener.send(MensajeTipo.SincPartida, partidaData);
    }

    private void actualizarPartida() {
        this.partidaActualInfo.ronda = this.partidaJugando.ronda;
        this.partidaActualInfo.cartaEliminada = this.partidaJugando.rondaActual.cartaEliminada != null;
        this.partidaActualInfo.mazo = this.partidaJugando.rondaActual.cantCartas();
        this.partidaActualInfo.mapaCartasDescartadas.clear();
        this.partidaJugando.rondaActual.mapaCartasDescartadas.forEach((jugadorServer, cartas) -> {
            jugadorServer.actualizarDummy();
            this.partidaActualInfo.mapaCartasDescartadas.put(jugadorServer.sincHelper, cartas);
        });
        this.partidaActualInfo.jugadoresEnLaRonda.clear();
        this.partidaJugando.rondaActual.jugadoresEnLaRonda.forEach(jugadorServer -> {
            this.partidaActualInfo.jugadoresEnLaRonda.add(jugadorServer.sincHelper);
        });
        this.partidaActualInfo.ordenReparto.clear();
        this.partidaJugando.rondaActual.ordenReparto.forEach(jugadorServer -> {
            this.partidaActualInfo.ordenReparto.add(jugadorServer.sincHelper);
        });
        this.partidaActualInfo.jugadorEnTurno = this.partidaJugando.getJugadorMano().sincHelper;
    }

    public void sincronizarSala() {
        this.salaActualInfo.nombre = this.salaActual.nombre;
        this.salaActualInfo.creador = this.salaActual.creador.sincHelper;
        this.salaActualInfo.jugadores.clear();
        for (JugadorServer jugador : this.salaActual.jugadores) {
            jugador.actualizarDummy();
            this.salaActualInfo.jugadores.add(jugador.sincHelper);
        }
        this.salaActualInfo.isConfigurada = this.salaActual.isConfigurada();
        JsonObject salaData = new JsonObject();
        this.salaActualInfo.serializarData(salaData);
        this.listener.send(MensajeTipo.SincSala, salaData);
    }

    public void actualizarDummy() {
        this.sincHelper.cantSimbolos = this.cantSimbolosAfecto;
        this.sincHelper.nombre = this.nombre;
        this.sincHelper.icono = this.iconoNombre;
        this.sincHelper.estaEnLaRonda = rondaJugando != null;
        this.sincHelper.tieneCarta1 = carta1 != null;
        this.sincHelper.tieneCarta2 = carta2 != null;
        this.sincHelper.estaProtegido = this.estaProtegido;
    }

    public ClientListener getListener() {
        return listener;
    }

    @Override
    public void salirSala() {
        if (salaActual == null)
            return;
        this.salaActual.eliminarJugador(this);
    }

    public JugadorFacade getEstado() {
        return facade;
    }

    public void terminarAcciones() {
        this.facade.resetElecciones();
        this.facade.setEstadoActual(EstadosJugador.ESPERANDO);
    }
}
