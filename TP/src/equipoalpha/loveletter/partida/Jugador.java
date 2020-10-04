package equipoalpha.loveletter.partida;

import java.util.Scanner;

import equipoalpha.loveletter.carta.Carta;
import equipoalpha.loveletter.carta.CartaTipo;

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
	}

	/**
	 * Crea una partida
	 */
	public Partida crearPartida() {
		return new Partida(this);
	}

	public void onComienzoTurno() {
		System.out.println("Elegir una carta a descartar: 1 para la carta1, 2 para la carta2");
		System.out.println("Elige cualquier otro valor para abandonar la partida");
		System.out.println("Carta1: " + carta1 + ". Carta2: " + carta2);

		Scanner scanner = new Scanner(System.in);
		int cartaElegida = scanner.nextInt();
		scanner.close();
		if (cartaElegida == 1)
			descartarCarta1();
		else if (cartaElegida == 2)
			descartarCarta2();
		else
			abandonarPartida();
	}

	private void descartarCarta1() {
		carta1.descartar(this);
		carta1 = carta2;
		carta2 = null;
	}

	private void descartarCarta2() {
		carta2.descartar(this);
		carta2 = null;
	}

	/**
	 * Roba una carta del mazo al principio del turno
	 */
	public void robarCarta(Carta cartaRobada) {
		carta2 = cartaRobada;

		// TODO: mover esta logica?
		if (cartaRobada.getTipo() == CartaTipo.CONDESA
				&& (carta1.getTipo() == CartaTipo.REY || carta1.getTipo() == CartaTipo.PRINCIPE)) {
			descartarCarta2();
		}
	}

	/**
	 * Generalmente llamado por otro jugador cuando descarta al guardia
	 */
	public boolean tieneCarta(Carta carta) {
		return carta1.equals(carta);
	}

	public int getFuerzaCarta() {
		return carta1.getTipo().fuerza;
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
