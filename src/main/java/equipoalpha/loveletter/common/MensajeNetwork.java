package equipoalpha.loveletter.common;

import com.google.gson.JsonObject;

public class MensajeNetwork {
    private final ComandoTipo tipoComando; // si es Cliente -> Servidor
    private final MensajeTipo tipoMensaje; // si es Servidor -> Cliente
    private final int idClient; // tal vez el id no sea necesario
    private final JsonObject mensaje;

    public MensajeNetwork(ComandoTipo tipo, int idClient, JsonObject mensaje) {
        this.tipoComando = tipo;
        this.tipoMensaje = null;
        this.idClient = idClient;
        this.mensaje = mensaje;
    }

    public MensajeNetwork(MensajeTipo tipo, int idClient, JsonObject mensaje) {
        this.tipoComando = null;
        this.tipoMensaje = tipo;
        this.idClient = idClient;
        this.mensaje = mensaje;
    }

    public MensajeNetwork(MensajeTipo tipo, JsonObject mensaje) {
        this.tipoComando = null;
        this.tipoMensaje = tipo;
        this.idClient = -1;
        this.mensaje = mensaje;
    }

    public MensajeNetwork(ComandoTipo tipo, JsonObject mensaje) {
        this.tipoComando = tipo;
        this.tipoMensaje = null;
        this.idClient = -1;
        this.mensaje = mensaje;
    }

    public ComandoTipo getTipoComando() {
        return tipoComando;
    }

    public MensajeTipo getTipoMensaje() {
        return tipoMensaje;
    }

    public int getIdClient() {
        return idClient;
    }

    public JsonObject getMensaje() {
        return mensaje;
    }
}
