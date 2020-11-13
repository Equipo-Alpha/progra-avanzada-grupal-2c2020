package equipoalpha.loveletter.jugador;

import equipoalpha.loveletter.carta.Carta;
import equipoalpha.loveletter.carta.CartaTipo;
import equipoalpha.loveletter.jugador.eventos.Evento;
import equipoalpha.loveletter.jugador.eventos.EventosJugador;
import equipoalpha.loveletter.jugador.eventos.EventosJugadorManager;
import equipoalpha.loveletter.partida.eventos.EventosPartida;

public class JugadorFacade {

    /**
     * el jugador al que pertenece esta instancia
     */
    private final Jugador jugador;
    private final EventosJugadorManager evento;
    /**
     * estado actual del jugador
     */
    private EstadosJugador estadoActual;
    private Carta cartaDescartada;
    private Jugador jugadorElegido;
    private CartaTipo cartaAdivinada;
    private Carta cartaViendo;

    public JugadorFacade(Jugador jugador) {
        this.estadoActual = null;
        this.jugador = jugador;

        this.evento = new EventosJugadorManager();
        EventosJugador eventos = new EventosJugador();

        Evento cartaDescartada = eventos::onCartaDescartada;
        Evento jugadorElegido = eventos::onJugadorElegido;
        Evento cartaAdivinada = eventos::onCartaAdivinada;

        evento.registrar(EventosJugador.Nombre.CARTADESCARTADA, cartaDescartada);
        evento.registrar(EventosJugador.Nombre.JUGADORELEGIDO, jugadorElegido);
        evento.registrar(EventosJugador.Nombre.CARTAADIVINADA, cartaAdivinada);
    }

    public EstadosJugador getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(EstadosJugador estadoActual) {
        this.estadoActual = estadoActual;
    }

    public void cartaDescartada(Carta cartaDescartada) {
        this.cartaDescartada = cartaDescartada;
        ejecutar(EventosJugador.Nombre.CARTADESCARTADA);
    }

    public void jugadorElegido(Jugador jugadorElegido) {
        if (this.estadoActual == EstadosJugador.ELIGIENDOJUGADOR) {
            this.jugadorElegido = jugadorElegido;
            ejecutar(EventosJugador.Nombre.JUGADORELEGIDO);
        }
    }

    public void cartaAdivinada(CartaTipo cartaAdivinada) {
        if (this.estadoActual == EstadosJugador.ADIVINANDOCARTA && cartaAdivinada != CartaTipo.GUARDIA) {
            this.cartaAdivinada = cartaAdivinada;
            ejecutar(EventosJugador.Nombre.CARTAADIVINADA);
        }
    }

    public void viendoCarta(Carta carta) {
        this.cartaViendo = carta;
        this.estadoActual = EstadosJugador.VIENDOCARTA;
    }

    public void terminarDeVer() {
        if (this.estadoActual == EstadosJugador.VIENDOCARTA) {
            if (cartaDescartada == null || cartaDescartada.getTipo() == CartaTipo.BARON)
                jugador.salaActual.eventos.removerObservador(EventosPartida.VIENDOCARTA, jugador);
            else
                this.jugador.rondaJugando.onFinalizarDescarte(this.jugador);
        }
    }

    public Carta getCartaDescartada() {
        return cartaDescartada;
    }

    public Jugador getJugadorElegido() {
        return jugadorElegido;
    }

    public CartaTipo getCartaAdivinada() {
        return cartaAdivinada;
    }

    public Carta getCartaViendo() {
        return cartaViendo;
    }

    public void resetElecciones() {
        this.cartaDescartada = null;
        this.jugadorElegido = null;
        this.cartaAdivinada = null;
        this.cartaViendo = null;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void ejecutar(EventosJugador.Nombre nombre) {
        this.evento.ejecutar(nombre, this.jugador);
    }
}
