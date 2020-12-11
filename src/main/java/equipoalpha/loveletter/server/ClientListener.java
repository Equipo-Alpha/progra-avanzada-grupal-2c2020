package equipoalpha.loveletter.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import equipoalpha.loveletter.common.ComandoTipo;
import equipoalpha.loveletter.common.MensajeNetwork;
import equipoalpha.loveletter.common.MensajeTipo;
import equipoalpha.loveletter.database.JugadorData;
import equipoalpha.loveletter.util.JsonUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientListener extends Thread {
    private final Socket socket;
    private final BufferedReader entrada;
    private final PrintWriter salida;
    private final int id;
    private final MensajeServerManager msm;
    private JugadorServer jugador = null;

    public ClientListener(Socket socket, BufferedReader entrada, PrintWriter salida, int id) {
        this.socket = socket;
        this.entrada = entrada;
        this.salida = salida;
        this.id = id;
        this.msm = MensajeServerManager.getInstancia();
        this.setName("temp" + id);
    }

    @Override
    public void run() {
        try {
            String input;
            while ((input = entrada.readLine()) != null) {
                Gson gson = new Gson();
                MensajeNetwork mensaje = gson.fromJson(input, MensajeNetwork.class);
                if (mensaje.getTipoComando() == ComandoTipo.Conectarse)
                    validarUsuario(mensaje);
                else if (this.jugador != null)
                    msm.procesar(mensaje.getTipoComando(), this.jugador, mensaje);
            }
        } catch (IOException ex) {
            if (!this.isInterrupted()) {
                LoveLetterServidor.log.error("Se desconecto incorrectamente el cliente " + this.id);
                if (this.jugador != null && this.jugador.salaActual != null) {
                    this.jugador.salirSala();
                }
                LoveLetterServidor.getINSTANCE().eliminarJugador(this);
            }
            try {
                socket.close();
                this.join();
            } catch (Exception ignored) {
            }
        }
    }

    public void send(MensajeTipo tipo, JsonObject objeto) {
        salida.println((new Gson()).toJson(new MensajeNetwork(tipo, objeto)));
        salida.flush();
    }

    public void validarUsuario(MensajeNetwork mensaje) {
        JsonObject json = mensaje.getMensaje();
        JsonObject respuesta = new JsonObject();
        respuesta.addProperty("id", 1); // 1 es el id del tipo de respuesta
        String nombre = JsonUtils.getString(json, "nombre");
        String pass = JsonUtils.getString(json, "password");
        JugadorData data = LoveLetterServidor.getINSTANCE().getBd().getJugadorPorNombre(nombre);
        if (data == null) {
            LoveLetterServidor.log.warn("Jugador nuevo, agregando ...");
            JugadorData nuevo = crearNuevoUsuario(nombre, pass);
            if (nuevo != null) {
                this.jugador = new JugadorServer(this, nombre, nuevo);
            } else {
                // error al crear nuevo juegador, usuario no disponible?
                respuesta.addProperty("tipo", false); // error
                send(MensajeTipo.Confirmacion, respuesta);
                return;
            }
        } else {
            if (data.getPassword().equals(pass) && !jugadorConectado(data.getId())) {
                this.jugador = new JugadorServer(this, nombre, data);
            } else {
                // contraseña incorrecta o ya esta conectado
                respuesta.addProperty("tipo", false); // error
                send(MensajeTipo.Confirmacion, respuesta);
                return;
            }
        }
        this.setName(nombre);
        LoveLetterServidor.getINSTANCE().agregarJugador(this);
        this.jugador.sincronizar(); // se sincroniza
        respuesta.addProperty("tipo", true); // lo setteo correctamente
        send(MensajeTipo.Confirmacion, respuesta);
    }

    private JugadorData crearNuevoUsuario(String nombre, String pass) {
        JugadorData data = new JugadorData();
        data.setNombre(nombre);
        data.setPassword(pass);
        data.setVictorias(0);
        data.setDerrotas(0);
        try {
            LoveLetterServidor.getINSTANCE().getBd().guardarNuevoJugador(data);
        } catch (Exception ex) {
            LoveLetterServidor.log.error("No se pudo guardar el nuevo jugador");
            LoveLetterServidor.getINSTANCE().getBd().rollBack();
            return null;
        }
        return data;
    }

    private boolean jugadorConectado(int id) {
        for (ClientListener cl : LoveLetterServidor.getINSTANCE().getJugadores()) {
            if (cl.jugador.getId() == id) {
                return true;
            }
        }
        return false;
    }
}
