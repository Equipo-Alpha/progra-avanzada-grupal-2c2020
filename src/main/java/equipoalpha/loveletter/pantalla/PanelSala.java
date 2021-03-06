package equipoalpha.loveletter.pantalla;

import com.google.gson.JsonObject;
import equipoalpha.loveletter.client.JugadorCliente;
import equipoalpha.loveletter.client.LoveLetter;
import equipoalpha.loveletter.common.ComandoTipo;
import equipoalpha.loveletter.common.PlayerDummy;
import equipoalpha.loveletter.util.Drawable;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.util.ArrayList;
import java.util.function.Consumer;

public class PanelSala extends JPanel implements Drawable {
    private static final long serialVersionUID = 4L;
    private final LoveLetter loveletter;
    private final JugadorCliente cliente;
    private final JButton botonSetCondiciones;
    private final JButton botonChat;
    private final JPanel panelInfoJugador;
    private final JTextArea infoJugador;
    private final JButton salirInfo;
    private final JPanel panelSetCondiciones;
    private final JTextArea labelSimbolos;
    private final JComboBox<Integer> simbolosAfecto = new JComboBox<>();
    private final JTextArea labelJugadorMano;
    private final JComboBox<String> jugadorMano = new JComboBox<>();
    private final JTextArea labelCreadorNull;
    private final JComboBox<Boolean> creadorNull = new JComboBox<>();
    private final JButton aceptarConfiguracion;
    private final JButton botonEmpezarPartida;
    private final JButton botonAgregarBot;
    private final JButton botonJugador;
    private final JButton botonJugador1;
    private final JButton botonJugador2;
    private final JButton botonJugador3;
    private final JButton botonSalir;
    private boolean botAgregado = false;

    public PanelSala() {
        this.loveletter = LoveLetter.getInstance();
        this.cliente = LoveLetter.getInstance().getCliente().getJugadorCliente();

        botonSetCondiciones = new JButton("Configurar");
        botonEmpezarPartida = new JButton("Empezar");
        botonEmpezarPartida.setEnabled(false);
        botonSalir = new JButton("Salir de la Sala");
        aceptarConfiguracion = new JButton("Aceptar");
        salirInfo = new JButton("Volver");
        botonJugador = new JButton();
        botonJugador1 = new JButton();
        botonJugador2 = new JButton();
        botonJugador3 = new JButton();
        botonAgregarBot = new JButton("Agregar Bot");
        botonChat = new JButton("Chat");
        botonJugador1.setOpaque(true);
        botonJugador1.setBackground(new Color(255, 255, 255, 0));
        botonJugador2.setOpaque(true);
        botonJugador2.setBackground(new Color(255, 255, 255, 0));
        botonJugador3.setOpaque(true);
        botonJugador3.setBackground(new Color(255, 255, 255, 0));
        Font buttonFont = new Font("Monotype Corsiva", Font.BOLD, 24);
        botonSetCondiciones.setFont(buttonFont);
        botonSetCondiciones.setBackground(Color.BLACK);
        botonSetCondiciones.setForeground(Color.WHITE);
        botonEmpezarPartida.setFont(buttonFont);
        botonEmpezarPartida.setBackground(Color.BLACK);
        botonEmpezarPartida.setForeground(Color.WHITE);
        aceptarConfiguracion.setFont(buttonFont);
        salirInfo.setFont(buttonFont);
        botonAgregarBot.setFont(buttonFont);
        botonAgregarBot.setBackground(Color.BLACK);
        botonAgregarBot.setForeground(Color.WHITE);
        botonSalir.setFont(buttonFont);
        botonSalir.setBackground(Color.BLACK);
        botonSalir.setForeground(Color.WHITE);
        botonChat.setFont(buttonFont);
        botonChat.setBackground(Color.BLACK);
        botonChat.setForeground(Color.WHITE);
        panelInfoJugador = new JPanel();
        panelInfoJugador.setVisible(false);
        panelInfoJugador.setBackground(Color.BLACK);
        panelInfoJugador.setBorder(new BorderUIResource.LineBorderUIResource(Color.WHITE));
        salirInfo.addActionListener(actionEvent -> panelInfoJugador.setVisible(false));
        infoJugador = new JTextArea();
        panelSetCondiciones = new JPanel();
        panelSetCondiciones.setVisible(false);
        panelSetCondiciones.setBackground(Color.BLACK);
        panelSetCondiciones.setBorder(new BorderUIResource.LineBorderUIResource(Color.WHITE));
        simbolosAfecto.addItem(2);
        simbolosAfecto.addItem(3);
        simbolosAfecto.addItem(4);
        simbolosAfecto.addItem(5);
        simbolosAfecto.addItem(6);
        simbolosAfecto.addItem(7);
        creadorNull.addItem(true);
        creadorNull.addItem(false);
        Font labelsConfiguracion = new Font("Monotype Corsiva", Font.BOLD, 16);
        Color colorBg = new Color(255, 255, 255, 0);
        Consumer<JTextArea> consumer = label -> {
            label.setOpaque(true);
            label.setBackground(colorBg);
            label.setForeground(Color.WHITE);
            label.setFont(labelsConfiguracion);
            label.setEditable(false);
        };
        consumer.accept(infoJugador);
        infoJugador.setFont(buttonFont);
        panelInfoJugador.add(infoJugador);
        panelInfoJugador.add(salirInfo);
        labelSimbolos = new JTextArea("Cantidad de simbolos\nnecesarios para ganar\nla partida");
        consumer.accept(labelSimbolos);
        labelJugadorMano = new JTextArea("Jugador mano para\nla primera ronda");
        consumer.accept(labelJugadorMano);
        labelCreadorNull = new JTextArea("Determina si la partida\ndebe terminar si el \ncreador la abandona");
        consumer.accept(labelCreadorNull);

        aceptarConfiguracion.addActionListener(actionEvent -> {
            guardarConfiguraciones(
                    simbolosAfecto.getItemAt(simbolosAfecto.getSelectedIndex()),
                    jugadorMano.getItemAt(jugadorMano.getSelectedIndex()),
                    creadorNull.getItemAt(creadorNull.getSelectedIndex()));
            panelSetCondiciones.setVisible(false);
        });
        botonSetCondiciones.addActionListener(actionEvent -> {
            panelSetCondiciones.setVisible(true);
            jugadorMano.removeAllItems();
            for (PlayerDummy jugador : cliente.getSalaActual().jugadores) {
                jugadorMano.addItem(jugador.nombre);
            }
            panelSetCondiciones.requestFocusInWindow();
        });
        botonJugador.addActionListener(actionEvent -> {
            PlayerDummy dummy = cliente.getSalaActual().jugadores.get(0);
            infoJugador.setText("" +
                    "Nombre:  " + dummy.nombre + "\n\n" +
                    "Victorias:  " + dummy.victorias + "\n\n" +
                    "Derrotas:  " + dummy.derrotas);
            panelInfoJugador.setVisible(true);
        });
        botonJugador1.addActionListener(actionEvent -> {
            PlayerDummy dummy = cliente.getSalaActual().jugadores.get(1);
            infoJugador.setText("" +
                    "Nombre:  " + dummy.nombre + "\n\n" +
                    "Victorias:  " + dummy.victorias + "\n\n" +
                    "Derrotas:  " + dummy.derrotas);
            panelInfoJugador.setVisible(true);
        });
        botonJugador2.addActionListener(actionEvent -> {
            PlayerDummy dummy = cliente.getSalaActual().jugadores.get(2);
            infoJugador.setText("" +
                    "Nombre:  " + dummy.nombre + "\n\n" +
                    "Victorias:  " + dummy.victorias + "\n\n" +
                    "Derrotas:  " + dummy.derrotas);
            panelInfoJugador.setVisible(true);
        });
        botonJugador3.addActionListener(actionEvent -> {
            PlayerDummy dummy = cliente.getSalaActual().jugadores.get(3);
            infoJugador.setText("" +
                    "Nombre:  " + dummy.nombre + "\n\n" +
                    "Victorias:  " + dummy.victorias + "\n\n" +
                    "Derrotas:  " + dummy.derrotas);
            panelInfoJugador.setVisible(true);
        });
        botonChat.addActionListener(actionEvent -> {
            loveletter.getVentana().cambiarVisibilidadChat();
        });
        botonSalir.addActionListener(actionEvent -> {
            cliente.salirSala();
            LoveLetter.getInstance().getVentana().onSalirSala();
        });
        botonEmpezarPartida.addActionListener(actionEvent -> {
            loveletter.getCliente().getJugadorCliente().iniciarPartida();
        });
        botonAgregarBot.addActionListener(actionEvent -> {
            loveletter.getCliente().send(ComandoTipo.AgregarBot, new JsonObject());
            if (!botAgregado) {
                botAgregado = true;
                botonAgregarBot.setText("Eliminar Bot");
            } else {
                botAgregado = false;
                botonAgregarBot.setText("Agregar Bot");
            }
        });

        panelSetCondiciones.add(simbolosAfecto);
        panelSetCondiciones.add(jugadorMano);
        panelSetCondiciones.add(creadorNull);
        panelSetCondiciones.add(aceptarConfiguracion);
        panelSetCondiciones.add(labelSimbolos);
        panelSetCondiciones.add(labelJugadorMano);
        panelSetCondiciones.add(labelCreadorNull);
        add(botonSetCondiciones);
        add(botonEmpezarPartida);
        add(panelSetCondiciones);
        add(panelInfoJugador);
        add(botonAgregarBot);
        add(botonJugador);
        add(botonJugador1);
        add(botonJugador2);
        add(botonJugador3);
        add(botonSalir);
        add(botonChat);
        registrar();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(Imagenes.fondo3, null, 0, 0);
        Color color = new Color(0, 0, 0, 150);
        g2.setColor(color);
        g2.fillRect(0, 0, 300, loveletter.HEIGHT);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Monotype Corsiva", Font.PLAIN, 40));
        g2.drawString("" + cliente.getSalaActual().nombre, 25, 100);
        g2.setFont(new Font("Monotype Corsiva", Font.PLAIN, 24));
        g2.drawString("Cantidad de Jugadores: " + cliente.getSalaActual().jugadores.size(), 25, 140);

        if (panelSetCondiciones.isVisible()) {
            g2.setColor(new Color(0, 0, 0, 165));
            g2.fillRect(0, 0, loveletter.WIDTH, loveletter.HEIGHT);
            botonJugador.setEnabled(false);
            botonJugador1.setEnabled(false);
            botonJugador2.setEnabled(false);
            botonJugador3.setEnabled(false);
        } else {
            botonJugador.setEnabled(true);
            botonJugador1.setEnabled(true);
            botonJugador2.setEnabled(true);
            botonJugador3.setEnabled(true);
        }

        g2.drawImage(Imagenes.marco, null, 396, 80);
        botonJugador.setBounds(420, 120, 150, 150);
        g2.drawImage(Imagenes.marco, null, 676, 80);
        botonJugador1.setBounds(700, 120, 150, 150);
        g2.drawImage(Imagenes.marco, null, 396, 290);
        botonJugador2.setBounds(420, 330, 150, 150);
        g2.drawImage(Imagenes.marco, null, 676, 290);
        botonJugador3.setBounds(700, 330, 150, 150);

        botonSetCondiciones.setBounds(50, 200, 200, 50);
        botonEmpezarPartida.setBounds(50, 260, 200, 50);
        botonAgregarBot.setBounds(50, 320, 200, 50);
        botonChat.setBounds(50, 540, 200, 50);
        botonSalir.setBounds(50, 600, 200, 50);

        labelSimbolos.setBounds(30, 31, 150, 55);
        simbolosAfecto.setBounds(200, 30, 150, 55);
        labelJugadorMano.setBounds(30, 140, 150, 55);
        jugadorMano.setBounds(200, 130, 150, 50);
        labelCreadorNull.setBounds(30, 230, 150, 50);
        creadorNull.setBounds(200, 230, 150, 50);
        aceptarConfiguracion.setBounds(100, 330, 200, 50);
        panelSetCondiciones.setBounds(300, 150, 400, 400);
        botonJugador.setVisible(false);
        botonJugador1.setVisible(false);
        botonJugador2.setVisible(false);
        botonJugador3.setVisible(false);

        ArrayList<PlayerDummy> jugs = new ArrayList<>(cliente.getSalaActual().jugadores);
        int index = 0;
        for (PlayerDummy dummy : jugs) {
            switch (index) {
                case 0:
                    botonJugador.setIcon(Imagenes.getIconoPorNombre(dummy.icono));
                    botonJugador.setVisible(true);
                    break;
                case 1:
                    botonJugador1.setIcon(Imagenes.getIconoPorNombre(dummy.icono));
                    botonJugador1.setVisible(true);
                    break;
                case 2:
                    botonJugador2.setIcon(Imagenes.getIconoPorNombre(dummy.icono));
                    botonJugador2.setVisible(true);
                    break;
                case 3:
                    botonJugador3.setIcon(Imagenes.getIconoPorNombre(dummy.icono));
                    botonJugador3.setVisible(true);
            }
            index++;
        }
        if (!cliente.getSalaActual().creador.nombre.equals(cliente.nombre)) {
            botonSetCondiciones.setVisible(false);
            botonEmpezarPartida.setVisible(false);
            botonAgregarBot.setVisible(false);
        } else {
            botonSetCondiciones.setVisible(true);
            botonEmpezarPartida.setVisible(true);
            botonAgregarBot.setVisible(true);
        }

        panelInfoJugador.setBounds(350, 200, 300, 300);
        infoJugador.setBounds(50, 50, 200, 200);
        salirInfo.setBounds(100, 250, 100, 30);
    }

    @Override
    public void render() {
        this.repaint();

        botonEmpezarPartida.setEnabled(cliente.getSalaActual().isConfigurada && cliente.getSalaActual().jugadores.size() > 1);
    }

    private void guardarConfiguraciones(Integer simbolos, String mano, Boolean creadorNull) {
        JsonObject json = new JsonObject();
        json.addProperty("simbolos", simbolos);
        json.addProperty("jugadorMano", mano);
        this.loveletter.getCliente().send(ComandoTipo.ConfigurarSala, json);
    }

}
