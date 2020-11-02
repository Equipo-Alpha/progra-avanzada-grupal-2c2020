package equipoalpha.lovelettertest;

import equipoalpha.loveletter.jugador.EstadosJugador;
import equipoalpha.loveletter.jugador.Jugador;
import equipoalpha.loveletter.partida.Sala;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JugadorTest {
    private Jugador jugador;
    private Jugador jugador2;
    private Sala sala;

    @Before
    public void setUp() {
        this.jugador = new Jugador("TesterDeJava");
        this.jugador2 = new Jugador("dummy");

        this.sala = jugador.crearSala("test");
    }

    @Test
    public void crearPartida() {
    }

    @Test
    public void unirseASala() {
        Assert.assertFalse(jugador.unirseASala(sala));
    }

    @Test
    public void iniciarPartida() {
        jugador2.iniciarPartida();
        Assert.assertNull(jugador2.salaActual); // no esta en ninguna sala

        jugador.iniciarPartida();
        Assert.assertNull(sala.partida); // solo 1 jugador

        jugador2.unirseASala(sala);
        jugador.iniciarPartida();
        Assert.assertNull(sala.partida); // faltan setear condiciones

        sala.setJugadorMano(jugador);
        sala.setCantSimbolosAfecto(5);

        jugador.iniciarPartida();
        //la sala puede iniciar ambos se encuentran confirmando que estan listos
        Assert.assertEquals(EstadosJugador.CONFIRMANDOINICIO, jugador.getEstado().getEstadoActual());
        Assert.assertEquals(EstadosJugador.CONFIRMANDOINICIO, jugador2.getEstado().getEstadoActual());
    }

    @Test
    public void confirmarInicio() {
        jugador2.unirseASala(sala);
        sala.setJugadorMano(jugador);
        sala.setCantSimbolosAfecto(5);

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
    public void cancelarInicio() {
        jugador2.unirseASala(sala);
        sala.setJugadorMano(jugador);
        sala.setCantSimbolosAfecto(5);

        jugador.iniciarPartida();
        //Los jugadores se encuentran confirmando el inicio
        Assert.assertEquals(EstadosJugador.CONFIRMANDOINICIO, jugador2.getEstado().getEstadoActual());

        //Un jugador no esta listo para iniciar la partida
        jugador.cancelarInicio();

        //La partida no inicia
        Assert.assertEquals(EstadosJugador.ESPERANDO, jugador2.getEstado().getEstadoActual());
        Assert.assertNull(sala.partida);
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