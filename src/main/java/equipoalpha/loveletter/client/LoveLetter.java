package equipoalpha.loveletter.client;

import equipoalpha.loveletter.carta.CartaTipo;
import equipoalpha.loveletter.pantalla.Imagenes;
import equipoalpha.loveletter.pantalla.Ventana;
import equipoalpha.loveletter.util.Handler;

import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoveLetter implements ActionListener {
    private static final LoveLetter instance = new LoveLetter();
    public static ClassLoader classLoader = LoveLetter.class.getClassLoader();
    public static Handler handler = Handler.getInstance();
    protected Ventana ventana;
    public final int WIDTH = 1024, HEIGHT = 768;
    public int fps = 60;
    private Cliente cliente;
    protected ServidorListener listener;

    private LoveLetter() {}

    public static void main(String[] args) {
        Imagenes.init();
        LoveLetter game = LoveLetter.getInstance();
        game.cliente = new Cliente("localhost", 20000);
        SwingUtilities.invokeLater(() -> game.ventana = new Ventana());
    }

    public static LoveLetter getInstance() {
        return instance;
    }

    private void render() {
        handler.render();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Ventana getVentana() {
        return ventana;
    }

    public ServidorListener getListener() {
        return listener;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        render();
    }
}
