package equipoalpha.loveletter.partida;

import equipoalpha.loveletter.carta.Carta;
import equipoalpha.loveletter.carta.CartaTipo;
import equipoalpha.loveletter.jugador.EstadosJugador;
import equipoalpha.loveletter.jugador.Jugador;
import equipoalpha.loveletter.jugador.JugadorIA;
import equipoalpha.loveletter.server.JugadorServer;

import java.util.*;

public class Ronda {
    /**
     * La partida a la que pertence esta ronda.
     */
    private final Partida partida;
    /**
     * Jugadores que estan jugando en la ronda actual y no han sido eliminados.
     */
    public ArrayList<JugadorServer> jugadoresEnLaRonda;
    public JugadorServer jugadorEnTurno;
    /**
     * Mapa de las cartas que descarto cada jugador durante la ronda.
     */
    public Map<JugadorServer, ArrayList<Carta>> mapaCartasDescartadas;
    /**
     * Carta que se elimina al principio de la ronda
     */
    public Carta cartaEliminada;
    public boolean turnosIniciados;
    public LinkedList<JugadorServer> ordenReparto;
    /**
     * Conjunto de 16 cartas al empezar, termina la ronda si se vacia
     */
    private LinkedList<Carta> mazo;

    public Ronda(Partida partida) {
        this.partida = partida;
    }

    public void initRonda() {
        initMazo();

        this.jugadoresEnLaRonda = new ArrayList<>();
        this.mapaCartasDescartadas = new HashMap<>();
        this.ordenReparto = new LinkedList<>();

        jugadorEnTurno = partida.jugadorMano;

        ListIterator<JugadorServer> iterador = partida.jugadores.listIterator();
        JugadorServer jugadorIterando = iterador.next();
        while (jugadorIterando != jugadorEnTurno) {
            jugadorIterando = iterador.next();
        }
        do {
            ordenReparto.add(jugadorIterando);
            if (!iterador.hasNext()) {
                iterador = partida.jugadores.listIterator();
            }
            jugadorIterando = iterador.next();
        } while (jugadorIterando != jugadorEnTurno);

        // ---------Repartir Cartas--------------\\
        cartaEliminada = mazo.remove();
        for (JugadorServer jugador : ordenReparto) {
            jugador.carta1 = mazo.remove();
            jugador.rondaJugando = this;
            jugadoresEnLaRonda.add(jugador);
            jugador.getEstado().resetElecciones();
            mapaCartasDescartadas.put(jugador, new ArrayList<>());
            jugador.getEstado().setEstadoActual(EstadosJugador.ESPERANDO);
            if (jugador instanceof JugadorIA) {
                ((JugadorIA) jugador).inicioRonda();
            }
        }

        System.out.println("Empezando Ronda");

        turnosIniciados = false;
    }

    public void initTurnos() {
        turnosIniciados = true;
        jugadorEnTurno.onComienzoTurno(darCarta());
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

    private void onRondaTerminada() {
        JugadorServer jugadorGanador = determinarGanador();
        jugadorGanador.cantSimbolosAfecto++;
        System.out.println("El ganador de la ronda es: " + jugadorGanador);
        partida.onNuevaRonda(jugadorGanador);
    }

    private JugadorServer determinarGanador() {

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

        ArrayList<JugadorServer> jugadorCartaMasFuerte = new ArrayList<>();
        for (JugadorServer jugador : jugadoresEnLaRonda) {
            if (jugador.getFuerzaCarta() == mayor)
                jugadorCartaMasFuerte.add(jugador);
        }

        // Si no hay empate lo devuelve
        if (jugadorCartaMasFuerte.size() == 1)
            return jugadorCartaMasFuerte.get(0);

        int cantCartas = 0;
        JugadorServer jugadorMasCartasDescartadas = null;

        // Si hay empate, gana el jugador que haya descartado mas cartas en la ronda
        for (JugadorServer jugador : jugadorCartaMasFuerte) {
            if (mapaCartasDescartadas.get(jugador).size() > cantCartas) {
                cantCartas = mapaCartasDescartadas.get(jugador).size();
                jugadorMasCartasDescartadas = jugador;
            }
        }

        return jugadorMasCartasDescartadas;
    }

    public Carta darCarta() {
        if (mazo.isEmpty()) return null;
        return mazo.remove();
    }

    public Carta darCartaEliminada() {
        return cartaEliminada;
    }

    public void eliminarJugador(JugadorServer jugador) {
        mapaCartasDescartadas.get(jugador).add(jugador.carta1);
        for (Jugador j : this.jugadoresEnLaRonda)
            if (j instanceof JugadorIA)
                ((JugadorIA) j).agregarCartaJugadorEliminado(jugador.carta1);

        if (jugador.carta2 != null) {
            mapaCartasDescartadas.get(jugador).add(jugador.carta2);
            for (Jugador j : this.jugadoresEnLaRonda)
                if (j instanceof JugadorIA)
                    ((JugadorIA) j).agregarCartaJugadorEliminado(jugador.carta2);
        }
        jugadoresEnLaRonda.remove(jugador);
        jugador.rondaJugando = null;
        jugador.carta1 = null;
        jugador.carta2 = null;
        jugador.getEstado().setEstadoActual(EstadosJugador.ESPERANDO);
    }

    public void eliminarJugadorEnTurno(JugadorServer jugador) {
        eliminarJugador(jugador);
        onFinalizarDescarte(jugador);
    }

    public void determinarCartaMayor(JugadorServer jugador1, JugadorServer jugador2) {
        if (jugador1.carta1.getTipo().fuerza > jugador2.carta1.getTipo().fuerza) {
            eliminarJugador(jugador2);
            return;
        }
        if (jugador1.carta1.getTipo().fuerza < jugador2.carta1.getTipo().fuerza)
            eliminarJugador(jugador1);
    }

    public void onFinalizarDescarte(JugadorServer jugador) {
        for (Jugador jugador1 : jugadoresEnLaRonda) {
            if (jugador1 instanceof JugadorIA) {
                ((JugadorIA) jugador1).finTurno(jugador, jugador.getEstado().getCartaDescartada());
            }
        }

        jugador.getEstado().setEstadoActual(EstadosJugador.ESPERANDO);
        jugador.getEstado().resetElecciones();

        for (JugadorServer j : this.partida.jugadores) {
            j.sincronizarPartida();
        }

        if (rondaTerminada()) {
            onRondaTerminada();
            return;
        }

        ListIterator<JugadorServer> iterador = partida.jugadores.listIterator();
        JugadorServer jugadorIterando = iterador.next();
        while (jugadorIterando != jugadorEnTurno) {
            jugadorIterando = iterador.next();
        }
        do {
            if (!iterador.hasNext()) {
                iterador = partida.jugadores.listIterator();
            }
            jugadorEnTurno = iterador.next();
        } while (!jugadoresEnLaRonda.contains(jugadorEnTurno));

        jugadorEnTurno.onComienzoTurno(darCarta());
        for (JugadorServer j : this.partida.jugadores) {
            j.sincronizarPartida();
        }
    }

    /**
     * LLamado al descartar una carta que requiera elegir a otro jugador.
     *
     * @param jugador jugador que descarto la carta
     * @param carta   la carta que descarto el jugador
     * @return false si no hay jugador disponible para elegir
     */
    public boolean puedeElegir(JugadorServer jugador, CartaTipo carta) {
        ArrayList<Jugador> jugadores = new ArrayList<>(jugadoresEnLaRonda);
        if (carta != CartaTipo.PRINCIPE) jugadores.remove(jugador);
        int cant = 0;
        for (JugadorServer j : jugadoresEnLaRonda) {
            if (j.estaProtegido) cant++;
        }
        return jugadores.size() != cant;
    }

    /**
     * condicion de fin de ronda
     *
     * @return true cuando el mazo esta vacio o cuando queda 1 solo jugador sin
     * eliminar
     */
    public boolean rondaTerminada() {
        return (mazo.isEmpty() || jugadoresEnLaRonda.size() == 1);
    }

    public boolean mazoVacio() {
        return mazo.isEmpty();
    }

    public int cantCartas() {
        return mazo.size();
    }

    //@TestOnly
    public void vaciarMazo() {
        mazo.clear();
    }
}
