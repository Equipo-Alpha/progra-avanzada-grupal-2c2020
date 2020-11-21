package equipoalpha.loveletter.server;

import equipoalpha.loveletter.partida.Sala;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class LoveLetterServidor extends Thread{
    private static LoveLetterServidor INSTANCE;
    private final int port;
    private ServerSocket serverSocket;
    private final List<ClientListener> jugadores;
    private final ArrayList<Sala> salas;
    private boolean isRunning;
    public static Logger log = LogManager.getLogger("LoveLeter Servidor");

    public LoveLetterServidor(int port) {
        this.port = port;
        INSTANCE = this;
        this.jugadores = new ArrayList<>();
        this.salas = new ArrayList<>();
    }

    public static LoveLetterServidor getINSTANCE() {
        return INSTANCE;
    }

    public static void main(String[] args) {
        LoveLetterServidor server = new LoveLetterServidor(20000);
        server.setName("Servidor");
        server.start();
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(this.port);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
        this.isRunning = true;
        int id = 1;
        log.info("Servidor iniciado!");
        try {
            while (isRunning) {
                Socket clienteSocket = serverSocket.accept();
                log.info("Se conecto un nuevo cliente: " + clienteSocket);

                PrintWriter salida = new PrintWriter(clienteSocket.getOutputStream(), true);
                BufferedReader entrada = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));

                ClientListener clienteListener = new ClientListener(clienteSocket, entrada, salida, id++);
                clienteListener.start();
                this.jugadores.add(clienteListener);
                log.info("Cliente agregado correctamente");
            }
        } catch (IOException e) {
            e.printStackTrace();
            this.stopServer();
        }
    }

    public void stopServer() {
        if (serverSocket != null) {
            try {
                serverSocket.close();
                serverSocket = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void agregarSala(Sala sala) {
        this.salas.add(sala);
    }

    public Sala getSalaPorNombre(String nombre) {
        for (Sala sala : salas) {
            if (sala.nombre.equals(nombre)) {
                return sala;
            }
        }
        return null;
    }

    public void eliminarSala(Sala sala) {
        this.salas.remove(sala);
    }

    public ArrayList<Sala> getSalas() {
        return salas;
    }
}
