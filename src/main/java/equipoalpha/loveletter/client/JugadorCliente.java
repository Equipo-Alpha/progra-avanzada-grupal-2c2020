package equipoalpha.loveletter.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import equipoalpha.loveletter.carta.Carta;
import equipoalpha.loveletter.carta.CartaTipo;
import equipoalpha.loveletter.common.ComandoTipo;
import equipoalpha.loveletter.jugador.Jugador;
import equipoalpha.loveletter.partida.Sala;

public class JugadorCliente extends Jugador {
    // el jugador cliente, manda comandos y mensajes al servidor
    public JugadorCliente() {
        super("temp");
    }

    //estos metodos hay que eliminarlos despues de jugador asi no son overrides
    @Override
    public Sala crearSala(String nombre) {
        return super.crearSala(nombre);
    }

    @Override
    public boolean unirseASala(Sala sala) {
        return super.unirseASala(sala);
    }

    @Override
    public void iniciarPartida() {
        super.iniciarPartida();
    }

    @Override
    public void confirmarInicio() {
        super.confirmarInicio();
    }

    @Override
    public void cancelarInicio() {
        super.cancelarInicio();
    }

    @Override
    public boolean descartarCarta1() {
        Gson gson = new Gson();
        JsonObject json = new JsonObject();
        json.add("CartaDescartada", gson.toJsonTree(carta1, Carta.class));
        Cliente.getINSTANCE().send(ComandoTipo.DescartarCarta1, json);
        return true;
    }

    @Override
    public boolean descartarCarta2() {
        Gson gson = new Gson();
        JsonObject json = new JsonObject();
        json.add("CartaDescartada", gson.toJsonTree(carta2, Carta.class));
        Cliente.getINSTANCE().send(ComandoTipo.DescartarCarta2, json);
        return true;
    }

    @Override
    public void elegirJugador(Jugador jugador) {

    }

    @Override
    public void elegirCarta(CartaTipo cartaAdivinada) {
        super.elegirCarta(cartaAdivinada);
    }

    @Override
    public void verCarta(Jugador jugador) {
        super.verCarta(jugador);
    }

    @Override
    public void terminarDeVer() {
        super.terminarDeVer();
    }
}
