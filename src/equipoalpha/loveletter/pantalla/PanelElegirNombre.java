package equipoalpha.loveletter.pantalla;

import equipoalpha.loveletter.LoveLetter;
import equipoalpha.loveletter.jugador.Jugador;
import equipoalpha.loveletter.util.Drawable;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelElegirNombre extends JPanel implements Drawable {
    private static final long serialVersionUID = 2L;
    private final Ventana parent;
    private final LoveLetter loveletter;
    private final JButton button;
    private final JLabel textoValido;
    private final JTextField textField;

    public PanelElegirNombre(Ventana ventana) {
        parent = ventana;
        setSize(1024, 768);
        loveletter = LoveLetter.getInstance();

        textoValido = new JLabel("Nombre invalido");
        textField = new JTextField("Test");
        button = new JButton("Aceptar");

        textField.setToolTipText("Ingresa tu nombre");
        textField.setFont(new Font("Arial", Font.PLAIN, 24));
        button.setFont(new Font("Arial", Font.PLAIN, 24));
        textoValido.setFont(new Font("Arial", Font.BOLD, 24));
        textoValido.setVisible(false);
        textoValido.setForeground(Color.WHITE);

        button.addActionListener(event -> {
            if (textField.getText().isEmpty()) {
                textoValido.setVisible(true);
            } else {
                textoValido.setVisible(false);
                crearJugador(textField.getText(), this);
            }
        });

        add(textField);
        add(button);
        add(textoValido);
        registrar();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(Imagenes.background, null, 0, 0);

        Color color = new Color(0, 0, 0, 185);
        g2.setColor(color);
        g2.fillRect((loveletter.WIDTH / 2) - 200, 0, 400, loveletter.HEIGHT);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 40));
        g2.drawString("LOVE LETTER", 370, 100);

        g2.setFont(new Font("Arial", Font.BOLD, 32));
        g2.drawString("Ingresa Tu Nombre:", 360, 240);

        textoValido.setBounds(360, 500, 300, 64);
        textField.setBounds(360, 300, 300, 64);
        button.setBounds(360, 400, 300, 64);

        if (LoveLetter.DEBUGGING) {
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Dialog", Font.BOLD, 16));
            g2.drawString("FPS: " + loveletter.fps + "", 20, 25);
        }
    }

    @Override
    public void render() {
        this.repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1024, 768);
    }

    private void crearJugador(String nombre, JPanel panel) {
        SwingWorker<Boolean, Jugador> worker = new SwingWorker<Boolean, Jugador>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                Jugador jugador = new Jugador(nombre);
                publish(jugador);
                return true;
            }

            @Override
            protected void process(List<Jugador> list) {
                loveletter.setJugador(list.remove(0));
            }

            @Override
            protected void done() {
                parent.onLogin(panel);
            }
        };
        worker.execute();
    }
}
