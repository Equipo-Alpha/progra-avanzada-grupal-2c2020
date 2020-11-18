package equipoalpha.lovelettertest;

import equipoalpha.loveletter.carta.Carta;
import equipoalpha.loveletter.carta.CartaTipo;
import equipoalpha.loveletter.jugador.EstadosJugador;
import equipoalpha.loveletter.partida.Partida;
import equipoalpha.loveletter.partida.Sala;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RondaTest {
    private JugadorImplTest jugador1;
    private JugadorImplTest jugador2;
    private JugadorImplTest jugador3;
    private Partida partida;

    @Before
    public void setUp() {
        jugador1 = new JugadorImplTest("TesterDeJava");
        jugador2 = new JugadorImplTest("TesterDeJS");
        jugador3 = new JugadorImplTest("TesterDeC");
        Sala sala = jugador1.crearSalaImpl("test");
        sala.agregarJugador(jugador2);
        sala.agregarJugador(jugador3);

        sala.setCantSimbolosAfecto(5);
        sala.setJugadorMano(jugador3);
        sala.setCreadorNull(false);
        sala.crearPartida();
        this.partida = sala.partida;
        partida.initPartida();
    }

    @Test
    public void initRonda() { //al iniciar partida la ronda tambien inicia
        Assert.assertNotNull(partida.rondaActual);
    }

    @Test
    public void testTurnos() {
        partida.rondaActual.initTurnos();
        jugador1.carta1 = new Carta(CartaTipo.GUARDIA);
        jugador2.carta1 = new Carta(CartaTipo.GUARDIA);
        Assert.assertEquals(EstadosJugador.ESPERANDO, jugador1.getEstado().getEstadoActual());
        Assert.assertNotEquals(EstadosJugador.ESPERANDO, jugador3.getEstado().getEstadoActual());
        jugador3.carta2 = new Carta(CartaTipo.CONDESA);
        jugador3.descartarCarta2Impl();
        Assert.assertEquals(EstadosJugador.ESPERANDO, jugador3.getEstado().getEstadoActual());
        Assert.assertEquals(EstadosJugador.DESCARTANDO, jugador1.getEstado().getEstadoActual());
        jugador1.carta2 = new Carta(CartaTipo.CONDESA);
        jugador1.descartarCarta2Impl();
        Assert.assertEquals(EstadosJugador.ESPERANDO, jugador1.getEstado().getEstadoActual());
        Assert.assertEquals(EstadosJugador.DESCARTANDO, jugador2.getEstado().getEstadoActual());
    }

    @Test
    public void onFinalizarDescarte() {
        jugador2.onComienzoTurno(new Carta(CartaTipo.CONDESA));
        jugador2.descartarCarta2Impl();
        jugador2.onComienzoTurno(new Carta(CartaTipo.CONDESA));
        jugador2.descartarCarta2Impl();
        jugador2.onComienzoTurno(new Carta(CartaTipo.CONDESA));
        jugador2.descartarCarta2Impl();

        //el jugador2 deberia tener 3 cartas en el mapa de cartas descartadas
        Assert.assertEquals(3, (int) partida.rondaActual.mapaCartasDescartadas.get(jugador2).size());
    }

    @Test
    public void rondaTerminadaUnJugador() {
        jugador3.getEstado().setEstadoActual(EstadosJugador.ESPERANDO); // porque es mano
        jugador1.onComienzoTurno(new Carta(CartaTipo.PRINCESA));
        jugador1.descartarCarta2Impl();
        jugador2.onComienzoTurno(new Carta(CartaTipo.PRINCESA));
        jugador2.descartarCarta2Impl();
        //gana el jugador 3
        Assert.assertEquals(1, jugador3.cantSimbolosAfecto); // recibe 1 simbolo
        Assert.assertEquals(jugador3, partida.getJugadorMano()); // y es la mano de la siguiente ronda
    }

    @Test
    public void rondaTerminadaMazoVacioValorMasAlto() {
        partida.rondaActual.vaciarMazo();
        jugador3.getEstado().setEstadoActual(EstadosJugador.ESPERANDO); // porque es mano
        jugador1.carta1 = new Carta(CartaTipo.BARON);
        jugador3.carta1 = new Carta(CartaTipo.GUARDIA);
        jugador2.carta1 = new Carta(CartaTipo.PRINCESA);
        jugador2.onComienzoTurno(new Carta(CartaTipo.CONDESA)); //la condesa no tiene ningun efecto especifico
        jugador2.descartarCarta2Impl();
        //gana el jugador2 tiene la carta de valor mas alto
        Assert.assertEquals(1, jugador2.cantSimbolosAfecto); // recibe 1 simbolo
        Assert.assertEquals(jugador2, partida.getJugadorMano()); // y es la mano de la siguiente ronda
    }

    @Test
    public void rondaTerminadaMazoVacioCartasDescartadas() {
        partida.rondaActual.vaciarMazo();
        jugador3.getEstado().setEstadoActual(EstadosJugador.ESPERANDO); // porque es mano
        jugador1.carta1 = new Carta(CartaTipo.BARON);
        jugador1.carta2 = null;
        jugador3.carta1 = new Carta(CartaTipo.BARON);
        jugador3.carta2 = null;
        jugador2.carta1 = new Carta(CartaTipo.BARON);
        jugador2.onComienzoTurno(new Carta(CartaTipo.CONDESA)); //la condesa no tiene ningun efecto especifico
        jugador2.descartarCarta2Impl();
        //gana el jugador2 descarto mas cartas
        Assert.assertEquals(1, jugador2.cantSimbolosAfecto); // recibe 1 simbolo
        Assert.assertEquals(jugador2, partida.getJugadorMano()); // y es la mano de la siguiente ronda
    }

    @Test
    public void eliminarJugadorEnTurno() {
        partida.rondaActual.initTurnos();
        Assert.assertEquals(jugador3, partida.rondaActual.jugadorEnTurno);
        Assert.assertNotEquals(EstadosJugador.ESPERANDO, jugador3.getEstado().getEstadoActual());
        jugador3.salirSala();
        Assert.assertFalse(partida.rondaActual.jugadoresEnLaRonda.contains(jugador3));
        Assert.assertNotEquals(jugador3, partida.rondaActual.jugadorEnTurno);
        Assert.assertEquals(EstadosJugador.ESPERANDO, jugador3.getEstado().getEstadoActual());
    }
}
