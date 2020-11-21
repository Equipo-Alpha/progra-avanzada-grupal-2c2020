package equipoalpha.lovelettertest;

import equipoalpha.loveletter.carta.Carta;
import equipoalpha.loveletter.carta.CartaTipo;
import equipoalpha.loveletter.jugador.EstadosJugador;
import equipoalpha.loveletter.pantalla.Imagenes;
import equipoalpha.loveletter.partida.Sala;
import equipoalpha.loveletter.partida.eventos.EventosPartida;
import equipoalpha.loveletter.server.JugadorServer;
import equipoalpha.loveletter.util.excepcion.JugadorNoValido;

import javax.swing.ImageIcon;

public class JugadorImplTest extends JugadorServer {

    public ImageIcon icono;

    public JugadorImplTest(String nombre) {
        super(null, -1);
        this.nombre = nombre;
        this.icono = Imagenes.iconoPrincipe;
    }

    /**
     * Crea una sala
     */
    public Sala crearSalaImpl(String nombre) {
        if (salaActual != null)
            return null;

        return new Sala(nombre, this);
    }

    public boolean unirseASalaImpl(Sala sala) {
        if (partidaJugando != null)
            return false;

        this.facade.setEstadoActual(EstadosJugador.ESPERANDO);

        return sala.agregarJugador(this);
    }

    public void confirmarInicio() {
        this.salaActual.eventos.removerObservador(EventosPartida.PEDIRCONFIRMACION, this);
    }

    public void cancelarInicio() {
        this.salaActual.eventos.cancelarEvento(EventosPartida.PEDIRCONFIRMACION);
    }

    public void onComienzoTurno(Carta cartaRobada) {
        this.estaProtegido = false;
        System.out.println("Turno de " + nombre);
        robarCarta(cartaRobada);
    }

    public boolean descartarCarta1Impl() {
        if (this.facade.getEstadoActual() == EstadosJugador.DESCARTANDO
                || (this.facade.getEstadoActual() == EstadosJugador.DESCARTANDOCONDESA
                && carta1.getTipo() == CartaTipo.CONDESA)) {
            Carta cartaJugada = carta1;
            carta1 = carta2;
            carta2 = null;
            this.facade.cartaDescartada(cartaJugada);
            return true;
        }
        return false;
    }

    public boolean descartarCarta2Impl() {
        if (this.facade.getEstadoActual() == EstadosJugador.DESCARTANDO
                || (this.facade.getEstadoActual() == EstadosJugador.DESCARTANDOCONDESA
                && carta2.getTipo() == CartaTipo.CONDESA)) {
            Carta cartaJugada = carta2;
            carta2 = null;
            this.facade.cartaDescartada(cartaJugada);
            return true;
        }
        return false;
    }

    /**
     * Roba una carta del mazo al principio del turno o cuando es afectado por el
     * principe
     */
    public void robarCarta(Carta cartaRobada) {
        carta2 = cartaRobada;

        if (tieneCarta(CartaTipo.CONDESA) && (tieneCarta(CartaTipo.REY) || tieneCarta(CartaTipo.PRINCIPE))) {
            this.facade.setEstadoActual(EstadosJugador.DESCARTANDOCONDESA);
        } else
            this.facade.setEstadoActual(EstadosJugador.DESCARTANDO);
    }

    public void elegirJugadorImpl(JugadorImplTest jugador) throws JugadorNoValido {
        if (this.facade.getCartaDescartada().getTipo() != CartaTipo.PRINCIPE && jugador.equals(this))
            throw new JugadorNoValido();

        if (jugador.estaProtegido)
            throw new JugadorNoValido();

        if (this.facade.getEstadoActual() == EstadosJugador.ELIGIENDOJUGADOR)
            this.facade.jugadorElegido(jugador);
    }

    /**
     * LLamado por el jugador cuando al descartar un guardia quiere adivinar la
     * carta del adversario
     *
     * @param cartaAdivinada la carta que adivina/elige
     */
    public void elegirCarta(CartaTipo cartaAdivinada) {
        if (this.facade.getEstadoActual() == EstadosJugador.ADIVINANDOCARTA)
            this.facade.cartaAdivinada(cartaAdivinada);
    }

    /**
     * LLamado por el sacerdote al ser descartado. Ve la carta que el jugador
     * pasado por parametro tiene.
     *
     * @param jugador jugador al cual this le ve las cartas
     */
    public void verCarta(JugadorImplTest jugador) {
        this.facade.viendoCarta(jugador.carta1);
    }

    public void terminarDeVer() {
        this.facade.terminarDeVer();
    }

    @Override
    public void sincronizarPartida() {
    }

    @Override
    public void sincronizar() {
    }

    @Override
    public void sincronizarSala() {
    }

    @Override
    public void actualizarDummy() {
    }

}
