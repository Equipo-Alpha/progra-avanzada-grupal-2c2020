package equipoalpha.lovelettertest;

import equipoalpha.loveletter.carta.Carta;
import equipoalpha.loveletter.carta.CartaTipo;
import equipoalpha.loveletter.partida.EstadosJugador;
import equipoalpha.loveletter.partida.Jugador;
import equipoalpha.loveletter.partida.Partida;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RondaTest {
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
        partida.agregarJugador(jugador2);
        partida.agregarJugador(jugador3);
        partida.setCantSimbolosAfecto(5);
        partida.setJugadorMano(jugador3);
        partida.initPartida();
    }

    @Test
    public void initRonda(){ //al iniciar partida la ronda tambien inicia
        Assert.assertNotNull(partida.rondaActual);
    }

    @Test
    public void testTurnos(){
        jugador1.carta1 = new Carta(CartaTipo.GUARDIA);
        jugador2.carta1 = new Carta(CartaTipo.GUARDIA);
        Assert.assertEquals(EstadosJugador.ESPERANDO, jugador1.getEstado().getEstadoActual());
        Assert.assertEquals(EstadosJugador.DESCARTANDO, jugador3.getEstado().getEstadoActual());
        jugador3.carta2 = new Carta(CartaTipo.CONDESA);
        jugador3.descartarCarta2();
        Assert.assertEquals(EstadosJugador.ESPERANDO, jugador3.getEstado().getEstadoActual());
        Assert.assertEquals(EstadosJugador.DESCARTANDO, jugador1.getEstado().getEstadoActual());
        jugador1.carta2 = new Carta(CartaTipo.CONDESA);
        jugador1.descartarCarta2();
        Assert.assertEquals(EstadosJugador.ESPERANDO, jugador1.getEstado().getEstadoActual());
        Assert.assertEquals(EstadosJugador.DESCARTANDO, jugador2.getEstado().getEstadoActual());
    }

    @Test
    public void onFinalizarDescarte() {
        jugador2.onComienzoTurno(new Carta(CartaTipo.CONDESA));
        jugador2.descartarCarta2();
        jugador2.onComienzoTurno(new Carta(CartaTipo.CONDESA));
        jugador2.descartarCarta2();
        jugador2.onComienzoTurno(new Carta(CartaTipo.CONDESA));
        jugador2.descartarCarta2();

        //el jugador2 deberia tener 3 cartas en el mapa de cartas descartadas
        Assert.assertEquals(3, (int) partida.rondaActual.mapaCartasEliminadas.get(jugador2));
    }

    @Test
    public void rondaTerminadaUnJugador() {
        jugador3.getEstado().setEstadoActual(EstadosJugador.ESPERANDO); // porque es mano
        jugador1.onComienzoTurno(new Carta(CartaTipo.PRINCESA));
        jugador1.descartarCarta2();
        jugador2.onComienzoTurno(new Carta(CartaTipo.PRINCESA));
        jugador2.descartarCarta2();
        //gana el jugador 3
        Assert.assertEquals(1, jugador3.cantSimbolosAfecto); // recibe 1 simbolo
        Assert.assertEquals(jugador3, partida.getJugadorMano()); // y es la mano de la siguiente ronda
    }

    @Test
    public void rondaTerminadaMazoVacioValorMasAlto(){
        partida.rondaActual.vaciarMazo();
        jugador3.getEstado().setEstadoActual(EstadosJugador.ESPERANDO); // porque es mano
        jugador1.carta1 = new Carta(CartaTipo.BARON);
        jugador3.carta1 = new Carta(CartaTipo.GUARDIA);
        jugador2.carta1 = new Carta(CartaTipo.PRINCESA);
        jugador2.onComienzoTurno(new Carta(CartaTipo.CONDESA)); //la condesa no tiene ningun efecto especifico
        jugador2.descartarCarta2();
        //gana el jugador2 tiene la carta de valor mas alto
        Assert.assertEquals(1, jugador2.cantSimbolosAfecto); // recibe 1 simbolo
        Assert.assertEquals(jugador2, partida.getJugadorMano()); // y es la mano de la siguiente ronda
    }

    @Test
    public void rondaTerminadaMazoVacioCartasDescartadas(){
        partida.rondaActual.vaciarMazo();
        jugador3.getEstado().setEstadoActual(EstadosJugador.ESPERANDO); // porque es mano
        jugador1.carta1 = new Carta(CartaTipo.BARON);
        jugador1.carta2 = null;
        jugador3.carta1 = new Carta(CartaTipo.BARON);
        jugador3.carta2 = null;
        jugador2.carta1 = new Carta(CartaTipo.BARON);
        jugador2.onComienzoTurno(new Carta(CartaTipo.CONDESA)); //la condesa no tiene ningun efecto especifico
        jugador2.descartarCarta2();
        //gana el jugador2 descarto mas cartas
        Assert.assertEquals(1, jugador2.cantSimbolosAfecto); // recibe 1 simbolo
        Assert.assertEquals(jugador2, partida.getJugadorMano()); // y es la mano de la siguiente ronda
    }
}
