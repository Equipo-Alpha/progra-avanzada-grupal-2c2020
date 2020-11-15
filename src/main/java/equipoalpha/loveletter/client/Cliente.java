package equipoalpha.loveletter.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {
    private static Cliente INSTANCE = null;
    private final String ip;
    private final int port;
    private Socket socketCliente;
    private PrintWriter output;
    private BufferedReader input;

    public Cliente(String ip, int port) {
        this.ip = ip;
        this.port = port;
        INSTANCE = this;
    }

    public static Cliente getINSTANCE() {
        return INSTANCE;
    }

    public void connect() {
        try {
            socketCliente = new Socket(ip, port);
            output = new PrintWriter(socketCliente.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));

            //send(MensajeTipo.CONEXION);
        } catch (Exception ex) {
            System.out.println("Fallo al recibir del servidor");
            ex.printStackTrace();
            System.exit(0);
        }
    }

    public BufferedReader getInput() {
        return this.input;
    }
}
