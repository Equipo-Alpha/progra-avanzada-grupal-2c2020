package equipoalpha.loveletter.client;

import com.google.gson.Gson;
import equipoalpha.loveletter.common.MensajeNetwork;

import javax.swing.SwingUtilities;
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
                SwingUtilities.invokeLater(() -> cnh.procesar(mensaje.getTipoMensaje(), mensaje));
            }
        } catch (Exception ex) {
            LoveLetter.getInstance().ventana.onServerDesconectado();
        }
    }

}
