package equipoalpha.loveletter.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import equipoalpha.loveletter.common.ComandoTipo;
import equipoalpha.loveletter.common.MensajeNetwork;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {
    private final String ip;
    private final int port;
    private Socket socketCliente;
    private JugadorCliente jugadorCliente;
    private PrintWriter output;
    private BufferedReader input;

    public Cliente(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void connect(String nombre) {
        try {
            socketCliente = new Socket(ip, port);
            output = new PrintWriter(socketCliente.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
            this.jugadorCliente = new JugadorCliente(nombre);
            LoveLetter.getInstance().listener = new ServidorListener(this);
            LoveLetter.getInstance().listener.start();
            JsonObject json = new JsonObject();
            json.addProperty("nombre", nombre);
            send(ComandoTipo.Conectarse, json);
        } catch (Exception ex) {
            System.out.println("Fallo al recibir del servidor");
            ex.printStackTrace();
        }
    }

    public void send(ComandoTipo tipo, JsonObject objeto) {
        output.println((new Gson()).toJson(new MensajeNetwork(tipo, objeto)));
        output.flush();
    }

    public JugadorCliente getJugadorCliente() {
        return jugadorCliente;
    }

    public BufferedReader getInput() {
        return this.input;
    }
}
