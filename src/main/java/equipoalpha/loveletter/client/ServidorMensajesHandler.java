package equipoalpha.loveletter.client;

import com.google.gson.JsonObject;
import equipoalpha.loveletter.common.MensajeNetwork;
import equipoalpha.loveletter.jugador.EstadosJugador;
import equipoalpha.loveletter.pantalla.PanelMenuPrincipal;
import equipoalpha.loveletter.pantalla.PanelPartida;
import equipoalpha.loveletter.util.JsonUtils;

public class ServidorMensajesHandler {
    // handlers de los mensajes del servidor

    public void onListaSala(MensajeNetwork mensaje) {

    }

    public void onSincJugador(MensajeNetwork mensaje) {
        JsonObject data = mensaje.getMensaje();
        EstadosJugador before = LoveLetter.getInstance().getCliente().getJugadorCliente().getEstado();
        LoveLetter.getInstance().getCliente().getJugadorCliente().deserializarData(data);
        // puede que el estado del jugador haya cambiado
        if (before == LoveLetter.getInstance().getCliente().getJugadorCliente().getEstado()) return;
        if (LoveLetter.getInstance().getCliente().getJugadorCliente().getEstado()
                == EstadosJugador.CONFIRMANDOINICIO) {
            LoveLetter.getInstance().ventana.onConfirmarInicio();
        }
        if (LoveLetter.getInstance().getCliente().getJugadorCliente().getEstado()
                == EstadosJugador.ELIGIENDOJUGADOR) {
            ((PanelPartida) LoveLetter.getInstance().ventana.getPanelActual()).actualizarJugadores();
        }
    }

    public void onSincSala(MensajeNetwork mensaje) {
        JsonObject data = mensaje.getMensaje();
        LoveLetter.getInstance().getCliente().getJugadorCliente().getSalaActual().deserializarData(data);
    }

    public void onSincPartida(MensajeNetwork mensaje) {
        JsonObject data = mensaje.getMensaje();
        LoveLetter.getInstance().getCliente().getJugadorCliente().getPartidaActual().deserializarData(data);
    }

    public void onPartidaEmpezada(MensajeNetwork mensaje) {
        LoveLetter.getInstance().ventana.onPartidaEmpezada();
    }

    public void onRondaEmpezada(MensajeNetwork mensaje) {
        LoveLetter.getInstance().ventana.onRondaEmpezada();
    }

    public void onRondaTerminada(MensajeNetwork mensaje) {

    }

    public void onPartidaTerminada(MensajeNetwork mensaje) {
        LoveLetter.getInstance().ventana.onPartidaTerminadaMsg();
    }

    public void onConfirmacion(MensajeNetwork mensaje) {
        JsonObject json = mensaje.getMensaje();
        int id = JsonUtils.getInt(json, "id");
        switch (id) {
            case 1:
                if (JsonUtils.getBoolean(json, "tipo")) {
                    LoveLetter.getInstance().ventana.onLogin();
                }
                break;
            case 2:
            case 3:
                if (JsonUtils.getBoolean(json, "tipo")) {
                    ((PanelMenuPrincipal) LoveLetter.getInstance().ventana.getPanelActual()).setMoviendoCentro(true);
                }
                break;
            case 4:
                if (JsonUtils.getBoolean(json, "tipo")) {
                    LoveLetter.getInstance().ventana.onPartidaTerminada();
                }
            default:
        }
    }
}
