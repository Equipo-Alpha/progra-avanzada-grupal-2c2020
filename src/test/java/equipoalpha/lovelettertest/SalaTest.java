package equipoalpha.lovelettertest;

import equipoalpha.loveletter.partida.Sala;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNull;

public class SalaTest {

    private JugadorImplTest jugador1;
    private JugadorImplTest jugador2;
    private JugadorImplTest jugador3;
    private Sala sala;

    @Before
    public void setUp() {
        jugador1 = new JugadorImplTest("TesterDeJava");
        jugador2 = new JugadorImplTest("TesterDeJS");
        jugador3 = new JugadorImplTest("TesterDeC");
        sala = jugador1.crearSalaImpl("test");
    }

    @Test
    public void empezarPartida() {
    }

    @Test
    public void setCantSimbolosAfecto() {
        sala.setCantSimbolosAfecto(1);
        assertNull(sala.getCantSimbolosAfecto());

        sala.setCantSimbolosAfecto(5);
        Assert.assertEquals(new Integer(5), sala.getCantSimbolosAfecto());
    }

    @Test
    public void setJugadorMano() {
        JugadorImplTest jugador4 = new JugadorImplTest("TesterDeAlfombras");

        sala.setJugadorMano(jugador4);
        Assert.assertNull(sala.getJugadorMano());

        sala.setJugadorMano(jugador1);
        Assert.assertEquals(jugador1, sala.getJugadorMano());
    }

    @Test
    public void isConfigurada() {
        Assert.assertFalse(sala.isConfigurada());
        sala.setJugadorMano(jugador1);
        Assert.assertFalse(sala.isConfigurada());
        sala.setCantSimbolosAfecto(5);
        Assert.assertTrue(sala.isConfigurada());
    }

    @Test
    public void agregarJugador() {
        Assert.assertTrue(sala.agregarJugador(jugador2));
        Assert.assertFalse(sala.agregarJugador(jugador2));
    }

    @Test
    public void eliminarJugador() {
        sala.agregarJugador(jugador2);
        sala.setCreadorNull(false);
        Assert.assertEquals(jugador1, sala.creador);
        jugador1.salirSala();
        Assert.assertEquals(jugador2, sala.creador);
        sala.agregarJugador(jugador1);
        sala.setCreadorNull(true);
        jugador2.salirSala();
        sala.tick();
        Assert.assertNull(jugador1.salaActual);
    }
}
