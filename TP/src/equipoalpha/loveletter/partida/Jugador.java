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

	private final JugadorFacade estado;

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
		this.estado = new JugadorFacade(this);
	}

	/**
	 * Crea una partida
	 */
	public Partida crearPartida() {
		if(partidaJugando != null) return null;

		estado.setEstadoActual(EstadosJugador.CREANDOPARTIDA);

		return new Partida(this);
	}

	public boolean unirseAPartida(Partida partida){
		if(partidaJugando != null) return false;

		estado.setEstadoActual(EstadosJugador.ESPERANDO);

		return partida.agregarJugador(this);
	}

	public void iniciarPartida(){
		if(estado.getEstadoActual() != EstadosJugador.CREANDOPARTIDA){
			return;
		}
		//TODO estos checks pueden volar cuando tengamos algun boton
		if (partidaJugando.getCantSimbolosAfecto() == 0 || partidaJugando.getJugadorMano() == null) {
			return;
		}

		partidaJugando.eventos.ejecutar(EventosPartida.PEDIRCONFIRMACION, partidaJugando.jugadores);
	}

	public void confirmarInicio(){
		if(this.estado.getEstadoActual() == EstadosJugador.CONFIRMANDOINICIO){
			partidaJugando.eventos.removerObservador(EventosPartida.PEDIRCONFIRMACION, this);
		}
	}

	public void cancelarInicio(){
		if(this.estado.getEstadoActual() == EstadosJugador.CONFIRMANDOINICIO){
			partidaJugando.eventos.cancelarEvento(EventosPartida.PEDIRCONFIRMACION);
		}
	}

	public void onComienzoTurno(Carta cartaRobada) {
		this.estaProtegido = false;
		carta2 = cartaRobada;

		if (carta2.getTipo() == CartaTipo.CONDESA
				&& (carta1.getTipo() == CartaTipo.REY || carta1.getTipo() == CartaTipo.PRINCIPE)) {
			this.estado.setEstadoActual(EstadosJugador.DESCARTANDOCONDESA);
		}
		else
			this.estado.setEstadoActual(EstadosJugador.DESCARTANDO);
	}

	public boolean descartarCarta1() {
		if(this.estado.getEstadoActual() == EstadosJugador.DESCARTANDO) {
			Carta cartaJugada = carta1;
			carta1 = carta2;
			carta2 = null;
			this.estado.cartaDescartada(cartaJugada);
			return true;
		}
		return false;
	}

	public boolean descartarCarta2() {
		if(this.estado.getEstadoActual() == EstadosJugador.DESCARTANDO) {
			Carta cartaJugada = carta2;
			carta2 = null;
			this.estado.cartaDescartada(cartaJugada);
			return true;
		}
		if(this.estado.getEstadoActual() == EstadosJugador.DESCARTANDOCONDESA){
			Carta cartaJugada = carta2;
			carta2 = null;
			cartaJugada.descartar(this);
			return true;
		}
		return false;
	}

	/**
	 * Roba una carta del mazo al principio del turno
	 */
	public void robarCarta(Carta cartaRobada) {
		carta2 = cartaRobada;
		
		if (carta2.getTipo() == CartaTipo.CONDESA
				&& (carta1.getTipo() == CartaTipo.REY || carta1.getTipo() == CartaTipo.PRINCIPE)) {
			this.estado.setEstadoActual(EstadosJugador.DESCARTANDOCONDESA);
		}
		else
			this.estado.setEstadoActual(EstadosJugador.DESCARTANDO);
	}

	/**
	 * LLamado por el jugador cuando elige a otro para el evento
	 * @param jugador jugador elegido para el evento
	 */
	public void elegirJugador(Jugador jugador){
		if(this.estado.getEstadoActual() == EstadosJugador.ELIGIENDOJUGADOR) {
			if(!jugador.estaProtegido) {
				this.estado.jugadorElegido(jugador);
			}
		}
	}

	public void elegirCarta(CartaTipo cartaAdivinada){
		if(this.estado.getEstadoActual() == EstadosJugador.ADIVINANDOCARTA) {
			this.estado.cartaAdivinada(cartaAdivinada);
		}
	}
	/**
	 * Llamado por varias cartas al ser descartadas.
	 * Ve la carta que el jugador pasado por parametro tiene.
	 * @param jugador jugador al cual this le ve las cartas
	 */
	public void verCarta(Jugador jugador){
		System.out.println(this + " ve la carta de " + jugador + ":" + jugador.carta1);
	}

	/**
	 * Generalmente llamado por otro jugador cuando descarta al guardia
	 */
	public boolean tieneCarta(CartaTipo tipo) {
		//tecnicamente tiene carta2 solo en tests
		if(carta2 != null){
			return (carta1.getTipo() == tipo || carta2.getTipo() == tipo);
		} else return carta1.getTipo() == tipo;

	}

	public int getFuerzaCarta() {
		return carta1.getTipo().fuerza;
	}

	public JugadorFacade getEstado() {
		return estado;
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
