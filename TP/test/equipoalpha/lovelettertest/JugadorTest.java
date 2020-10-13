package equipoalpha.lovelettertest;

import static org.junit.Assert.*;

import org.junit.Test;

import equipoalpha.loveletter.carta.Carta;
import equipoalpha.loveletter.carta.CartaTipo;
import equipoalpha.loveletter.partida.Jugador;

public class JugadorTest {

	@Test
	public void test() {
		Jugador j1 = new Jugador("test1234");
		j1.robarCarta(new Carta(CartaTipo.PRINCESA));
	}

}
