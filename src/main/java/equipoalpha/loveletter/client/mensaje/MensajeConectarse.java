package equipoalpha.loveletter.client.mensaje;

import equipoalpha.loveletter.common.Mensaje;

public class MensajeConectarse implements Mensaje {
    // ejemplo de mensaje, le envia un mensaje al servidor con el nombre del jugador
    private final String nombre;

    public MensajeConectarse(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
