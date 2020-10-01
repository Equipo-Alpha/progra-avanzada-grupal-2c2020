package equipoalpha.loveletter.partida;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;

import equipoalpha.loveletter.carta.Carta;
import equipoalpha.loveletter.carta.CartaTipo;

public class Partida {

	/**
	 * Conjunto de 16 cartas al empezar, termina la ronda si se vacia
	 */
	private LinkedList<Carta> mazo;

	/**
	 * El jugador que creo la partida
	 */
	public Jugador creador;

	/**
	 * Conjunto de jugadores, se necesitan 2-4 para empezar la partida. El creador
	 * de la partida se agrega por defecto.
	 */
	public ArrayList<Jugador> jugadores;

	/**
	 * Jugadores que estan jugando en la ronda actual y no han sido eliminados.
	 */
	public ArrayList<Jugador> jugadoresEnLaRonda;

	/**
	 * Cantidad de rondas que tiene la partida
	 */
	public int ronda = 0;

	/**
	 * Cantidad de simbolos de afecto que en total se pueden repartir durante la
	 * partida
	 */
	private int cantSimbolosAfecto = 0;

	/**
	 * El jugador que empieza el primer turno. Para la primer ronda es configurado
	 * por el creador. Para las demas rondas se elige al ganador de la ronda
	 * anterior. En el caso de terminarse la partida este jugador seria el ganador.
	 */
	private Jugador jugadorMano;

	/**
	 * Jugador que actualmente tiene el turno
	 */
	protected Jugador jugadorTurno;

	/**
	 * Carta que se elimina al principio de la ronda
	 */
	private Carta cartaEliminada;

	/**
	 * Indica si la partida esta en curso, en caso de ser true no se pueden unir mas
	 * jugadores
	 */
	public boolean partidaEnCurso = false;

	// TODO Condiciones de la partida?
	// La partida podrá ser iniciada por el creador de la sala, o cuando todos los
	// jugadores estén listos, o cualquier otra condición que consideren
	// TODO Cual es el orden de la ronda?
	public Partida(Jugador creador) {
		this.creador = creador;
		this.mazo = new LinkedList<Carta>();
		this.jugadores = new ArrayList<Jugador>();
		jugadores.add(creador);
	}

	public void initPartida() {
		if (cantSimbolosAfecto == 0 || jugadorMano == null) {
			System.out.println("Faltan settear las condiciones de la partida!\n");
			return;
		}
		partidaEnCurso = true;
		for (Jugador jugador : jugadores) {
			jugador.partidaJugando = this;
			jugador.cantSimbolosAfecto = 0;
			jugador.estaProtegido = false;
		}
	}

	/**
	 * Inicializa la ronda. Crea el mazo, elimina 1 carta, reparte 1 carta a cada
	 * jugador. Asigna el turno al jugador seleccionado para empezar en el caso de
	 * ser la primer ronda sino asigna el turno al jugador que gano la anterior
	 * ronda.
	 */
	public void initRonda() {
		if (partidaTerminada()) {
			System.out.println("La partida termino!");
			System.out.println("Felicidades al ganador... " + jugadorMano + "!!");
			return;
		}

		ronda++;
		System.out.println("Comienza la ronda numero " + ronda);

		// ---------Inicializar Mazo -----------\\
		mazo.clear();
		for (CartaTipo tipo : CartaTipo.values()) {
			for (int i = 0; i < tipo.cantCartas; i++) {
				mazo.add(new Carta(tipo));
			}
		}
		Collections.shuffle(mazo);

		// ---------Repartir Cartas--------------\\
		jugadoresEnLaRonda.clear();
		cartaEliminada = mazo.remove();
		ListIterator<Jugador> iterador = jugadores.listIterator(jugadores.indexOf(jugadorMano));
		while (iterador.hasNext()) {
			Jugador jugadorIterando = iterador.next();
			jugadorIterando.carta1 = mazo.remove();
			jugadoresEnLaRonda.add(jugadorIterando);
		}

		System.out.println("Es el turno de " + jugadorMano);
		jugadorTurno = jugadorMano;

	}

	/**
	 * Llamado por un jugador cuando termina su turno. Le asigna el turno al
	 * siguiente.
	 */
	protected void onTurnoTerminado() {
		if (rondaTerminada()) {
			if (jugadoresEnLaRonda.size() == 1)
				for (Jugador jugador : jugadoresEnLaRonda) // Bueno solo hay 1
					jugadorMano = jugador;
			else {
				// TODO determinar ganador
			}
			System.out.println("La ronda termino, el ganador es " + jugadorMano);
			jugadorMano.cantSimbolosAfecto++;
			return;
		}
		int index = jugadoresEnLaRonda.indexOf(jugadorMano); // TODO esto puede dar null
		index++;
		if (index > jugadores.size())
			index = 0;
		jugadorTurno = jugadoresEnLaRonda.get(index);
		System.out.println("Es el turno de " + jugadorTurno);
	}

	/**
	 * Agrega jugadores a la partida.
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

	/**
	 * condicion de fin de partida
	 * @return true cuando ya no hay mas simbolos de afectos que repartir o cuando
	 *         solo queda 1 jugador en la partida
	 */
	public boolean partidaTerminada() {
		return (cantSimbolosAfecto == 0 || jugadores.size() < 2);
	}

	// TODO por el momento printea puntajes, habra que ver que mas se podria
	// printear o cambiarlo
	public void getDatosDeLaPartida() {
		System.out.println("Jugador\t\tPuntaje");
		for (Jugador jugador : jugadores) {
			System.out.println(jugador + "\t\t" + jugador.cantSimbolosAfecto);
		}
	}

	/**
	 * Setters para la configuracion de la partida. Si la partida esta en curso no
	 * se pueden modificar.
	 */
	public void setCantSimbolosAfecto(int cantSimbolosAfecto) {
		if (partidaEnCurso || cantSimbolosAfecto < 15 || cantSimbolosAfecto > 100) // TODO: rango decente
			return;
		this.cantSimbolosAfecto = cantSimbolosAfecto;
	}

	public void setJugadorMano(Jugador jugadorMano) {
		if (partidaEnCurso || !jugadores.contains(jugadorMano))
			return;
		this.jugadorMano = jugadorMano;
	}

}
