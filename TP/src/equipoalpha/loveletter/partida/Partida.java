package equipoalpha.loveletter.partida;

import equipoalpha.loveletter.partida.eventos.EventoObservado;
import equipoalpha.loveletter.partida.eventos.EventosPartida;
import equipoalpha.loveletter.partida.eventos.EventosPartidaManager;

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

	public final EventosPartidaManager eventos;
	
	public Partida(Jugador creador) {
		this.creador = creador;
		creador.partidaJugando = this;
		this.jugadores = new ArrayList<>();
		jugadores.add(creador);
		this.eventos = new EventosPartidaManager();
		EventosPartida evento = new EventosPartida();
		EventoObservado confirmarInicio = evento::onPedirConfirmacion;
		eventos.registrar(EventosPartida.Nombre.PEDIRCONFIRMACION, confirmarInicio);
	}

	public void initPartida() {
		//TODO borrar este check
		if (cantSimbolosAfecto == 0 || jugadorMano == null) {
			return;
		}
		partidaEnCurso = true;
		for (Jugador jugador : jugadores) {
			jugador.getEstado().setEstadoActual(EstadosJugador.ESPERANDO);
			jugador.cantSimbolosAfecto = 0;
			jugador.estaProtegido = false;
		}

		//while (!partidaTerminada()) {
		//	Ronda rondaActual = new Ronda(this);
		//	ronda++;
		//	rondaActual.initRonda();
		//	jugadorMano.cantSimbolosAfecto++;
		//	this.cantSimbolosAfecto--;
		//}
	}

	/**
	 * Agrega jugadores a la partida.
	 * @return false cuando ya hay 4 jugadores en la partida
	 */
	public boolean agregarJugador(Jugador jugadorAAgregar) {
		if (jugadores.size() < 4 && !jugadores.contains(jugadorAAgregar)) {
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

	/**
	 * Setters para la configuracion de la partida. Si la partida esta en curso no
	 * se pueden modificar.
	 */
	public void setCantSimbolosAfecto(int cantSimbolosAfecto) {
		if (partidaEnCurso || cantSimbolosAfecto < 15 || cantSimbolosAfecto > 100) // TODO: rango decente
			return;
		this.cantSimbolosAfecto = cantSimbolosAfecto;
	}

	public int getCantSimbolosAfecto() {
		return cantSimbolosAfecto;
	}

	public void setJugadorMano(Jugador jugadorMano) {
		if (partidaEnCurso || !jugadores.contains(jugadorMano))
			return;
		this.jugadorMano = jugadorMano;
	}

	public Jugador getJugadorMano() {
		return jugadorMano;
	}
}
