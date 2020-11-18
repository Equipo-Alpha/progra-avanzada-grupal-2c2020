package equipoalpha.loveletter.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import equipoalpha.loveletter.carta.CartaTipo;
import equipoalpha.loveletter.common.ComandoTipo;
import equipoalpha.loveletter.common.PartidaInfo;
import equipoalpha.loveletter.common.SalaInfo;
import equipoalpha.loveletter.jugador.EstadosJugador;
import equipoalpha.loveletter.jugador.Jugador;
import equipoalpha.loveletter.pantalla.Imagenes;
import equipoalpha.loveletter.partida.Sala;

import javax.swing.ImageIcon;

public class JugadorCliente extends Jugador {

    private SalaInfo salaActual;
    private PartidaInfo partidaActual;
    private EstadosJugador estadoActual;
    public ImageIcon icono;

    // el jugador cliente, manda comandos y mensajes al servidor
    public JugadorCliente(String nombre) {
        super(nombre);
        this.icono = Imagenes.iconoPrincipe;
        this.iconoNombre = "principe";
    }

    //estos metodos hay que eliminarlos despues de jugador asi no son overrides
    public void crearSala(String nombre) {

    }

    public void unirseASala(Sala sala) {

    }

    public void iniciarPartida() {

    }

    public void confirmarInicio() {

    }

    public void cancelarInicio() {

    }

    public void descartarCarta1() {
        Gson gson = new Gson();
        JsonObject json = new JsonObject();
        json.add("CartaDescartada", gson.toJsonTree(carta1.getTipo()));
        Cliente.getINSTANCE().send(ComandoTipo.DescartarCarta1, json);
    }

    public void descartarCarta2() {
        Gson gson = new Gson();
        JsonObject json = new JsonObject();
        json.add("CartaDescartada", gson.toJsonTree(carta2.getTipo()));
        Cliente.getINSTANCE().send(ComandoTipo.DescartarCarta2, json);
    }

    public void elegirJugador(Jugador jugador) {

    }

    public void elegirCarta(CartaTipo cartaAdivinada) {

    }

    public void verCarta(Jugador jugador) {

    }

    public void terminarDeVer() {

    }

    @Override
    public void salirSala() {

    }
}
