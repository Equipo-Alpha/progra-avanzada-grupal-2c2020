package equipoalpha.loveletter.carta;

import equipoalpha.loveletter.jugador.EstadosJugador;
import equipoalpha.loveletter.partida.Ronda;
import equipoalpha.loveletter.partida.eventos.EventosPartida;
import equipoalpha.loveletter.server.JugadorServer;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Carta implements Comparable<Carta> {
    private final CartaTipo tipo;

    public Carta(CartaTipo tipo) {
        this.tipo = tipo;
    }

    /**
     * @param jugador jugador que la descarto
     */
    public void descartar(JugadorServer jugador) {
        Ronda ronda = jugador.partidaJugando.rondaActual;
        switch (tipo) {
            case MUCAMA:
                jugador.estaProtegido = true;
                break;
            case CONDESA:
                break;
            case PRINCESA:
                agregarAlMapa(ronda, jugador, this);
                ronda.eliminarJugadorEnTurno(jugador);
                return;
            default:
                if (ronda.puedeElegir(jugador, this.tipo)) {
                    jugador.getEstado().setEstadoActual(EstadosJugador.ELIGIENDOJUGADOR);
                    agregarAlMapa(ronda, jugador, this);
                    ronda.actualizarJugadores();
                    return;
                }
        }

        agregarAlMapa(ronda, jugador, this);
        ronda.onFinalizarDescarte(jugador);
    }

    public void jugadorElegido(JugadorServer jugadorQueDescarto, JugadorServer jugadorElegido) {
        Ronda ronda = jugadorQueDescarto.partidaJugando.rondaActual;
        switch (tipo) {
            case SACERDOTE:
                jugadorQueDescarto.verCarta(jugadorElegido);
                return;
            case BARON:
                ArrayList<JugadorServer> jugadores = new ArrayList<>();
                jugadores.add(jugadorQueDescarto);
                jugadores.add(jugadorElegido);
                jugadorQueDescarto.salaActual.eventos.ejecutar(EventosPartida.VIENDOCARTA, jugadores);
                return;
            case PRINCIPE:
                if (jugadorElegido.carta1.getTipo() == CartaTipo.PRINCESA) {
                    ronda.eliminarJugador(jugadorElegido);
                } else {
                    agregarAlMapa(ronda, jugadorElegido, jugadorElegido.carta1);
                    Carta cartaADar = ronda.darCarta();
                    if (cartaADar == null) cartaADar = ronda.darCartaEliminada();
                    jugadorElegido.carta1 = cartaADar;
                }
                break;
            case REY:
                Carta carta = jugadorQueDescarto.carta1;
                jugadorQueDescarto.carta1 = jugadorElegido.carta1;
                jugadorElegido.carta1 = carta;
                break;
            default:
                jugadorQueDescarto.getEstado().setEstadoActual(EstadosJugador.ADIVINANDOCARTA);
                return;
        }
        ronda.onFinalizarDescarte(jugadorQueDescarto);
    }

    public void cartaAdivinada(JugadorServer jugadorQueDescarto, JugadorServer jugadorElegido, CartaTipo cartaAdivinada) {
        if (tipo == CartaTipo.GUARDIA)
            if (jugadorElegido.tieneCarta(cartaAdivinada)) {
                jugadorQueDescarto.salaActual.chat.nuevoMensaje(jugadorQueDescarto + " adivina correctamente");
                jugadorQueDescarto.rondaJugando.eliminarJugador(jugadorElegido);
            }

        jugadorQueDescarto.rondaJugando.onFinalizarDescarte(jugadorQueDescarto);
    }

    private void agregarAlMapa(Ronda ronda, JugadorServer jugador, Carta carta) {
        ronda.mapaCartasDescartadas.get(jugador).add(carta);
    }

    public CartaTipo getTipo() {
        return tipo;
    }

    public BufferedImage getImagen() {
        return tipo.imagen;
    }

    public String getDescripcion() {return tipo.descripcion;}

    @Override
    public int hashCode() {
        return tipo.fuerza;
    }

    @Override
    public boolean equals(Object otraCarta) {
        if (this == otraCarta)
            return true;
        if (otraCarta == null)
            return false;
        if (getClass() != otraCarta.getClass())
            return false;
        Carta other = (Carta) otraCarta;
        return tipo == other.tipo;
    }

    @Override
    public int compareTo(Carta otraCarta) {
        return tipo.fuerza - otraCarta.tipo.fuerza;
    }

    @Override
    public String toString() {
        return tipo.nombre;
    }

}
