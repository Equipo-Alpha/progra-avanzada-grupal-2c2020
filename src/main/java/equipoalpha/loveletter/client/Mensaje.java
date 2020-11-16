package equipoalpha.loveletter.client;

import equipoalpha.loveletter.common.MensajeNetwork;

public interface Mensaje {
    void procesar(MensajeNetwork mensaje);
}
