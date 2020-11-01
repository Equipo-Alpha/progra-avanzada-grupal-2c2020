package equipoalpha.lovelettertest;

import equipoalpha.loveletter.carta.Carta;
import equipoalpha.loveletter.carta.CartaTipo;
import equipoalpha.loveletter.jugador.EstadosJugador;
import equipoalpha.loveletter.jugador.Jugador;
import equipoalpha.loveletter.partida.Partida;
import equipoalpha.loveletter.partida.Sala;
import equipoalpha.loveletter.util.excepcion.JugadorException;
import equipoalpha.loveletter.util.excepcion.JugadorNoValido;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CartaTest {

    private Jugador jugador1;
    private Jugador jugador2;
    private Jugador jugador3;
    private Partida partida;

    @Before
    public void setUp() throws Exception {
        jugador1 = new Jugador("TesterDeJava");
        jugador2 = new Jugador("TesterDeJS");
        jugador3 = new Jugador("TesterDeC");

        Sala sala = jugador1.crearSala("test");
        sala.agregarJugador(jugador2);
        sala.agregarJugador(jugador3);

        sala.setCantSimbolosAfecto(5);
        sala.setJugadorMano(jugador1);
        sala.empezarPartida();
        this.partida = sala.partida;
    }

    @Test
    public void testGuardiaAdivinaBien() throws JugadorNoValido {
        jugador1.onComienzoTurno(new Carta(CartaTipo.GUARDIA));
        jugador1.descartarCarta2();
        jugador1.elegirJugador(jugador2);

        // le damos al jugador2 una carta
        jugador2.carta2 = new Carta(CartaTipo.SACERDOTE);

        jugador1.elegirCarta(CartaTipo.SACERDOTE);

        Assert.assertFalse(partida.rondaActual.jugadoresEnLaRonda.contains(jugador2));
        Assert.assertNull(jugador2.rondaJugando);
    }

    @Test
    public void testGuardiaAdivinaMal() throws JugadorNoValido {
        jugador1.onComienzoTurno(new Carta(CartaTipo.GUARDIA));
        jugador1.descartarCarta2();
        jugador1.elegirJugador(jugador2);

        // le damos al jugador2 una carta
        jugador2.carta2 = new Carta(CartaTipo.SACERDOTE);

        jugador1.elegirCarta(CartaTipo.MUCAMA);

        Assert.assertTrue(partida.rondaActual.jugadoresEnLaRonda.contains(jugador2));
        Assert.assertNotNull(jugador2.rondaJugando);
    }

    @Test
    public void testSacerdote() throws JugadorNoValido {
        jugador1.onComienzoTurno(new Carta(CartaTipo.SACERDOTE));
        jugador1.descartarCarta2();
        jugador1.elegirJugador(jugador2);
    }

    @Test
    public void testBaron() throws JugadorNoValido {
        jugador1.carta1 = new Carta(CartaTipo.REY);
        jugador2.carta1 = new Carta(CartaTipo.GUARDIA);
        jugador1.onComienzoTurno(new Carta(CartaTipo.BARON));
        jugador1.descartarCarta2();
        jugador1.elegirJugador(jugador2);

        Assert.assertFalse(partida.rondaActual.jugadoresEnLaRonda.contains(jugador2));
        Assert.assertNull(jugador2.rondaJugando);
    }

    @Test
    public void testMucama() {
        jugador1.onComienzoTurno(new Carta(CartaTipo.MUCAMA));
        jugador1.descartarCarta2();

        Assert.assertTrue(jugador1.estaProtegido);
    }

    @Test
    public void testPrincipe() throws JugadorNoValido {
        jugador1.carta1 = new Carta(CartaTipo.GUARDIA); // para que no quede descartando condesa
        jugador1.onComienzoTurno(new Carta(CartaTipo.PRINCIPE));
        jugador1.descartarCarta2();
        jugador2.carta1 = new Carta(CartaTipo.GUARDIA); // para que no quede descartando condesa
        jugador1.elegirJugador(jugador2);

        Assert.assertEquals(EstadosJugador.DESCARTANDO, jugador2.getEstado().getEstadoActual());
        Assert.assertEquals(EstadosJugador.ESPERANDO, jugador1.getEstado().getEstadoActual());
        Assert.assertEquals(EstadosJugador.ESPERANDO, jugador3.getEstado().getEstadoActual());
    }

    @Test
    public void testPrincipeCartaEliminada() throws JugadorNoValido {
        jugador1.carta1 = new Carta(CartaTipo.GUARDIA); // para que no quede descartando condesa
        partida.rondaActual.vaciarMazo();
        jugador1.onComienzoTurno(new Carta(CartaTipo.PRINCIPE));
        jugador1.descartarCarta2();
        jugador2.carta1 = new Carta(CartaTipo.GUARDIA); // para que no quede descartando condesa
        jugador1.elegirJugador(jugador2);
        Assert.assertEquals(EstadosJugador.DESCARTANDO, jugador2.getEstado().getEstadoActual());
        Assert.assertEquals(jugador2.carta2, partida.rondaActual.darCartaEliminada());
        Assert.assertEquals(EstadosJugador.ESPERANDO, jugador1.getEstado().getEstadoActual());
        Assert.assertEquals(EstadosJugador.ESPERANDO, jugador3.getEstado().getEstadoActual());
    }

    @Test
    public void testRey() throws JugadorException {
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

        Assert.assertFalse(partida.rondaActual.jugadoresEnLaRonda.contains(jugador1));
        Assert.assertNull(jugador1.rondaJugando);
    }

    @Test
    public void testElegirJugadorNingunoDisponible() {
        jugador2.estaProtegido = true;
        jugador3.estaProtegido = true;
        jugador1.onComienzoTurno(new Carta(CartaTipo.GUARDIA));
        jugador1.descartarCarta2();

        Assert.assertEquals(EstadosJugador.ESPERANDO, jugador1.getEstado().getEstadoActual());
    }

    @Test(expected = JugadorNoValido.class)
    public void testElegirseASiMismo() throws JugadorException {
        jugador1.onComienzoTurno(new Carta(CartaTipo.GUARDIA));
        jugador1.descartarCarta2();
        jugador1.elegirJugador(jugador1);
    }

    @Test
    public void testElegirseASiMismoPrincipe() throws JugadorException {
        jugador1.onComienzoTurno(new Carta(CartaTipo.PRINCIPE));
        jugador1.descartarCarta2();
        jugador1.elegirJugador(jugador1);
    }

    @Test(expected = JugadorNoValido.class)
    public void testElegirJugadorProtegido() throws JugadorException {
        jugador1.onComienzoTurno(new Carta(CartaTipo.GUARDIA));
        jugador1.descartarCarta2();
        jugador2.estaProtegido = true;
        jugador1.elegirJugador(jugador2);
    }
}
