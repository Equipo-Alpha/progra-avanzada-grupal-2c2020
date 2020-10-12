package equipoalpha.loveletter.partida.estadosjugador;

import equipoalpha.loveletter.carta.Carta;
import equipoalpha.loveletter.carta.CartaTipo;
import equipoalpha.loveletter.partida.Jugador;

public class EstadoEligiendoCarta implements Estado{
	
	private CartaTipo cartaElegida;
	private final Jugador jugadorElegido;
	private final Carta cartaDescartada;
	
	public EstadoEligiendoCarta(Jugador jugadorElegido, Carta cartaDescartada) {
		this.jugadorElegido = jugadorElegido;
		this.cartaDescartada = cartaDescartada;
		this.setCartaElegida(null);
	}
	
	@Override
	public void ejecutar(EstadoJugador estado) {
		cartaDescartada.onCartaElegida(estado.getJugador(), jugadorElegido, cartaElegida);
	}

	public void setCartaElegida(CartaTipo cartaElegida) {
		this.cartaElegida = cartaElegida;
	}
	
	
	
}
