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
        JugadorComandoHandler handlers = new JugadorComandoHandler();

        registrar(ComandoTipo.Conectarse,               handlers::onNuevoNombre);
        registrar(ComandoTipo.SalaSync,                 handlers::onReqSala);
        registrar(ComandoTipo.UnirseSala,               handlers::onUnirseSala);
        registrar(ComandoTipo.CrearSala,                handlers::onCrearSala);
        registrar(ComandoTipo.AgregarBot,               handlers::onAgregarBot);
        registrar(ComandoTipo.ConfigurarSala,           handlers::onConfigurarSala);
        registrar(ComandoTipo.PartidaEmpezada,          handlers::onPartidaEmpezada);
        registrar(ComandoTipo.ConfirmarInicio,          handlers::onConfirmarInicio);
        registrar(ComandoTipo.CancelarInicio,           handlers::onCancelarInicio);
        registrar(ComandoTipo.SalirSala,                handlers::onSalirSala);
        registrar(ComandoTipo.DescartarCarta1,          handlers::onDescartarCarta1);
        registrar(ComandoTipo.DescartarCarta2,          handlers::onDescartarCarta2);
        registrar(ComandoTipo.ElegirJugador,            handlers::onElegirJugador);
        registrar(ComandoTipo.AdivinarCarta,            handlers::onAdivinarCarta);
        registrar(ComandoTipo.TerminarDeVer,            handlers::onTerminarDeVer);
        registrar(ComandoTipo.ContinuarComienzo,        handlers::onContinuarComienzo);
        registrar(ComandoTipo.ContinuarFin,             handlers::onContinuarFin);
        registrar(ComandoTipo.ConfirmarVolverAJugar,    handlers::onConfirmarVolverAJugar);
        registrar(ComandoTipo.CancelarVolverJugar,      handlers::onCancelarVolverJugar);
        registrar(ComandoTipo.MensajeChat,              handlers::onMensajeChat);
    }

    private void registrar(ComandoTipo tipo, Comando handler) {
        mapaHandlers.put(tipo, handler);
    }

    public synchronized void procesar(ComandoTipo tipo, JugadorServer jugador, MensajeNetwork mensaje) {
        Comando comando = mapaHandlers.get(tipo);
        if (comando == null) {
            throw new IllegalStateException("No se registro el comando");
        }
        comando.procesar(jugador, mensaje);
    }
}
