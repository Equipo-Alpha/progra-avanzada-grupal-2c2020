package equipoalpha.loveletter.partida;

import equipoalpha.loveletter.carta.Carta;
import equipoalpha.loveletter.carta.CartaTipo;
import equipoalpha.loveletter.jugador.EstadosJugador;
import equipoalpha.loveletter.jugador.Jugador;
import equipoalpha.loveletter.jugador.JugadorIA;

import java.util.*;

public class Ronda {
    /**
     * La partida a la que pertence esta ronda.
     */
    private final Partida partida;
    /**
     * Jugadores que estan jugando en la ronda actual y no han sido eliminados.
     */
    public ArrayList<Jugador> jugadoresEnLaRonda;
    public Jugador jugadorEnTurno;
    /**
     * Mapa de la cantidad de cartas que descarto cada jugador durante la ronda. Es
     * importante en caso de desempate al finalizar la ronda.
     */
    public Map<Jugador, Integer> mapaCartasEliminadas;
    /**
     * Mapa de las cartas que descarto cada jugador durante la ronda.
     */
    public Map<Jugador, ArrayList<Carta>> mapaCartasDescartadas;
    /**
     * Carta que se elimina al principio de la ronda
     */
    public Carta cartaEliminada;
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
        this.mapaCartasEliminadas = new HashMap<>();
        this.mapaCartasDescartadas = new HashMap<>();

        // ---------Repartir Cartas--------------\\
        cartaEliminada = mazo.remove();
        for (Jugador jugadorIterando : partida.jugadores) {
            jugadorIterando.carta1 = mazo.remove();
            jugadorIterando.rondaJugando = this;
            jugadoresEnLaRonda.add(jugadorIterando);
            jugadorIterando.getEstado().resetElecciones();
            mapaCartasEliminadas.put(jugadorIterando, 0);
            mapaCartasDescartadas.put(jugadorIterando, new ArrayList<>());
            jugadorIterando.getEstado().setEstadoActual(EstadosJugador.ESPERANDO);
            if (jugadorIterando instanceof JugadorIA) {
                ((JugadorIA) jugadorIterando).inicioRonda();
            }
        }

        System.out.println("Empezando Ronda");

        jugadorEnTurno = partida.jugadorMano;
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
        Jugador jugadorGanador = determinarGanador();
        jugadorGanador.cantSimbolosAfecto++;
        System.out.println("El ganador de la ronda es: " + jugadorGanador);
        partida.onNuevaRonda(jugadorGanador);
    }

    private Jugador determinarGanador() {

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

    public Carta darCarta() {
        if (mazo.isEmpty()) return null;
        return mazo.remove();
    }

    public Carta darCartaEliminada() {
        return cartaEliminada;
    }

    public void eliminarJugador(Jugador jugador) {
        ArrayList<Carta> ALC = mapaCartasDescartadas.remove(jugador);
        ALC.add(jugador.carta1);
        for (Jugador j : this.jugadoresEnLaRonda)
            if (j instanceof JugadorIA)
                ((JugadorIA) j).agregarCartaJugadorEliminado(jugador.carta1);

        if (jugador.carta2 != null) {
            ALC.add(jugador.carta2);
            for (Jugador j : this.jugadoresEnLaRonda)
                if (j instanceof JugadorIA)
                    ((JugadorIA) j).agregarCartaJugadorEliminado(jugador.carta2);
        }
        mapaCartasDescartadas.put(jugador, ALC);
        jugadoresEnLaRonda.remove(jugador);
        mapaCartasEliminadas.remove(jugador);
        jugador.rondaJugando = null;
        jugador.getEstado().setEstadoActual(EstadosJugador.ESPERANDO);
    }

    public void eliminarJugadorEnTurno(Jugador jugador) {
        eliminarJugador(jugador);
        onFinalizarDescarte(jugador);
    }

    public void determinarCartaMayor(Jugador jugador1, Jugador jugador2) {
        if (jugador1.carta1.getTipo().fuerza > jugador2.carta1.getTipo().fuerza) {
            eliminarJugador(jugador2);
            jugador2.rondaJugando = null;
        }
        if (jugador1.carta1.getTipo().fuerza < jugador2.carta1.getTipo().fuerza)
            eliminarJugador(jugador1);
    }

    public void onFinalizarDescarte(Jugador jugador) {
        if (jugadoresEnLaRonda.contains(jugador)) {
            int nuevaCantidad = mapaCartasEliminadas.remove(jugador);
            nuevaCantidad++;
            mapaCartasEliminadas.putIfAbsent(jugador, nuevaCantidad);
        } else {
            jugador.rondaJugando = null;
        }

        for (Jugador jugador1 : jugadoresEnLaRonda) {
            if (jugador1 instanceof JugadorIA) {
                ((JugadorIA) jugador1).finTurno(jugador, jugador.getEstado().getCartaDescartada());
            }
        }

        jugador.getEstado().setEstadoActual(EstadosJugador.ESPERANDO);
        jugador.getEstado().resetElecciones();

        if (rondaTerminada()) {
            onRondaTerminada();
            return;
        }

        ListIterator<Jugador> iterador = partida.jugadores.listIterator();
        Jugador jugadorIterando = iterador.next();
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
    }

    /**
     * LLamado al descartar una carta que requiera elegir a otro jugador.
     *
     * @param jugador jugador que descarto la carta
     * @param carta   la carta que descarto el jugador
     * @return false si no hay jugador disponible para elegir
     */
    public boolean puedeElegir(Jugador jugador, CartaTipo carta) {
        ArrayList<Jugador> jugadores = new ArrayList<>(jugadoresEnLaRonda);
        if (carta != CartaTipo.PRINCIPE) jugadores.remove(jugador);
        int cant = 0;
        for (Jugador j : jugadoresEnLaRonda) {
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
