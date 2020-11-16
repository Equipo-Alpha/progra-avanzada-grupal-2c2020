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
        // registar los handlers para cada mensaje
        // posiblemente esten en ServidorMensajesHandler
    }

    private void registar(MensajeTipo tipo, Mensaje handler) {
        mapaHandlers.put(tipo, handler);
    }

    public void procesar(MensajeTipo tipo, MensajeNetwork mensaje) {
        Mensaje handler = mapaHandlers.get(tipo);
        if (handler == null) {
            throw new IllegalStateException("No se registro el evento");
        }
        handler.procesar(mensaje);
    }

}
