package equipoalpha.loveletter.server;

import equipoalpha.loveletter.database.BaseDeDatos;
import equipoalpha.loveletter.partida.Sala;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LoveLetterServidor extends Thread {
    public static Logger log = LogManager.getLogger("LoveLeter Servidor");
    private static LoveLetterServidor INSTANCE;
    private final int port;
    private final List<ClientListener> jugadores;
    private final ArrayList<Sala> salas;
    private ServerSocket serverSocket;
    private final BaseDeDatos bd;
    private boolean isRunning;

    public LoveLetterServidor(int port) {
        this.port = port;
        INSTANCE = this;
        this.jugadores = new ArrayList<>();
        this.salas = new ArrayList<>();
        this.bd = new BaseDeDatos();
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
                log.info("Cliente conectado correctamente");
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

    public BaseDeDatos getBd() {
        return bd;
    }

    public void eliminarSala(Sala sala) {
        this.salas.remove(sala);
    }

    public ArrayList<Sala> getSalas() {
        return salas;
    }

    public void agregarJugador(ClientListener cl) {
        this.jugadores.add(cl);
    }

    public void eliminarJugador(ClientListener cl) {
        this.jugadores.remove(cl);
    }

    public List<ClientListener> getJugadores() {
        return Collections.unmodifiableList(this.jugadores);
    }
}
