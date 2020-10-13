package equipoalpha.lovelettertest;

import equipoalpha.loveletter.partida.EstadosJugador;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import equipoalpha.loveletter.carta.Carta;
import equipoalpha.loveletter.carta.CartaTipo;
import equipoalpha.loveletter.partida.Jugador;
import equipoalpha.loveletter.partida.Partida;
import equipoalpha.loveletter.partida.Ronda;

public class CartaTest {
	
	private Jugador jugador1;
	private Jugador jugador2;
	private Ronda rondaTest;

	@Before
	public void setUp() throws Exception {
		jugador1 = new Jugador("TesterDeJava");
		jugador2 = new Jugador("TesterDeJS");
		
		Partida partida = jugador1.crearPartida();
		partida.agregarJugador(jugador2);
		
		partida.setCantSimbolosAfecto(20);
		partida.setJugadorMano(jugador1);
		partida.initPartida();
		
		this.rondaTest = new Ronda(partida);
		rondaTest.initRonda();
		
	}

	@Test
	public void testGuardiaAdivinaBien() {
		jugador1.onComienzoTurno(new Carta(CartaTipo.GUARDIA));
		jugador1.descartarCarta2();
		jugador1.elegirJugador(jugador2);
		
		//le damos al jugador2 una carta
		jugador2.carta2 = new Carta(CartaTipo.SACERDOTE);
		
		jugador1.elegirCarta(CartaTipo.SACERDOTE); // A esto le pondria "Adivinar carta y le pasaria el jugador 2 y la carta, de manera que retorne true o false para poder descalificarlo

		Assert.assertFalse(rondaTest.jugadoresEnLaRonda.contains(jugador2));
		Assert.assertNull(jugador2.rondaJugando);
	}
	
	@Test
	public void testGuardiaAdivinaMal() {
		jugador1.onComienzoTurno(new Carta(CartaTipo.GUARDIA));
		jugador1.descartarCarta2();
		jugador1.elegirJugador(jugador2);
		
		//le damos al jugador2 una carta
		jugador2.carta2 = new Carta(CartaTipo.SACERDOTE);
		
		jugador1.elegirCarta(CartaTipo.MUCAMA); // A esto le pondr�a "Adivinar carta y le pasar�a el jugador 2 y la carta, de manera que retorne true o false para poder descalificarlo

		Assert.assertTrue(rondaTest.jugadoresEnLaRonda.contains(jugador2));
		Assert.assertNotNull(jugador2.rondaJugando);
	}
	
	@Test
	public void testSacerdote() {
		jugador1.onComienzoTurno(new Carta(CartaTipo.SACERDOTE));
		jugador1.descartarCarta2();
		jugador1.elegirJugador(jugador2);
	}
	
	@Test
	public void testBaron() {
		jugador1.carta1 = new Carta(CartaTipo.REY);
		jugador2.carta1 = new Carta(CartaTipo.GUARDIA);
		jugador1.onComienzoTurno(new Carta(CartaTipo.BARON));
		jugador1.descartarCarta2();
		jugador1.elegirJugador(jugador2);

		Assert.assertFalse(rondaTest.jugadoresEnLaRonda.contains(jugador2));
		Assert.assertNull(jugador2.rondaJugando);
	}
	
	@Test
	public void testMucama() {
		jugador1.onComienzoTurno(new Carta(CartaTipo.MUCAMA));
		jugador1.descartarCarta2();

		Assert.assertTrue(jugador1.estaProtegido);
	}
	
	@Test
	public void testPrincipe() {
		jugador1.onComienzoTurno(new Carta(CartaTipo.PRINCIPE));
		jugador1.descartarCarta2();
		
		jugador1.elegirJugador(jugador2);
		
		Assert.assertEquals(EstadosJugador.DESCARTANDO, jugador2.getEstado().getEstadoActual());
	}
	
	@Test
	public void testRey() {
		jugador1.carta1 = new Carta(CartaTipo.GUARDIA);
		jugador2.carta1 = new Carta(CartaTipo.BARON);
		
		jugador1.onComienzoTurno(new Carta(CartaTipo.REY));
		jugador1.descartarCarta2();
		
		jugador1.elegirJugador(jugador2);
		
		Assert.assertEquals(CartaTipo.BARON, jugador1.carta1.getTipo());
		Assert.assertNotEquals(CartaTipo.BARON, jugador2.carta1.getTipo());
	}
	
	@Test
	public void testCondesaConRey() {
		jugador1.carta1 = new Carta(CartaTipo.REY);
		
		jugador1.onComienzoTurno(new Carta(CartaTipo.CONDESA));
		
		Assert.assertEquals(EstadosJugador.DESCARTANDOCONDESA, jugador1.getEstado().getEstadoActual());

		Assert.assertFalse(jugador1.descartarCarta1());
	}
	
	@Test
	public void testCondesaConPrincipe() {
		jugador1.carta1 = new Carta(CartaTipo.PRINCIPE);
		
		jugador1.onComienzoTurno(new Carta(CartaTipo.CONDESA));
		
		Assert.assertEquals(EstadosJugador.DESCARTANDOCONDESA, jugador1.getEstado().getEstadoActual());

		Assert.assertFalse(jugador1.descartarCarta1());
	}
	
	@Test
	public void testPrincesa() {
		jugador1.onComienzoTurno(new Carta(CartaTipo.PRINCESA));
		jugador1.descartarCarta2();

		Assert.assertFalse(rondaTest.jugadoresEnLaRonda.contains(jugador1));
		Assert.assertNull(jugador1.rondaJugando);
	}

}
