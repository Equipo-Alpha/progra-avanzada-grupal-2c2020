package equipoalpha.loveletter.server;

import com.google.gson.JsonObject;
import equipoalpha.loveletter.common.MensajeNetwork;

public class JugadorComandoHandler {
    // cada uno de los handlers de los comandos del jugador

    public void onNuevoNombre(JugadorServer jugadorServer, MensajeNetwork mensaje) {
        JsonObject json = mensaje.getMensaje();
        String nombre = json.get("nombre").getAsString();
        jugadorServer.setNombre(nombre);
    }
}