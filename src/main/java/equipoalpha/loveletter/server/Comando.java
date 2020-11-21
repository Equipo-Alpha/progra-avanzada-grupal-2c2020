package equipoalpha.loveletter.server;

import equipoalpha.loveletter.common.MensajeNetwork;

public interface Comando {
    void procesar(JugadorServer jugadorServer, MensajeNetwork mensaje);
}
