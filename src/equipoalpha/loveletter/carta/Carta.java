package equipoalpha.loveletter.carta;

import equipoalpha.loveletter.jugador.EstadosJugador;
import equipoalpha.loveletter.jugador.Jugador;
import equipoalpha.loveletter.jugador.JugadorIA;
import equipoalpha.loveletter.partida.Ronda;

import java.awt.image.BufferedImage;

public class Carta implements Comparable<Carta> {
    private final CartaTipo tipo;

    public Carta(CartaTipo tipo) {
        this.tipo = tipo;
    }

    /**
     * @param jugador jugador que la descarto
     */
    public void descartar(Jugador jugador) {
        Ronda ronda = jugador.partidaJugando.rondaActual;

        switch (tipo) {
            case MUCAMA:
                jugador.estaProtegido = true;
                break;
            case CONDESA:
                break;
            case PRINCESA:
                ronda.eliminarJugador(jugador);
                break;
            default:
                if (ronda.puedeElegir(jugador, this.tipo)) {
                    jugador.getEstado().setEstadoActual(EstadosJugador.ELIGIENDOJUGADOR);
                    if (jugador instanceof JugadorIA) {
                        try {
                            ((JugadorIA) jugador).onElegirJugador();
                        } catch (Exception ignored) {
                        }
                    }
                    return;
                }
        }

        ronda.onFinalizarDescarte(jugador);
    }

    public void jugadorElegido(Jugador jugadorQueDescarto, Jugador jugadorElegido) {
        Ronda ronda = jugadorQueDescarto.partidaJugando.rondaActual;

        switch (tipo) {
            case SACERDOTE:
                jugadorQueDescarto.verCarta(jugadorElegido);
                break;
            case BARON:
                jugadorElegido.verCarta(jugadorQueDescarto);
                jugadorQueDescarto.verCarta(jugadorElegido);
                ronda.determinarCartaMayor(jugadorQueDescarto, jugadorElegido);
                break;
            case PRINCIPE:
                Carta cartaADar = ronda.darCarta();
                if (cartaADar == null) cartaADar = ronda.darCartaEliminada();
                jugadorElegido.robarCarta(cartaADar);
                break;
            case REY:
                Carta carta = jugadorQueDescarto.carta1;
                jugadorQueDescarto.carta1 = jugadorElegido.carta1;
                jugadorElegido.carta1 = carta;
                break;
            default:
                jugadorQueDescarto.getEstado().setEstadoActual(EstadosJugador.ADIVINANDOCARTA);
                if (jugadorQueDescarto instanceof JugadorIA) {
                    ((JugadorIA) jugadorQueDescarto).onAdivinarCarta();
                }
                return;
        }
        ronda.onFinalizarDescarte(jugadorQueDescarto);

    }

    public void cartaAdivinada(Jugador jugadorQueDescarto, Jugador jugadorElegido, CartaTipo cartaAdivinada) {
        if (tipo == CartaTipo.GUARDIA)
            if (jugadorElegido.tieneCarta(cartaAdivinada))
                jugadorQueDescarto.rondaJugando.eliminarJugador(jugadorElegido);

        jugadorQueDescarto.rondaJugando.onFinalizarDescarte(jugadorQueDescarto);
    }

    public CartaTipo getTipo() {
        return tipo;
    }

    public BufferedImage getImagen() {
        return tipo.imagen;
    }

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
        return tipo.toString();
    }

}
