package equipoalpha.loveletter.partida;

import com.google.gson.JsonObject;
import equipoalpha.loveletter.common.MensajeTipo;
import equipoalpha.loveletter.server.JugadorServer;

import java.util.ArrayList;
import java.util.List;

public class Chat {
    private final List<JugadorServer> jugadores;
    private final List<String> historial;

    public Chat(List<JugadorServer> jugadores) {
        this.jugadores = jugadores;
        this.historial = new ArrayList<>();
    }

    public void nuevoMensaje(JugadorServer remitente, String mensaje){
        String mensajeFinal = String.format("<%s>: %s", remitente, mensaje);
        historial.add(mensajeFinal);
        enviarMensaje(mensajeFinal);
    }


    public void nuevoMensaje(String mensaje) {
        // solo para mensajes de la partida
        String mensajeFinal = String.format("<Partida>: %s", mensaje);
        historial.add(mensajeFinal);
        enviarMensaje(mensajeFinal);
    }

    private void enviarMensaje(String mensaje) {
        JsonObject json = new JsonObject();
        json.addProperty("mensaje", mensaje);
        for (JugadorServer jugador : jugadores) {
            if (jugador.getListener() == null) continue;
            jugador.getListener().send(MensajeTipo.MensajeChat, json);
        }
    }

}
