package equipoalpha.loveletter.carta;

import equipoalpha.loveletter.partida.Jugador;
import equipoalpha.loveletter.partida.estadosjugador.EstadoEligiendoJugador;
import equipoalpha.loveletter.partida.estadosjugador.EstadoEligiendoCarta;

@SuppressWarnings("incomplete-switch")
public class Carta implements Comparable<Carta> {
	private final CartaTipo tipo;

	public Carta(CartaTipo tipo) {
		this.tipo = tipo;
	}

	/**
	 * @param jugador jugador que la descarto
	 */
	public void descartar(Jugador jugador) {

		switch (tipo) {
			case MUCAMA:
				jugador.estaProtegido = true;
				break;
			case CONDESA:
				break;
			case PRINCESA:
				jugador.rondaJugando.eliminarJugador(jugador);
				break;
			default: jugador.getEstado().setEstado(new EstadoEligiendoJugador(this)); return;
		}

		if(jugador.rondaJugando != null){
			jugador.rondaJugando.onFinalizarDescarte(jugador);
		}
	}

	public void onElegido(Jugador jugadorQueDescarto, Jugador jugadorElegido){

		switch (tipo){
			case GUARDIA:
				jugadorQueDescarto.getEstado().setEstado(new EstadoEligiendoCarta(jugadorElegido, this));
				return;
			case SACERDOTE:
				jugadorQueDescarto.verCarta(jugadorElegido);
				break;
			case BARON:
				jugadorElegido.verCarta(jugadorQueDescarto);
				jugadorQueDescarto.verCarta(jugadorElegido);
				jugadorQueDescarto.rondaJugando.determinarCartaMayor(jugadorQueDescarto, jugadorElegido);
				break;
			case PRINCIPE:
				jugadorElegido.robarCarta(jugadorElegido.rondaJugando.darCarta());
				break;
			case REY:
				Carta carta = jugadorQueDescarto.carta1;
				jugadorQueDescarto.carta1 = jugadorElegido.carta1;
				jugadorElegido.carta1 = carta;
				break;
		}
		if(jugadorQueDescarto.rondaJugando != null){
			jugadorQueDescarto.rondaJugando.onFinalizarDescarte(jugadorQueDescarto);
		}

	}
	
	public void onCartaElegida(Jugador jugadorQueDescarto, Jugador jugadorElegido, CartaTipo cartaElegida) {
		if (tipo == CartaTipo.GUARDIA) {
			if (jugadorElegido.tieneCarta(cartaElegida)) {
				jugadorQueDescarto.rondaJugando.eliminarJugador(jugadorElegido);
				jugadorElegido.rondaJugando = null;
			}
		}
		
		jugadorQueDescarto.rondaJugando.onFinalizarDescarte(jugadorQueDescarto);
	}

	public CartaTipo getTipo() {
		return tipo;
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
