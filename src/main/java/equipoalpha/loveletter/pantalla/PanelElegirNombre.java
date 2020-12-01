package equipoalpha.loveletter.pantalla;

import equipoalpha.loveletter.client.Cliente;
import equipoalpha.loveletter.client.LoveLetter;
import equipoalpha.loveletter.util.Drawable;

import javax.swing.*;
import java.awt.*;

public class PanelElegirNombre extends JPanel implements Drawable {
    private static final long serialVersionUID = 2L;
    private final JButton botonIniciarSesion;
    private final JLabel textoValido;
    private final JPasswordField passField;
    private final JTextField textField;

    public PanelElegirNombre() {
        textoValido = new JLabel("Nombre invalido");
        textField = new JTextField("Usuario");
        passField = new JPasswordField("Password");
        botonIniciarSesion = new JButton("Iniciar Sesion");

        textField.setToolTipText("Nombre de usuario");
        Font fuente = new Font("Monotype Corsiva", Font.BOLD, 32);
        Font fuente2 = new Font("Monotype Corsiva", Font.BOLD, 28);
        textField.setFont(fuente);
        passField.setFont(fuente);
        botonIniciarSesion.setFont(fuente);
        botonIniciarSesion.setBackground(Color.BLACK);
        botonIniciarSesion.setForeground(Color.WHITE);
        textoValido.setFont(fuente2);
        textoValido.setVisible(false);
        textoValido.setForeground(Color.WHITE);

        botonIniciarSesion.addActionListener(event -> {
            if (textField.getText().length() < 4) {
                textoValido.setText("<html>El nombre debe tener<br>mas de 4 caracteres</html>");
                textoValido.setVisible(true);
            } else if (passField.getPassword().length < 4) {
                textoValido.setText("<html>La clave debe tener<br>mas de 4 caracteres<html>");
                textoValido.setVisible(true);
            } else if (esInvalido(textField.getText()) || esInvalido(new String(passField.getPassword()))) {
                textoValido.setText("<html>Solo se permiten<br>letras y numeros<br>en el nombre/clave</html>");
                textoValido.setVisible(true);
            } else {
                textoValido.setVisible(false);
                crearJugador(textField.getText(), new String(passField.getPassword()));
            }
        });

        add(textField);
        add(passField);
        add(botonIniciarSesion);
        add(textoValido);
        registrar();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(Imagenes.fondoPrincesa, null, 0, 0);
        g2.drawImage(Imagenes.logo, null, 240, 40);

        textoValido.setBounds(300, 540, 300, 90);
        textField.setBounds(300, 260, 220, 64);
        passField.setBounds(300, 360, 220, 64);
        botonIniciarSesion.setBounds(300, 460, 220, 64);
    }

    @Override
    public void render() {
        this.repaint();
    }

    private void crearJugador(String nombre, String clave) {
        Cliente cliente = LoveLetter.getInstance().getCliente();
        cliente.connect(nombre, clave);
    }

    public boolean esInvalido(String s) {
        String n = ".*[!-/:-?{-~].*";
        return s.matches(n);
    }
}
