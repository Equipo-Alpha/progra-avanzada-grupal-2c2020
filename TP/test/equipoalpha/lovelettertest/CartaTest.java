package equipoalpha.lovelettertest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import equipoalpha.loveletter.carta.Carta;
import equipoalpha.loveletter.carta.CartaTipo;
import equipoalpha.loveletter.partida.Jugador;
import equipoalpha.loveletter.partida.Partida;
import equipoalpha.loveletter.partida.Ronda;
import equipoalpha.loveletter.partida.estadosjugador.EstadoDescartando;
import equipoalpha.loveletter.partida.estadosjugador.EstadoDescartandoCondesa;

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
	public void testGuardia() {
		jugador1.onComienzoTurno(new Carta(CartaTipo.GUARDIA));
		jugador1.descartarCarta2();
		jugador1.elegirJugador(jugador2);
		
		//le damos al jugador2 una carta
		jugador2.carta2 = new Carta(CartaTipo.SACERDOTE);
		
		jugador1.elegirCarta(CartaTipo.SACERDOTE);
		
		Assert.assertEquals(false, rondaTest.jugadoresEnLaRonda.contains(jugador2));
		Assert.assertEquals(null, jugador2.rondaJugando);
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
		
		Assert.assertEquals(false, rondaTest.jugadoresEnLaRonda.contains(jugador2));
		Assert.assertEquals(null, jugador2.rondaJugando);
	}
	
	@Test
	public void testMucama() {
		jugador1.onComienzoTurno(new Carta(CartaTipo.MUCAMA));
		jugador1.descartarCarta2();
		
		Assert.assertEquals(true, jugador1.estaProtegido);
	}
	
	@Test
	public void testPrincipe() {
		jugador1.onComienzoTurno(new Carta(CartaTipo.PRINCIPE));
		jugador1.descartarCarta2();
		
		jugador1.elegirJugador(jugador2);
		
		Assert.assertEquals(EstadoDescartando.class, jugador2.getEstado().getEstado().getClass());
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
	public void testCondesa() {
		jugador1.carta1 = new Carta(CartaTipo.REY);
		
		jugador1.onComienzoTurno(new Carta(CartaTipo.CONDESA));
		
		Assert.assertEquals(EstadoDescartandoCondesa.class, jugador1.getEstado().getEstado().getClass());
		
		Assert.assertEquals(false, jugador1.descartarCarta1());
	}
	
	@Test
	public void testPrincesa() {
		jugador1.onComienzoTurno(new Carta(CartaTipo.PRINCESA));
		jugador1.descartarCarta2();
		
		Assert.assertEquals(false, rondaTest.jugadoresEnLaRonda.contains(jugador1));
		Assert.assertEquals(null, jugador1.rondaJugando);
	}

}
