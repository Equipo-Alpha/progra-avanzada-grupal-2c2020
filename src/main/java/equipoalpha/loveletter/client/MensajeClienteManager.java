package equipoalpha.loveletter.client;

import equipoalpha.loveletter.common.MensajeNetwork;
import equipoalpha.loveletter.common.MensajeTipo;

import java.util.HashMap;

public class MensajeClienteManager {
    private static final MensajeClienteManager instancia = new MensajeClienteManager();
    private final HashMap<MensajeTipo, Mensaje> mapaHandlers = new HashMap<>();

    private MensajeClienteManager() {
        init();
    }

    public static MensajeClienteManager getInstancia() {
        return instancia;
    }

    private void init() {
        // registrar los handlers para cada mensaje
        ServidorMensajesHandler handlers = new ServidorMensajesHandler();

        registrar(MensajeTipo.ListaSala,         handlers::onListaSala);
        registrar(MensajeTipo.PartidaEmpezada,   handlers::onPartidaEmpezada);
        registrar(MensajeTipo.PartidaTerminada,  handlers::onPartidaTerminada);
        registrar(MensajeTipo.RondaTerminada,    handlers::onRondaTerminada);
        registrar(MensajeTipo.SincJugador,       handlers::onSincJugador);
        registrar(MensajeTipo.SincPartida,       handlers::onSincPartida);
        registrar(MensajeTipo.SincSala,          handlers::onSincSala);
        registrar(MensajeTipo.RondaEmpezada,     handlers::onRondaEmpezada);
        registrar(MensajeTipo.Confirmacion,      handlers::onConfirmacion);
        registrar(MensajeTipo.MensajeChat,       handlers::onNuevoMensajeChat);
        registrar(MensajeTipo.SinCreador,        handlers::onSinCreador);
    }

    private void registrar(MensajeTipo tipo, Mensaje handler) {
        mapaHandlers.putIfAbsent(tipo, handler);
    }

    public void procesar(MensajeTipo tipo, MensajeNetwork mensaje) {
        Mensaje handler = mapaHandlers.get(tipo);
        if (handler == null) {
            throw new IllegalStateException("No se registro el mensaje");
        }
        handler.procesar(mensaje);
    }

}
