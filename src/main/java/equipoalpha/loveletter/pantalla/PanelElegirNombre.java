package equipoalpha.loveletter.pantalla;

import equipoalpha.loveletter.client.Cliente;
import equipoalpha.loveletter.client.LoveLetter;
import equipoalpha.loveletter.util.Drawable;

import javax.swing.*;
import java.awt.*;

public class PanelElegirNombre extends JPanel implements Drawable {
    private static final long serialVersionUID = 2L;
    private final LoveLetter loveletter;
    private final JButton button;
    private final JLabel textoValido;
    private final JPasswordField passField;
    private final JTextField textField;

    public PanelElegirNombre() {
        loveletter = LoveLetter.getInstance();

        textoValido = new JLabel("Nombre invalido");
        textField = new JTextField("Test");
        passField = new JPasswordField();
        button = new JButton("Aceptar");

        textField.setToolTipText("Nombre de usuario");
        Font fuente = new Font("Arial", Font.PLAIN, 24);
        textField.setFont(fuente);
        passField.setFont(fuente);
        button.setFont(fuente);
        textoValido.setFont(fuente);
        textoValido.setVisible(false);
        textoValido.setForeground(Color.WHITE);

        button.addActionListener(event -> {
            if (textField.getText().isEmpty()) {
                textoValido.setVisible(true);
            } else {
                textoValido.setVisible(false);
                if (passField.getPassword().length < 3)
                    crearJugador(textField.getText());
                else
                    crearJugador(textField.getText(), new String(passField.getPassword()));
            }
        });

        add(textField);
        add(passField);
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
        g2.drawString("Inicia sesion:", 360, 240);

        textoValido.setBounds(360, 600, 300, 64);
        textField.setBounds(360, 300, 300, 64);
        passField.setBounds(360, 400, 300, 64);
        button.setBounds(360, 500, 300, 64);
    }

    @Override
    public void render() {
        this.repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1024, 768);
    }

    private void crearJugador(String nombre) {
        Cliente cliente = LoveLetter.getInstance().getCliente();
        cliente.connect(nombre);
    }

    private void crearJugador(String nombre, String clave) {
        Cliente cliente = LoveLetter.getInstance().getCliente();
        cliente.connect(nombre, clave);
    }
}
