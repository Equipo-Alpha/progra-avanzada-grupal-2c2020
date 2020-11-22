package equipoalpha.loveletter.client;

import com.google.gson.Gson;
import equipoalpha.loveletter.common.MensajeNetwork;

import javax.swing.*;
import java.io.BufferedReader;

public class ServidorListener extends Thread {
    private final BufferedReader inputClient;
    private final MensajeClienteManager cnh;

    public ServidorListener(Cliente client) {
        this.inputClient = client.getInput();
        this.cnh = MensajeClienteManager.getInstancia();
    }

    public void run() {
        try {
            String input;
            while ((input = inputClient.readLine()) != null) {
                Gson gson = new Gson();
                MensajeNetwork mensaje = gson.fromJson(input, MensajeNetwork.class);
                System.out.println(mensaje.getTipoMensaje());
                SwingUtilities.invokeLater(() -> cnh.procesar(mensaje.getTipoMensaje(), mensaje));
            }
        } catch (Exception ex) {
            System.out.println("Fallo al recibir del servidor");
            ex.printStackTrace();
            System.exit(0);
        }
    }

}
