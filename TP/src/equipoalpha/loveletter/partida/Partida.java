package equipoalpha.loveletter.partida;

import equipoalpha.loveletter.partida.eventos.ConfirmarInicioEvento;
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
	 * Si la partida empezo, es la ronda en la que actualmente se esta jugando
	 */
	public Ronda rondaActual = null;

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
		EventoObservado confirmarInicio = new ConfirmarInicioEvento(this);
		eventos.registrar(EventosPartida.PEDIRCONFIRMACION, confirmarInicio);
	}

	public void initPartida() {
		partidaEnCurso = true;

		for (Jugador jugador : jugadores) {
			jugador.getEstado().setEstadoActual(EstadosJugador.ESPERANDO);
			jugador.cantSimbolosAfecto = 0;
			jugador.estaProtegido = false;
		}

		this.rondaActual = new Ronda(this);
		onNuevaRonda(jugadorMano);
	}

	/**
	 * Evento que empieza al iniciar la partida o cuando la ronda anterior termino.
	 * Determina una nueva ronda a jugar
	 * @param ganadorRonda jugador que gano la ronda anterior
	 */
	public void onNuevaRonda(Jugador ganadorRonda){
		if(partidaTerminada()){
			onFinalizarPartida(ganadorRonda);
			return;
		}

		this.jugadorMano = ganadorRonda; // el que gano la anterior ronda es la nueva mano
		ronda++;
		rondaActual.initRonda();

	}

	/**
	 * Evento de fin de partida
	 * @param ganadorPartida el ganador de la partida
	 */
	private void onFinalizarPartida(Jugador ganadorPartida){
		partidaEnCurso = false;
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
	 * @return true cuando algun jugador llego a la cantidad de simbolos de afecto seteados o cuando
	 *         solo queda 1 jugador en la partida
	 */
	public boolean partidaTerminada() {
		if(jugadores.size() < 2)
			return true;
		for(Jugador jugador : this.jugadores){
			if (jugador.cantSimbolosAfecto == this.cantSimbolosAfecto)
				return true;
		}
		return false;
	}

	/**
	 * Setters para la configuracion de la partida. Si la partida esta en curso no
	 * se pueden modificar.
	 */
	public void setCantSimbolosAfecto(int cantSimbolosAfecto) {
		if (partidaEnCurso || cantSimbolosAfecto < 2 || cantSimbolosAfecto > 7)
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
