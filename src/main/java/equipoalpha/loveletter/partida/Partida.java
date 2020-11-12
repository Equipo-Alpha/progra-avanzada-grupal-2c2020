package equipoalpha.loveletter.partida;

import equipoalpha.loveletter.jugador.EstadosJugador;
import equipoalpha.loveletter.jugador.Jugador;

import java.util.ArrayList;

public class Partida {
    /**
     * Cantidad de simbolos de afecto que en total se pueden repartir durante la
     * partida
     */
    private final int cantSimbolosAfecto;
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
     * Indica si la partida esta en curso, en caso de ser true no se pueden unir mas
     * jugadores
     */
    public boolean partidaEnCurso = false;
    /**
     * El jugador que empieza el primer turno. Para la primer ronda es configurado
     * por el creador. Para las demas rondas se elige al ganador de la ronda
     * anterior. En el caso de terminarse la partida este jugador seria el ganador.
     */
    protected Jugador jugadorMano;

    public Partida(Jugador jugador, ArrayList<Jugador> jugadores, Jugador jugadorMano, int cantSimbolosAfecto) {
        this.creador = jugador;
        this.jugadores = jugadores;
        this.jugadorMano = jugadorMano;
        this.cantSimbolosAfecto = cantSimbolosAfecto;
        for (Jugador j : this.jugadores) {
            j.partidaJugando = this;
        }
    }

    public void initPartida() {
        this.partidaEnCurso = true;
        this.ronda = 0;

        for (Jugador jugador : jugadores) {
            jugador.getEstado().setEstadoActual(EstadosJugador.ESPERANDO);
            jugador.cantSimbolosAfecto = 0;
            jugador.estaProtegido = false;
        }

        System.out.println("Empezando Partida");

        this.rondaActual = new Ronda(this);
        onNuevaRonda(this.jugadorMano);
    }

    /**
     * Evento que empieza al iniciar la partida o cuando la ronda anterior termino.
     * Determina una nueva ronda a jugar
     *
     * @param ganadorRonda jugador que gano la ronda anterior
     */
    public void onNuevaRonda(Jugador ganadorRonda) {
        this.jugadorMano = ganadorRonda; // el que gano la anterior ronda es la nueva mano

        if (partidaTerminada()) {
            onFinalizarPartida(ganadorRonda);
            return;
        }

        this.ronda++;
        this.rondaActual.initRonda(); //empieza una nueva ronda
    }

    /**
     * Evento de fin de partida
     *
     * @param ganadorPartida el ganador de la partida
     */
    private void onFinalizarPartida(Jugador ganadorPartida) {
        this.partidaEnCurso = false;
        System.out.println("El ganador de la partida es: " + ganadorPartida);
    }

    /**
     * condicion de fin de partida
     *
     * @return true cuando algun jugador llego a la cantidad de simbolos de afecto seteados o cuando
     * solo queda 1 jugador en la partida
     */
    public boolean partidaTerminada() {
        if (this.jugadores.size() < 2)
            return true;
        for (Jugador jugador : this.jugadores) {
            if (jugador.cantSimbolosAfecto == this.cantSimbolosAfecto)
                return true;
        }
        return false;
    }

    public Jugador getJugadorMano() {
        return jugadorMano;
    }
}
