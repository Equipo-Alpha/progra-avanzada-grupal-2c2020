package equipoalpha.loveletter.partida;

import java.util.ArrayList;

public class Partida {

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
	protected Jugador jugadorMano;

	/**
	 * Indica si la partida esta en curso, en caso de ser true no se pueden unir mas
	 * jugadores
	 */
	public boolean partidaEnCurso = false;
	
	public Partida(Jugador creador) {
		this.creador = creador;
		this.jugadores = new ArrayList<Jugador>();
		jugadores.add(creador);
	}
	
	// TODO Condiciones de la partida?
	// La partida podrá ser iniciada por el creador de la sala, o cuando todos los
	// jugadores estén listos, o cualquier otra condición que consideren
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

		while (!partidaTerminada()) {
			Ronda rondaActual = new Ronda(this);
			ronda++;
			System.out.println("Comienza la ronda numero " + ronda);
			jugadorMano = rondaActual.initRonda();
			System.out.println("El ganador de la ronda es " + jugadorMano);
			jugadorMano.cantSimbolosAfecto++;
			this.cantSimbolosAfecto--;
		}

		System.out.println("EL GANADOR DE LA PARTIDA ES + " + jugadorMano);
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
