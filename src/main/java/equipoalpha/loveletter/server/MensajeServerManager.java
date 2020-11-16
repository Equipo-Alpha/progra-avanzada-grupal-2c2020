package equipoalpha.loveletter.server;

import equipoalpha.loveletter.common.ComandoTipo;
import equipoalpha.loveletter.common.MensajeNetwork;

import java.util.HashMap;

public class MensajeServerManager {
    private static final MensajeServerManager instancia = new MensajeServerManager();
    private final HashMap<ComandoTipo, Comando> mapaHandlers = new HashMap<>();

    private MensajeServerManager() {
        init();
    }

    public static MensajeServerManager getInstancia() {
        return instancia;
    }

    private void init() {
        // registar los handlers para cada mensaje
        // posiblemente esten en JugadorComandoHandler
        JugadorComandoHandler handlers = new JugadorComandoHandler();
        registrar(ComandoTipo.Conectarse, handlers::onNuevoNombre);
    }

    private void registrar(ComandoTipo tipo, Comando handler) {
        mapaHandlers.put(tipo, handler);
    }

    public void procesar(ComandoTipo tipo, JugadorServer jugador, MensajeNetwork mensaje) {
        Comando comando = mapaHandlers.get(tipo);
        if (comando == null) {
            throw new IllegalStateException("No se registro el evento");
        }
        comando.procesar(jugador, mensaje);
    }
}
