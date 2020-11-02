package equipoalpha.lovelettertest;

import equipoalpha.loveletter.jugador.Jugador;
import equipoalpha.loveletter.partida.Sala;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SalaTest {

    private Jugador jugador1;
    private Jugador jugador2;
    private Jugador jugador3;
    private Sala sala;

    @Before
    public void setUp(){
        jugador1 = new Jugador("TesterDeJava");
        jugador2 = new Jugador("TesterDeJS");
        jugador3 = new Jugador("TesterDeC");
        sala = jugador1.crearSala("test");
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
        Jugador jugador4 = new Jugador("TesterDeAlfombras");

        sala.setJugadorMano(jugador4);
        Assert.assertNull(sala.getJugadorMano());

        sala.setJugadorMano(jugador1);
        Assert.assertEquals(jugador1, sala.getJugadorMano());
    }

    @Test
    public void isConfigurada() {
    }

    @Test
    public void agregarJugador() {
        Assert.assertTrue(sala.agregarJugador(jugador2));
        Assert.assertFalse(sala.agregarJugador(jugador2));
    }

    @Test
    public void eliminarJugador() {
    }
}