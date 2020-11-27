package equipoalpha.loveletter.partida;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import equipoalpha.loveletter.carta.Carta;
import equipoalpha.loveletter.carta.CartaTipo;
import equipoalpha.loveletter.jugador.EstadosJugador;
import equipoalpha.loveletter.jugador.Jugador;
import equipoalpha.loveletter.jugador.JugadorIA;
import equipoalpha.loveletter.partida.eventos.EventosPartida;
import equipoalpha.loveletter.server.JugadorServer;
import equipoalpha.loveletter.server.LoveLetterServidor;

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
    public LinkedList<JugadorServer> ordenReparto;
    /**
     * Conjunto de 16 cartas al empezar, termina la ronda si se vacia
     */
    private LinkedList<Carta> mazo;
    private String motivoFin;
    private JsonObject jsonMotivoFin;
    private JugadorServer ganadorRonda;

    public Ronda(Partida partida) {
        this.partida = partida;
    }

    public void initRonda() {
        initMazo();

        this.jugadoresEnLaRonda = new ArrayList<>(this.partida.jugadores);
        this.mapaCartasDescartadas = new HashMap<>();
        this.ordenReparto = new LinkedList<>();
        this.motivoFin = "";
        this.ganadorRonda = null;
        this.jsonMotivoFin = null;

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
            jugador.getEstado().resetElecciones();
            mapaCartasDescartadas.put(jugador, new ArrayList<>());
            jugador.getEstado().setEstadoActual(EstadosJugador.ESPERANDO);
            if (jugador instanceof JugadorIA) {
                ((JugadorIA) jugador).inicioRonda();
            }
        }

        if (LoveLetterServidor.getINSTANCE() == null) return;
        actualizarJugadores();
        partida.jugadorMano.salaActual.eventos.ejecutar(EventosPartida.COMIENZORONDA, this.jugadoresEnLaRonda);
    }

    public void initTurnos() {
        jugadorEnTurno.onComienzoTurno(darCarta());
        actualizarJugadores();
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
        if (LoveLetterServidor.getINSTANCE() == null)
            partida.onNuevaRonda(jugadorGanador);
        else {
            this.ganadorRonda = jugadorGanador;
            JsonObject motivo = new JsonObject();
            motivo.addProperty("motivo", motivoFin);
            motivo.addProperty("ganador", jugadorGanador.nombre);
            switch (motivoFin) {
                case "Solo queda un jugador":
                    break;
                case "Valor de carta mas alto":
                    motivo.add("carta", new Gson().toJsonTree(jugadorGanador.carta1, Carta.class));
                    break;
                case "Cantidad de cartas descartadas":
                    motivo.addProperty("cantidad", mapaCartasDescartadas.get(jugadorGanador).size());
                    break;
            }
            this.jsonMotivoFin = motivo;
            jugadorGanador.salaActual.eventos.ejecutar(EventosPartida.FINRONDA, jugadorGanador.salaActual.jugadores);
        }
    }

    private JugadorServer determinarGanador() {

        // Si solo queda 1 devuelve a ese jugador
        if (jugadoresEnLaRonda.size() == 1) {
            this.motivoFin = "Solo queda un jugador";
            return jugadoresEnLaRonda.get(0);
        }

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
        if (jugadorCartaMasFuerte.size() == 1) {
            this.motivoFin = "Valor de carta mas alto";
            return jugadorCartaMasFuerte.get(0);
        }

        int cantCartas = 0;
        JugadorServer jugadorMasCartasDescartadas = null;

        // Si hay empate, gana el jugador que haya descartado mas cartas en la ronda
        for (JugadorServer jugador : jugadorCartaMasFuerte) {
            if (mapaCartasDescartadas.get(jugador).size() > cantCartas) {
                cantCartas = mapaCartasDescartadas.get(jugador).size();
                jugadorMasCartasDescartadas = jugador;
            }
        }

        this.motivoFin = "Cantidad de cartas descartadas";
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
        actualizarJugadores();
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

        actualizarJugadores();

        if (rondaTerminada()) {
            onRondaTerminada();
            return;
        }

        ListIterator<JugadorServer> iterador = this.ordenReparto.listIterator();
        JugadorServer jugadorIterando = iterador.next();
        while (jugadorIterando != jugadorEnTurno) {
            jugadorIterando = iterador.next();
        }
        do {
            if (!iterador.hasNext()) {
                iterador = this.ordenReparto.listIterator();
            }
            jugadorEnTurno = iterador.next();
        } while (!jugadoresEnLaRonda.contains(jugadorEnTurno));

        jugadorEnTurno.onComienzoTurno(darCarta());
        actualizarJugadores();
    }

    public void actualizarJugadores() {
        for (JugadorServer j : this.partida.jugadores) {
            j.sincronizarPartida();
            j.sincronizarSala();
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

    public JsonObject getJsonMotivoFin() {
        return jsonMotivoFin;
    }

    public JugadorServer getGanadorRonda() {
        return ganadorRonda;
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
