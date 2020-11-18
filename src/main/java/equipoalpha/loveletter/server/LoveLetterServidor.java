package equipoalpha.loveletter.server;

import equipoalpha.loveletter.partida.Sala;

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
        //MensajeServerManager.getInstancia().init();
        int id = 1;
        try {
            while (isRunning) {
                Socket clienteSocket = serverSocket.accept();
                System.out.println("Se conecto un nuevo cliente: " + clienteSocket);

                PrintWriter salida = new PrintWriter(clienteSocket.getOutputStream(), true);
                BufferedReader entrada = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));

                ClientListener clienteListener = new ClientListener(clienteSocket, entrada, salida, id++);
                clienteListener.start();
                this.jugadores.add(clienteListener);
                System.out.println("Cliente agregado correctamente");
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
