package equipoalpha.lovelettertest;

import equipoalpha.loveletter.partida.EstadosJugador;
import equipoalpha.loveletter.partida.Jugador;
import equipoalpha.loveletter.partida.Partida;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class JugadorTest {
    private Jugador jugador;
    private Jugador jugador2;
    private Partida partida;

    @Before
    public void setUp(){
        this.jugador = new Jugador("TesterDeJava");
        this.jugador2 = new Jugador("dummy");

        this.partida = jugador.crearPartida();
    }

    @Test
    public void crearPartida() {
    }

    @Test
    public void unirseAPartida() {
        Assert.assertFalse(jugador.unirseAPartida(partida));
    }

    @Test
    public void iniciarPartida() {
        jugador2.iniciarPartida();
        Assert.assertNull(jugador2.partidaJugando); // no esta en ninguna partida

        jugador.iniciarPartida();
        Assert.assertFalse(partida.partidaEnCurso); // solo 1 jugador

        jugador2.unirseAPartida(partida);
        jugador.iniciarPartida();
        Assert.assertFalse(partida.partidaEnCurso); // faltan setear condiciones

        partida.setJugadorMano(jugador);
        partida.setCantSimbolosAfecto(20);

        jugador.iniciarPartida();
        //la partida puede iniciar ambos se encuentran confirmando que estan listos
        Assert.assertEquals(EstadosJugador.CONFIRMANDOINICIO, jugador.getEstado().getEstadoActual());
        Assert.assertEquals(EstadosJugador.CONFIRMANDOINICIO, jugador2.getEstado().getEstadoActual());
    }

    @Test
    public void confirmarInicio() {
        jugador2.unirseAPartida(partida);
        partida.setJugadorMano(jugador);
        partida.setCantSimbolosAfecto(20);

        jugador.iniciarPartida();

        jugador.confirmarInicio();
        Assert.assertEquals(EstadosJugador.ESPERANDO, jugador.getEstado().getEstadoActual());

        jugador2.confirmarInicio();
        Assert.assertEquals(EstadosJugador.ESPERANDO, jugador2.getEstado().getEstadoActual());
        //ambos jugadores confirmaron la partida inicio
        Assert.assertTrue(jugador.partidaJugando.partidaEnCurso);
        Assert.assertTrue(jugador2.partidaJugando.partidaEnCurso);
    }

    @Test
    public void onComienzoTurno() {
    }

    @Test
    public void descartarCarta1() {
    }

    @Test
    public void descartarCarta2() {
    }

    @Test
    public void robarCarta() {
    }

    @Test
    public void elegirJugador() {
    }

    @Test
    public void elegirCarta() {
    }

    @Test
    public void verCarta() {
    }

    @Test
    public void tieneCarta() {
    }
}