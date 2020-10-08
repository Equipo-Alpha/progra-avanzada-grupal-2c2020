package equipoalpha.loveletter.partida;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;

import equipoalpha.loveletter.carta.Carta;
import equipoalpha.loveletter.carta.CartaTipo;

public class Ronda {
	/**
	 * La partida a la que pertence esta ronda.
	 */
	private final Partida partida;

	/**
	 * Conjunto de 16 cartas al empezar, termina la ronda si se vacia
	 */
	private LinkedList<Carta> mazo;

	/**
	 * Jugadores que estan jugando en la ronda actual y no han sido eliminados.
	 */
	public ArrayList<Jugador> jugadoresEnLaRonda;

	/**
	 * Mapa de la cantidad de cartas que descarto cada jugador durante la ronda. Es
	 * importante en caso de desempate al finalizar la ronda.
	 */
	protected Map<Jugador, Integer> mapaCartasEliminadas;

	/**
	 * Carta que se elimina al principio de la ronda
	 */
	protected Carta cartaEliminada;

	public Ronda(Partida partida) {
		this.partida = partida;
	}
	
	// TODO Cual es el orden de la ronda?
	public Jugador initRonda() {
		this.mazo = new LinkedList<Carta>();

		// ---------Inicializar Mazo -----------\\
		mazo.clear();
		for (CartaTipo tipo : CartaTipo.values()) {
			for (int i = 0; i < tipo.cantCartas; i++) {
				mazo.add(new Carta(tipo));
			}
		}
		Collections.shuffle(mazo);

		this.jugadoresEnLaRonda = new ArrayList<Jugador>();
		this.mapaCartasEliminadas = new HashMap<Jugador, Integer>();

		// ---------Repartir Cartas--------------\\
		jugadoresEnLaRonda.clear();
		cartaEliminada = mazo.remove();
		ListIterator<Jugador> iterador = partida.jugadores.listIterator(partida.jugadores.indexOf(partida.jugadorMano));
		while (iterador.hasNext()) {
			Jugador jugadorIterando = iterador.next();
			jugadorIterando.carta1 = mazo.remove();
			jugadorIterando.rondaJugando = this;
			jugadoresEnLaRonda.add(jugadorIterando);
			mapaCartasEliminadas.put(jugadorIterando, 0);
		}

		// Loop de ronda
		while (!rondaTerminada()) {
			for (Jugador jugador : jugadoresEnLaRonda) {
				System.out.println("Es el turno de " + jugador);
				jugador.robarCarta(mazo.remove());
				jugador.onComienzoTurno();
			}
		}

		return onRondaTerminada();
	}

	private Jugador onRondaTerminada() {

		// Si solo queda 1 devuelve a ese jugador
		if (jugadoresEnLaRonda.size() == 1)
			return jugadoresEnLaRonda.get(0);

		// Sino, busca a el jugador que tiene en su mano la carta con valor numérico más
		// alto (fuerza)
		int mayor = 0;
		for (int i = 0; i < jugadoresEnLaRonda.size(); i++) {
			if (jugadoresEnLaRonda.get(i).getFuerzaCarta() > mayor)
				mayor = jugadoresEnLaRonda.get(i).getFuerzaCarta();
		}

		ArrayList<Jugador> jugadorCartaMasFuerte = new ArrayList<Jugador>();
		for (int i = 0; i < jugadoresEnLaRonda.size(); i++) {
			if (jugadoresEnLaRonda.get(i).getFuerzaCarta() == mayor)
				jugadorCartaMasFuerte.add(jugadoresEnLaRonda.get(i));
		}

		// Si no hay empate lo devuelve
		if (jugadorCartaMasFuerte.size() == 1)
			return jugadorCartaMasFuerte.get(0);

		int cantCartas = 0;
		Jugador jugadorMasCartasDescartadas = null;

		// Si hay empate, gana el jugador que haya jugado/descartado más cartas en la
		// ronda
		for (int i = 0; i < jugadorCartaMasFuerte.size(); i++) {
			if (mapaCartasEliminadas.get(jugadorCartaMasFuerte.get(i)) > cantCartas) {
				cantCartas = mapaCartasEliminadas.get(jugadorCartaMasFuerte.get(i));
				jugadorMasCartasDescartadas = jugadorCartaMasFuerte.get(i);
			}
		}

		return jugadorMasCartasDescartadas;
	}

	/**
	 * condicion de fin de ronda
	 * @return true cuando el mazo esta vacio o cuando queda 1 solo jugador sin
	 *         eliminar
	 */
	public boolean rondaTerminada() {
		if (mazo.isEmpty())
			return true;
		return jugadoresEnLaRonda.size() == 1;
	}
}
