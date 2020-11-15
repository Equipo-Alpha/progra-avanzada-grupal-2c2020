package equipoalpha.loveletter.server;

import equipoalpha.loveletter.common.MensajeNetwork;
import equipoalpha.loveletter.common.MensajeTipo;

import java.util.HashMap;

public class MensajeServerManager {
    private static final MensajeServerManager instancia = new MensajeServerManager();
    private HashMap<MensajeTipo, Comando> mapaHandlers = new HashMap<>();

    private MensajeServerManager() {
    }

    public static MensajeServerManager getInstancia() {
        return instancia;
    }

    public void init() {
        // registar los handlers para cada mensaje
        // posiblemente esten en JugadorComandoHandler
        JugadorComandoHandler handlers = new JugadorComandoHandler();
        registrar(MensajeTipo.Conectarse, handlers::onNuevoNombre);
    }

    public void registrar(MensajeTipo tipo, Comando handler) {
        mapaHandlers.put(tipo, handler);
    }

    public void procesar(MensajeTipo tipo, JugadorServer jugador, MensajeNetwork mensaje) {
        Comando comando = mapaHandlers.get(tipo);
        if (tipo == null) {
            throw new IllegalStateException("No se registro el evento");
        }
        comando.procesar(jugador, mensaje);
    }
}
