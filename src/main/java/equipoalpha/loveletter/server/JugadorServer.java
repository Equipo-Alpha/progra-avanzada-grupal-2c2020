package equipoalpha.loveletter.server;

import equipoalpha.loveletter.jugador.Jugador;

import java.net.Socket;

public class JugadorServer extends Jugador {
    // el jugador, pero del lado del servidor
    // este es el que tiene el facade y procesa los comandos del jugador cliente
    private final Socket socket;
    private final Integer id;

    public JugadorServer(Socket socket, int id) {
        super("tesmp");
        this.socket = socket;
        this.id = id;
    }

    public Socket getSocket() {
        return socket;
    }

    public Integer getId() {
        return id;
    }

    public void setNombre(String nombre){

    }
}
