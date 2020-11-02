package equipoalpha.loveletter.carta;

import equipoalpha.loveletter.jugador.EstadosJugador;
import equipoalpha.loveletter.jugador.Jugador;
import equipoalpha.loveletter.jugador.JugadorIA;
import equipoalpha.loveletter.partida.Ronda;

import java.awt.image.BufferedImage;

import static java.lang.System.exit;

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
        System.out.println(jugador + " descarta " + this);
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
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            exit(0);
                        }
                    }
                    return;
                }
        }

        ronda.onFinalizarDescarte(jugador);
    }

    public void jugadorElegido(Jugador jugadorQueDescarto, Jugador jugadorElegido) {
        Ronda ronda = jugadorQueDescarto.partidaJugando.rondaActual;
        System.out.println(jugadorQueDescarto + " elige al jugador " + jugadorElegido);
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
                if(jugadorElegido.carta1.getTipo() == CartaTipo.PRINCESA) {
                    ronda.eliminarJugador(jugadorElegido);
                } else {
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
                if (jugadorQueDescarto instanceof JugadorIA) {
                    ((JugadorIA) jugadorQueDescarto).onAdivinarCarta();
                }
                return;
        }
        ronda.onFinalizarDescarte(jugadorQueDescarto);

    }

    public void cartaAdivinada(Jugador jugadorQueDescarto, Jugador jugadorElegido, CartaTipo cartaAdivinada) {
        System.out.println(jugadorQueDescarto + " intenta adivinar con " + cartaAdivinada.nombre);
        if (tipo == CartaTipo.GUARDIA)
            if (jugadorElegido.tieneCarta(cartaAdivinada)) {
                System.out.println(jugadorQueDescarto + " adivina correctamente");
                jugadorQueDescarto.rondaJugando.eliminarJugador(jugadorElegido);
            }

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
        return tipo.nombre;
    }

}
