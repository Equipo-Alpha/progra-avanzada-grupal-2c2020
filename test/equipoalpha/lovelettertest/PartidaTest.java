package equipoalpha.lovelettertest;

import equipoalpha.loveletter.carta.Carta;
import equipoalpha.loveletter.carta.CartaTipo;
import equipoalpha.loveletter.jugador.Jugador;
import equipoalpha.loveletter.partida.Partida;
import equipoalpha.loveletter.partida.Sala;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PartidaTest {

    private Jugador jugador1;
    private Jugador jugador2;
    private Partida partida;

    @Before
    public void setUp(){
        jugador1 = new Jugador("TesterDeJava");
        jugador2 = new Jugador("TesterDeJS");
        Sala sala = jugador1.crearSala("test");
        sala.agregarJugador(jugador2);

        sala.setCantSimbolosAfecto(5);
        sala.setJugadorMano(jugador1);
        sala.crearPartida();
        this.partida = sala.partida;
        partida.initPartida();
    }

    @Test
    public void partidaTerminada() {
        Assert.assertFalse(partida.partidaTerminada());
    }

    @Test
    public void onFinalizarPartida() {
        jugador2.cantSimbolosAfecto = 4;
        jugador1.onComienzoTurno(new Carta(CartaTipo.PRINCESA));
        jugador1.descartarCarta2();

        Assert.assertFalse(partida.partidaEnCurso); // la partida termino
        Assert.assertEquals(jugador2, partida.getJugadorMano()); // gano el jugador2

    }
}
