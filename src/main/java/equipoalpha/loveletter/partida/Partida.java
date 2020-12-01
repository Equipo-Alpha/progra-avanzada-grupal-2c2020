package equipoalpha.loveletter.partida;

import equipoalpha.loveletter.jugador.EstadosJugador;
import equipoalpha.loveletter.jugador.JugadorIA;
import equipoalpha.loveletter.partida.eventos.EventosPartida;
import equipoalpha.loveletter.server.JugadorServer;
import equipoalpha.loveletter.server.LoveLetterServidor;

import java.util.ArrayList;

public class Partida {
    /**
     * Cantidad de simbolos de afecto que en total se pueden repartir durante la
     * partida
     */
    private final int cantSimbolosAfecto;
    /**
     * Conjunto de jugadores, se necesitan 2-4 para empezar la partida. El creador
     * de la partida se agrega por defecto.
     */
    public ArrayList<JugadorServer> jugadores;
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
    protected JugadorServer jugadorMano;

    public Partida(ArrayList<JugadorServer> jugadores, JugadorServer jugadorMano, int cantSimbolosAfecto) {
        this.jugadores = jugadores;
        this.jugadorMano = jugadorMano;
        this.cantSimbolosAfecto = cantSimbolosAfecto;
        for (JugadorServer j : this.jugadores) {
            j.partidaJugando = this;
        }
    }

    public void initPartida() {
        this.partidaEnCurso = true;
        this.ronda = 0;

        for (JugadorServer jugador : jugadores) {
            jugador.getEstado().setEstadoActual(EstadosJugador.ESPERANDO);
            jugador.cantSimbolosAfecto = 0;
            jugador.estaProtegido = false;
        }

        LoveLetterServidor.log.info("Empezando Partida de la sala " + jugadorMano.salaActual);

        this.rondaActual = new Ronda(this);
    }

    /**
     * Evento que empieza al iniciar la partida o cuando la ronda anterior termino.
     * Determina una nueva ronda a jugar
     *
     * @param ganadorRonda jugador que gano la ronda anterior
     */
    public void onNuevaRonda(JugadorServer ganadorRonda) {
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
    private void onFinalizarPartida(JugadorServer ganadorPartida) {
        this.partidaEnCurso = false;
        LoveLetterServidor.log.info("La partida de la sala " + ganadorPartida.salaActual + " termino.");
        if (LoveLetterServidor.getINSTANCE() == null) return;
        actualizarHistorialJugadores(ganadorPartida);
        ganadorPartida.salaActual.eventos.ejecutar(EventosPartida.FINPARTIDA, this.jugadores);
    }

    private void actualizarHistorialJugadores(JugadorServer ganador) {
        ArrayList<JugadorServer> perdedores = new ArrayList<>(this.jugadores);
        perdedores.removeIf( j -> j instanceof JugadorIA);
        perdedores.remove(ganador);
        if (!(ganador instanceof JugadorIA)) {
            ganador.getData().setVictorias(ganador.getData().getVictorias() + 1);
            LoveLetterServidor.getINSTANCE().getBd().guardarDatosJugador(ganador.getData());
        }
        for (JugadorServer perdedor : perdedores) {
            perdedor.getData().setDerrotas(perdedor.getData().getDerrotas() + 1);
            LoveLetterServidor.getINSTANCE().getBd().guardarDatosJugador(perdedor.getData());
        }
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
        for (JugadorServer jugador : this.jugadores) {
            if (jugador.cantSimbolosAfecto == this.cantSimbolosAfecto)
                return true;
        }
        return false;
    }

    public JugadorServer getJugadorMano() {
        return jugadorMano;
    }
}
