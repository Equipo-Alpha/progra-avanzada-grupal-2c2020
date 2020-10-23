package equipoalpha.loveletter.partida;

import equipoalpha.loveletter.carta.Carta;
import equipoalpha.loveletter.carta.CartaTipo;
import equipoalpha.loveletter.partida.eventos.EventosPartida;

public class Jugador {

	/**
	 * Nombre del jugador
	 */
	public String nombre;

	/**
	 * La mano del jugador carta1 es la carta que siempre tiene en la mano carta2 es
	 * la carta que roba del mazo
	 */
	public Carta carta1;
	public Carta carta2;

	/**
	 * La partida en la que actualmente esta jugando
	 */
	public Partida partidaJugando;

	/**
	 * La ronda en la que actualmente esta jugando
	 */
	public Ronda rondaJugando;

	private final JugadorFacade facade;

	/**
	 * Determina si el jugador esta o no protegido por haber descartado una mucama
	 */
	public boolean estaProtegido;

	/**
	 * Cantidad de simbolos de afecto que tiene el jugador
	 */
	public int cantSimbolosAfecto;

	public Jugador(String nombre) {
		this.nombre = nombre;
		this.facade = new JugadorFacade(this);
	}

	/**
	 * Crea una partida
	 */
	public Partida crearPartida() {
		if (partidaJugando != null)
			return null;

		facade.setEstadoActual(EstadosJugador.CREANDOPARTIDA);

		return new Partida(this);
	}

	public boolean unirseAPartida(Partida partida) {
		if (partidaJugando != null)
			return false;

		facade.setEstadoActual(EstadosJugador.ESPERANDO);

		return partida.agregarJugador(this);
	}

	public void iniciarPartida() {
		if (facade.getEstadoActual() != EstadosJugador.CREANDOPARTIDA) {
			return;
		}
		// TODO estos checks pueden volar cuando tengamos algun boton
		if (partidaJugando.getCantSimbolosAfecto() == 0 || partidaJugando.getJugadorMano() == null) {
			return;
		}

		partidaJugando.eventos.ejecutar(EventosPartida.PEDIRCONFIRMACION, partidaJugando.jugadores);
	}

	public void confirmarInicio() {
		if (this.facade.getEstadoActual() == EstadosJugador.CONFIRMANDOINICIO) {
			partidaJugando.eventos.removerObservador(EventosPartida.PEDIRCONFIRMACION, this);
		}
	}

	public void cancelarInicio() {
		if (this.facade.getEstadoActual() == EstadosJugador.CONFIRMANDOINICIO) {
			partidaJugando.eventos.cancelarEvento(EventosPartida.PEDIRCONFIRMACION);
		}
	}

	public void onComienzoTurno(Carta cartaRobada) {
		this.estaProtegido = false;
		robarCarta(cartaRobada);
	}

	public boolean descartarCarta1() {
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

	public boolean descartarCarta2() {
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

	/**
	 * LLamado por el jugador cuando elige a otro para el evento
	 * 
	 * @param jugador jugador elegido para el evento
	 * @throws JugadorNoValido
	 */
	public void elegirJugador(Jugador jugador) throws JugadorNoValido {
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
		if (this.facade.getEstadoActual() == EstadosJugador.ADIVINANDOCARTA) {
			if (cartaAdivinada != CartaTipo.GUARDIA) {
				this.facade.cartaAdivinada(cartaAdivinada);
			}
		}
	}

	/**
	 * LLamado por varias cartas al ser descartadas. Ve la carta que el jugador
	 * pasado por parametro tiene.
	 * 
	 * @param jugador jugador al cual this le ve las cartas
	 */
	public void verCarta(Jugador jugador) {
		System.out.println(this + " ve la carta de " + jugador + ":" + jugador.carta1);
	}

	/**
	 * Generalmente llamado cuando se descarta al guardia
	 * 
	 * @param tipo CartaTipo que se busca comprobar que se tenga
	 * @return true cuando tiene ese tipo de carta
	 */
	public boolean tieneCarta(CartaTipo tipo) {
		if (carta2 != null) {
			return (carta1.getTipo() == tipo || carta2.getTipo() == tipo);
		} else
			return carta1.getTipo() == tipo;

	}

	public int getFuerzaCarta() {
		return carta1.getTipo().fuerza;
	}

	public JugadorFacade getEstado() {
		return facade;
	}

	private void abandonarPartida() {
		if (partidaJugando == null)
			return;
		if (rondaJugando != null) {
			this.rondaJugando.jugadoresEnLaRonda.remove(this);
			this.rondaJugando = null;
		}
		this.partidaJugando.jugadores.remove(this);
		this.partidaJugando = null;
	}

	@Override
	public String toString() {
		return nombre;
	}

}
