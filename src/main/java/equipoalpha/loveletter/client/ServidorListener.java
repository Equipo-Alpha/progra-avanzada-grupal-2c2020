package equipoalpha.loveletter.client;

import java.io.BufferedReader;

public class ServidorListener extends Thread{
    private final BufferedReader inputClient;
    private final ClientNetworkHandler cnh;

    public ServidorListener(Cliente client) {
        this.inputClient = client.getInput();
        this.cnh = new ClientNetworkHandler();
    }

    public void run() {
        try {
            String input;
            while ((input = inputClient.readLine()) != null) {
                //cnh.processInput(input);
            }
        } catch (Exception ex) {
            System.out.println("Fallo al recibir del servidor");
            ex.printStackTrace();
            System.exit(0);
        }
    }


}
