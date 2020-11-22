package equipoalpha.loveletter.pantalla;

import equipoalpha.loveletter.client.LoveLetter;

import javax.swing.*;
import java.awt.*;

public class VentanaChat {
    private final JFrame ventana;
    private final PanelMensajes panel;

    public VentanaChat() {
        this.ventana = new JFrame("Chat");
        this.ventana.setResizable(false);
        this.ventana.setSize(400, 768);
        this.ventana.setPreferredSize(new Dimension(420, 600));
        this.ventana.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.ventana.setLocationRelativeTo(null);
        this.ventana.setVisible(false);
        this.panel = new PanelMensajes();
        this.ventana.add(panel);
        this.ventana.pack();
    }

    public void cambiarVisibilidad() {
        this.panel.setVisible(!this.ventana.isVisible());
        this.ventana.setVisible(!this.ventana.isVisible());
    }

    public void ocultar() {
        this.panel.setVisible(false);
        this.ventana.setVisible(false);
    }

    public void agregarMensaje(String mensaje) {
        this.panel.agregarMensaje(mensaje);
    }

    private class PanelMensajes extends JPanel{
        private final JTextArea areaMensajes;
        private final JScrollPane panelMensajes;
        private final JTextField textField;
        private final JButton botonEnviar;

        public PanelMensajes() {
            this.areaMensajes = new JTextArea();
            this.botonEnviar = new JButton("Enviar Mensaje");
            this.botonEnviar.setBackground(new Color(63, 78, 99));
            this.botonEnviar.setForeground(Color.WHITE);
            this.botonEnviar.setFont(new Font("Montserrat", Font.PLAIN, 22));
            this.textField = new JTextField();
            this.textField.setBackground(new Color(63, 78, 99));
            this.textField.setForeground(Color.WHITE);
            this.textField.setFont(new Font("Montserrat", Font.PLAIN, 22));
            this.areaMensajes.setEditable(false);
            this.areaMensajes.setBackground(new Color(63, 78, 99));
            this.areaMensajes.setForeground(Color.WHITE);
            this.areaMensajes.setFont(new Font("Montserrat", Font.PLAIN, 20));
            this.areaMensajes.setLineWrap(true);
            this.botonEnviar.addActionListener(actionEvent -> {
                String mensaje = textField.getText();
                LoveLetter.getInstance().getCliente().getJugadorCliente().enviarMensajeChat(mensaje);
                textField.setText("");
            });
            this.panelMensajes = new JScrollPane(areaMensajes);
            add(panelMensajes);
            add(textField);
            add(botonEnviar);
            this.setBackground(new Color(37, 46, 59));
            this.repaint();
        }

        public void agregarMensaje(String mensaje) {
            this.areaMensajes.append(mensaje + "\n");
            this.repaint();
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            this.areaMensajes.setBounds(0,0,380,400);
            this.panelMensajes.setBounds(10,10, 380, 400);
            this.textField.setBounds(10, 420, 380, 40);
            this.botonEnviar.setBounds(10, 480, 380, 40);
        }
    }
}
