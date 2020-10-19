package equipoalpha.lovelettertest;

import equipoalpha.loveletter.carta.Carta;
import equipoalpha.loveletter.carta.CartaTipo;
import equipoalpha.loveletter.partida.Jugador;
import equipoalpha.loveletter.partida.Partida;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PartidaTest {

    private Jugador jugador1;
    private Jugador jugador2;
    private Jugador jugador3;
    private Partida partida;

    @Before
    public void setUp() throws Exception {
        jugador1 = new Jugador("TesterDeJava");
        jugador2 = new Jugador("TesterDeJS");
        jugador3 = new Jugador("TesterDeC");
        partida = jugador1.crearPartida();
    }

    @Test
    public void initPartida() {
        partida.initPartida();
        Assert.assertFalse(partida.partidaEnCurso);

        partida.agregarJugador(jugador2);
        partida.agregarJugador(jugador3);
        partida.setCantSimbolosAfecto(5);
        partida.setJugadorMano(jugador1);

        partida.initPartida();
        Assert.assertTrue(partida.partidaEnCurso);
    }

    @Test
    public void agregarJugador() {
        Assert.assertTrue(partida.agregarJugador(jugador2));
        Assert.assertFalse(partida.agregarJugador(jugador2));
    }

    @Test
    public void partidaTerminada() {
        Assert.assertTrue(partida.partidaTerminada()); // bueno nunca empezo

        partida.setCantSimbolosAfecto(5);
        partida.agregarJugador(jugador2);
        Assert.assertFalse(partida.partidaTerminada());
    }

    @Test
    public void onFinalizarPartida(){
        partida.agregarJugador(jugador2);
        partida.setCantSimbolosAfecto(5);
        partida.setJugadorMano(jugador1);

        partida.initPartida();

        jugador2.cantSimbolosAfecto = 4;
        jugador1.onComienzoTurno(new Carta(CartaTipo.PRINCESA));
        jugador1.descartarCarta2();

        Assert.assertFalse(partida.partidaEnCurso); // la partida termino
        Assert.assertEquals(jugador2, partida.getJugadorMano()); // gano el jugador2

    }

    @Test
    public void setCantSimbolosAfecto() {
        partida.setCantSimbolosAfecto(1);
        Assert.assertEquals(0, partida.getCantSimbolosAfecto());

        partida.setCantSimbolosAfecto(5);
        Assert.assertEquals(5, partida.getCantSimbolosAfecto());
    }

    @Test
    public void setJugadorMano() {
        Jugador jugador4 = new Jugador("TesterDeAlfombras");

        partida.setJugadorMano(jugador4);
        Assert.assertNull(partida.getJugadorMano());

        partida.setJugadorMano(jugador1);
        Assert.assertNotNull(partida.getJugadorMano());
    }
}