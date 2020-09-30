package equipoalpha.loveletter.partida;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;

import equipoalpha.loveletter.carta.Carta;
import equipoalpha.loveletter.carta.CartaTipo;

public class Partida {

	/*
	 * Conjunto de 16 cartas al empezar, termina la ronda si se vacia
	 */
	private LinkedList<Carta> mazo;

	/*
	 * El jugador que creo la partida
	 */
	public Jugador creador;

	/*
	 * Conjunto de jugadores, se necesitan 2-4 para empezar la partida. El creador
	 * de la partida se agrega por defecto.
	 */
	public ArrayList<Jugador> jugadores;

	/*
	 * Cantidad de rondas que tiene la partida
	 */
	public int ronda = 0;

	/*
	 * Cantidad de simbolos de afecto que en total se pueden repartir durante la
	 * partida
	 */
	private int cantSimbolosAfecto = 0;
	
	/*
	 * El jugador que empieza el primer turno.
	 * Para la primer ronda es configurado por el creador. 
	 * Para las demas rondas se elige al ganador de la ronda anterior.
	 */
	private Jugador jugadorMano;
	
	/*
	 * Carta que se elimina al principio de la ronda
	 */
	private Carta cartaEliminada;

	/*
	 * Indica si la partida esta en curso, en caso de ser true no se pueden unir mas
	 * jugadores
	 */
	public boolean partidaEnCurso = false;

	// TODO Condiciones de la partida?
	// La partida podrá ser iniciada por el creador de la sala, o cuando todos los
	// jugadores estén listos, o cualquier otra condición que consideren
	// cantidad de símbolos de afecto para ganar, que jugador comienza la ronda, y
	// cual es el orden de la ronda
	public Partida(Jugador creador) {
		this.creador = creador;
		this.mazo = new LinkedList<Carta>();
		this.jugadores = new ArrayList<Jugador>();
		jugadores.add(creador);
	}
	
	public void initPartida() {
		if(cantSimbolosAfecto == 0 || jugadorMano == null)
			return;
		partidaEnCurso = true;
		for (Jugador jugador : jugadores) {
			jugador.partidaJugando = this;
			jugador.cantSimbolosAfecto = 0;
			jugador.estaProtegido = false;
		}
	}

	/*
	 * Inicializa la ronda. Crea el mazo, elimina 1 carta, reparte 1 carta a cada jugador.
	 * Asigna el turno al jugador seleccionado para empezar en el caso de ser el primer turno.
	 * Asigna el turno al jugador que gano el anterior turno.
	 */
	private void initRonda() {
		ronda++;
		initMazo();
		cartaEliminada = mazo.remove();
		ListIterator<Jugador> iterador = jugadores.listIterator(jugadores.indexOf(jugadorMano));
		while (iterador.hasNext()) {
			Jugador jugadorIterando = iterador.next();
			jugadorIterando.carta1 = mazo.remove();
		}
	}

	private void initMazo() {
		mazo.clear();
		for (CartaTipo tipo : CartaTipo.values()) {
			for (int i = 0; i < tipo.cantCartas; i++) {
				mazo.add(new Carta(tipo));
			}
		}
		Collections.shuffle(mazo);
	}
	
	/*
	 * Agrega jugadores a la partida
	 * @return false cuando ya hay 4 jugadores en la partida
	 */
	public boolean agregarJugador(Jugador jugadorAAgregar) {
		if (jugadores.size() < 4) {
			jugadores.add(jugadorAAgregar);
			jugadorAAgregar.partidaJugando = this;
			return true;
		}
		return false;
	}
	
	/*
	 * Setters para la configuracion de la partida.
	 * Si la partida esta en curso no se pueden modificar.
	 */
	public void setCantSimbolosAfecto(int cantSimbolosAfecto) {
		if(!partidaEnCurso || cantSimbolosAfecto < 15 || cantSimbolosAfecto > 100) //TODO: rango decente
			return;
		this.cantSimbolosAfecto = cantSimbolosAfecto;
	}

	public void setJugadorMano(Jugador jugadorMano) {
		if(!partidaEnCurso || !jugadores.contains(jugadorMano))
			return;
		this.jugadorMano = jugadorMano;
	}
	
	
}
