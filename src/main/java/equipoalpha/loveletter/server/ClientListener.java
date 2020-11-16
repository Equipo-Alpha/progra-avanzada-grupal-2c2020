package equipoalpha.loveletter.server;

import com.google.gson.Gson;
import equipoalpha.loveletter.common.MensajeNetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientListener extends Thread {
    private final Socket socket;
    private final BufferedReader entrada;
    private final PrintWriter salida;
    private final int id;
    private final JugadorServer jugador;
    private final MensajeServerManager msm;

    public ClientListener(Socket socket, BufferedReader entrada, PrintWriter salida, int id) {
        this.socket = socket;
        this.entrada = entrada;
        this.salida = salida;
        this.id = id;
        this.jugador = new JugadorServer(socket, id);
        this.msm = MensajeServerManager.getInstancia();
    }

    @Override
    public void run() {
        try {
            String input;
            while ((input = entrada.readLine()) != null) {
                Gson gson = new Gson();
                MensajeNetwork mensaje = gson.fromJson(input, MensajeNetwork.class);
                msm.procesar(mensaje.getTipoComando(), this.jugador, mensaje);
            }
        } catch (IOException ex) {
            if (!this.isInterrupted()) {
                System.out.println("Se desconecto incorrectamente el cliente " + this.id);
            }
        }
    }
}
