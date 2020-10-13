package equipoalpha.loveletter.partida;

import equipoalpha.loveletter.carta.Carta;
import equipoalpha.loveletter.carta.CartaTipo;
import equipoalpha.loveletter.partida.eventos.Evento;
import equipoalpha.loveletter.partida.eventos.EventosJugador;
import equipoalpha.loveletter.partida.eventos.EventosJugadorSwitch;

public class EstadoJugador {

    /**
     * el jugador al que pertenece esta instancia
     */
    private final Jugador jugador;
    /**
     * estado actual del jugador
     */
    private EstadosJugador estado;

    private final EventosJugadorSwitch evento;

    private Carta cartaDescartada;
    private Jugador jugadorElegido;
    private CartaTipo cartaAdivinada;

    public EstadoJugador(Jugador jugador) {
        this.estado = null;
        this.jugador = jugador;

        this.evento = new EventosJugadorSwitch();
        EventosJugador eventos = new EventosJugador();

        Evento cartaDescartada = eventos::onCartaDescartada;
        Evento jugadorElegido = eventos::onJugadorElegido;
        Evento cartaAdivinada = eventos::onCartaAdivinada;

        evento.registrar(EventosJugador.Nombre.CARTADESCARTADA, cartaDescartada);
        evento.registrar(EventosJugador.Nombre.JUGADORELEGIDO, jugadorElegido);
        evento.registrar(EventosJugador.Nombre.CARTAADIVINADA, cartaAdivinada);
    }

    public void setEstado(EstadosJugador estado) {
        this.estado = estado;
    }

    public EstadosJugador getEstado() {
        return estado;
    }

    public void cartaDescartada(Carta cartaDescartada) {
        this.cartaDescartada = cartaDescartada;
        ejecutar(EventosJugador.Nombre.CARTADESCARTADA);
    }

    public void jugadorElegido(Jugador jugadorElegido) {
        this.jugadorElegido = jugadorElegido;
        ejecutar(EventosJugador.Nombre.JUGADORELEGIDO);
    }

    public void cartaAdivinada(CartaTipo cartaAdivinada) {
        this.cartaAdivinada = cartaAdivinada;
        ejecutar(EventosJugador.Nombre.CARTAADIVINADA);
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

    public void resetElecciones(){
        this.cartaDescartada = null;
        this.jugadorElegido = null;
        this.cartaAdivinada = null;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void ejecutar(EventosJugador.Nombre nombre){
        evento.ejecutar(nombre, jugador);
    }
}
