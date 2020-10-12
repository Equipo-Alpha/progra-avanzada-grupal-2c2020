package equipoalpha.loveletter.partida;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;

import equipoalpha.loveletter.carta.Carta;
import equipoalpha.loveletter.carta.CartaTipo;
import equipoalpha.loveletter.partida.estadosjugador.EstadoEsperando;

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
	public void initRonda() {
		initMazo();

		this.jugadoresEnLaRonda = new ArrayList<>();
		this.mapaCartasEliminadas = new HashMap<>();

		// ---------Repartir Cartas--------------\\
		cartaEliminada = mazo.remove();
		ListIterator<Jugador> iterador = partida.jugadores.listIterator(partida.jugadores.indexOf(partida.jugadorMano));
		while (iterador.hasNext()) {
			Jugador jugadorIterando = iterador.next();
			jugadorIterando.carta1 = mazo.remove();
			jugadorIterando.rondaJugando = this;
			jugadoresEnLaRonda.add(jugadorIterando);
			mapaCartasEliminadas.put(jugadorIterando, 0);
			jugadorIterando.getEstado().setEstado(new EstadoEsperando());
		}

		// Loop de ronda
		//while (!rondaTerminada()) {
		//	for (Jugador jugador : jugadoresEnLaRonda) {
		//		darCarta(jugador);
		//		jugador.onComienzoTurno();
		//	}
		//}

	}
	
	private void initMazo() {
		this.mazo = new LinkedList<>();

		for (CartaTipo tipo : CartaTipo.values()) {
			for (int i = 0; i < tipo.cantCartas; i++) {
				mazo.add(new Carta(tipo));
			}
		}
		
		Collections.shuffle(mazo);
	}

	private Jugador onRondaTerminada() {

		// Si solo queda 1 devuelve a ese jugador
		if (jugadoresEnLaRonda.size() == 1)
			return jugadoresEnLaRonda.get(0);

		// Sino, busca a el jugador que tiene en su mano la carta con valor numerico mas
		// alto (fuerza)
		int mayor = 0;
		for (Jugador jugador : jugadoresEnLaRonda) {
			if (jugador.getFuerzaCarta() > mayor)
				mayor = jugador.getFuerzaCarta();
		}

		ArrayList<Jugador> jugadorCartaMasFuerte = new ArrayList<>();
		for (Jugador jugador : jugadoresEnLaRonda) {
			if (jugador.getFuerzaCarta() == mayor)
				jugadorCartaMasFuerte.add(jugador);
		}

		// Si no hay empate lo devuelve
		if (jugadorCartaMasFuerte.size() == 1)
			return jugadorCartaMasFuerte.get(0);

		int cantCartas = 0;
		Jugador jugadorMasCartasDescartadas = null;

		// Si hay empate, gana el jugador que haya descartado mas cartas en la ronda
		for (Jugador jugador : jugadorCartaMasFuerte) {
			if (mapaCartasEliminadas.get(jugador) > cantCartas) {
				cantCartas = mapaCartasEliminadas.get(jugador);
				jugadorMasCartasDescartadas = jugador;
			}
		}

		return jugadorMasCartasDescartadas;
	}

	public Carta darCarta(){
		return mazo.remove();
	}

	public void eliminarJugador(Jugador jugador){
		jugadoresEnLaRonda.remove(jugador);
		mapaCartasEliminadas.remove(jugador);
	}

	public void determinarCartaMayor(Jugador jugador1, Jugador jugador2){
		if(jugador1.carta1.getTipo().fuerza > jugador2.carta1.getTipo().fuerza) {
			eliminarJugador(jugador2);
			jugador2.rondaJugando = null;
		}
		if(jugador1.carta1.getTipo().fuerza < jugador2.carta1.getTipo().fuerza)
			eliminarJugador(jugador1);
	}

	public void onFinalizarDescarte(Jugador jugador){
		if(jugadoresEnLaRonda.contains(jugador)) {
			int nuevaCantidad = mapaCartasEliminadas.remove(jugador);
			nuevaCantidad++;
			mapaCartasEliminadas.putIfAbsent(jugador, nuevaCantidad);
		}
		else {
			jugador.rondaJugando = null;
		}
		jugador.getEstado().setEstado(new EstadoEsperando());
		
		if(rondaTerminada()) {
			onRondaTerminada();
		}
	}

	/**
	 * condicion de fin de ronda
	 * @return true cuando el mazo esta vacio o cuando queda 1 solo jugador sin
	 *         eliminar
	 */
	public boolean rondaTerminada() {
		return (mazo.isEmpty() || jugadoresEnLaRonda.size() == 1);
	}
}
