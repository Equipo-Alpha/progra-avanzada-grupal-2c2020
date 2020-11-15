package equipoalpha.loveletter.common;

import com.google.gson.JsonObject;

public class MensajeNetwork {
    private final MensajeTipo tipo;
    private final int idClient; // tal vez el id no sea necesario
    private final JsonObject mensaje;

    public MensajeNetwork(MensajeTipo tipo, int idClient, JsonObject mensaje) {
        this.tipo = tipo;
        this.idClient = idClient;
        this.mensaje = mensaje;
    }

    public MensajeTipo getTipo() {
        return tipo;
    }

    public int getIdClient() {
        return idClient;
    }

    public JsonObject getMensaje() {
        return mensaje;
    }
}
