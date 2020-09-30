package equipoalpha.loveletter.carta;

import equipoalpha.loveletter.partida.Jugador;

public class Carta implements Comparable<Carta> {
	private final CartaTipo tipo;

	public Carta(CartaTipo tipo) {
		this.tipo = tipo;
	}

	/*
	 * @param jugador que la descarto
	 */
	public void descartar(Jugador jugador) {
		switch (tipo) {
		case GUARDIA:
			break;
		case BARON:
			break;
		case CONDESA:
			break;
		case MUCAMA:
			jugador.estaProtegido = true;
			break;
		case PRINCESA:
			break;
		case PRINCIPE:
			break;
		case REY:
			break;
		case SACERDOTE:
			break;
		}
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
		if (tipo != other.tipo)
			return false;
		return true;
	}

	@Override
	public int compareTo(Carta otraCarta) {
		return tipo.fuerza - otraCarta.tipo.fuerza;
	}

}
