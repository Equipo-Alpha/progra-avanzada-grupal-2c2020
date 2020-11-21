package equipoalpha.loveletter.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import equipoalpha.loveletter.carta.Carta;
import equipoalpha.loveletter.carta.CartaTipo;
import equipoalpha.loveletter.common.ComandoTipo;
import equipoalpha.loveletter.common.PartidaInfo;
import equipoalpha.loveletter.common.SalaInfo;
import equipoalpha.loveletter.jugador.EstadosJugador;
import equipoalpha.loveletter.jugador.Jugador;
import equipoalpha.loveletter.pantalla.Imagenes;
import equipoalpha.loveletter.util.JsonUtils;

import javax.swing.ImageIcon;

public class JugadorCliente extends Jugador {

    private final SalaInfo salaActual;
    private final PartidaInfo partidaActual;
    private final Cliente cliente;
    public ImageIcon icono;
    public boolean elegirseASiMismo;
    public Carta cartaViendo;
    private EstadosJugador estadoActual;

    // el jugador cliente, manda comandos y mensajes al servidor
    public JugadorCliente(String nombre) {
        super(nombre);
        this.icono = Imagenes.iconoPrincipe;
        this.iconoNombre = "principe";
        this.cliente = LoveLetter.getInstance().getCliente();
        this.salaActual = new SalaInfo();
        this.partidaActual = new PartidaInfo();
    }

    //estos metodos hay que eliminarlos despues de jugador asi no son overrides
    public void crearSala(String nombre) {
        this.cliente.send(ComandoTipo.CrearSala, new JsonObject());
    }

    public void buscarSalas() {
        this.cliente.send(ComandoTipo.SalaSync, new JsonObject());
    }

    public void unirseASala(String nombre) {
        JsonObject json = new JsonObject();
        json.addProperty("nombre", nombre);
        this.cliente.send(ComandoTipo.UnirseSala, json);
    }

    public void iniciarPartida() {
        this.cliente.send(ComandoTipo.PartidaEmpezada, new JsonObject());
    }

    public void confirmarInicio() {
        JsonObject json = new JsonObject();
        json.addProperty("check", true);
        this.cliente.send(ComandoTipo.ConfirmarInicio, json);
    }

    public void cancelarInicio() {
        JsonObject json = new JsonObject();
        json.addProperty("check", true);
        this.cliente.send(ComandoTipo.CancelarInicio, json);
    }

    public void descartarCarta1() {
        Gson gson = new Gson();
        JsonObject json = new JsonObject();
        json.add("CartaDescartada", gson.toJsonTree(carta1.getTipo()));
        this.cliente.send(ComandoTipo.DescartarCarta1, json);
    }

    public void descartarCarta2() {
        Gson gson = new Gson();
        JsonObject json = new JsonObject();
        json.add("CartaDescartada", gson.toJsonTree(carta2.getTipo()));
        this.cliente.send(ComandoTipo.DescartarCarta2, json);
    }

    public void elegirJugador(String jugador) {
        JsonObject json = new JsonObject();
        json.addProperty("JugadorElegido", jugador);
        this.cliente.send(ComandoTipo.ElegirJugador, json);
    }

    public void elegirCarta(CartaTipo cartaAdivinada) {
        Gson gson = new Gson();
        JsonObject json = new JsonObject();
        json.add("CartaAdivinada", gson.toJsonTree(cartaAdivinada));
        this.cliente.send(ComandoTipo.AdivinarCarta, json);
    }

    public void terminarDeVer() {
        this.cliente.send(ComandoTipo.TerminarDeVer, new JsonObject());
    }

    public void animacionInicioTerminada() {
        this.cliente.send(ComandoTipo.ContinuarComienzo, new JsonObject());
    }

    @Override
    public void salirSala() {
        JsonObject json = new JsonObject();
        json.addProperty("check", true);
        this.cliente.send(ComandoTipo.SalirSala, json);
    }

    @Override
    public void deserializarData(JsonObject object) {
        super.deserializarData(object);
        this.estadoActual = new Gson().fromJson(object.get("estado"), EstadosJugador.class);
        if (object.has("elegirseASiMismo")) {
            this.elegirseASiMismo = JsonUtils.getBoolean(object, "elegirseASiMismo");
        }
        if (object.has("cartaViendo")) {
            this.cartaViendo = new Gson().fromJson(object.get("cartaViendo"), Carta.class);
        }
    }

    public EstadosJugador getEstado() {
        return estadoActual;
    }

    public SalaInfo getSalaActual() {
        return salaActual;
    }

    public PartidaInfo getPartidaActual() {
        return partidaActual;
    }
}
