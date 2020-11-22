package equipoalpha.loveletter.pantalla;

import com.google.gson.JsonObject;
import equipoalpha.loveletter.client.JugadorCliente;
import equipoalpha.loveletter.client.LoveLetter;
import equipoalpha.loveletter.common.ComandoTipo;
import equipoalpha.loveletter.common.PlayerDummy;
import equipoalpha.loveletter.jugador.Jugador;
import equipoalpha.loveletter.jugador.JugadorIA;
import equipoalpha.loveletter.partida.Sala;
import equipoalpha.loveletter.util.Drawable;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.basic.BasicTableUI;
import java.awt.*;
import java.util.ArrayList;
import java.util.function.Consumer;

public class PanelSala extends JPanel implements Drawable {
    private static final long serialVersionUID = 4L;
    private final LoveLetter loveletter;
    private final JugadorCliente cliente;
    private final JButton botonJugador;
    private final JButton botonSetCondiciones;
    private final JButton botonChat;
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
    private boolean botAgregado = false;
    private final JButton botonJugador1;
    private final JButton botonJugador2;
    private final JButton botonJugador3;
    private final JButton botonSalir;

    public PanelSala() {
        this.loveletter = LoveLetter.getInstance();
        this.cliente = LoveLetter.getInstance().getCliente().getJugadorCliente();

        botonSetCondiciones = new JButton("Configurar");
        botonEmpezarPartida = new JButton("Empezar");
        botonEmpezarPartida.setEnabled(false);
        botonSalir = new JButton("Salir de la Sala");
        aceptarConfiguracion = new JButton("Aceptar");
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
        Font buttonFont = new Font("Arial", Font.BOLD, 22);
        botonSetCondiciones.setFont(buttonFont);
        botonEmpezarPartida.setFont(buttonFont);
        aceptarConfiguracion.setFont(buttonFont);
        botonAgregarBot.setFont(buttonFont);
        botonSalir.setFont(buttonFont);
        botonChat.setFont(buttonFont);
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
        Font labelsConfiguracion = new Font("Arial", Font.BOLD, 13);
        Color colorBg = new Color(255, 255, 255, 0);
        Consumer<JTextArea> consumer = label -> {
            label.setOpaque(true);
            label.setBackground(colorBg);
            label.setForeground(Color.WHITE);
            label.setFont(labelsConfiguracion);
            label.setEditable(false);
        };
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
        botonJugador1.addActionListener(actionEvent -> {
            // datos del jugador
        });
        botonJugador2.addActionListener(actionEvent -> {

        });
        botonJugador3.addActionListener(actionEvent -> {

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
            }
            else {
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

        g2.drawImage(Imagenes.background, null, 0, 0);
        Color color = new Color(0, 0, 0, 185);
        g2.setColor(color);
        g2.fillRect(0, 0, 300, loveletter.HEIGHT);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 40));
        g2.drawString("" + cliente.getSalaActual().nombre, 25, 100);
        g2.setFont(new Font("Arial", Font.PLAIN, 22));
        g2.drawString("Cantidad de Jugadores: " + cliente.getSalaActual().jugadores.size(), 25, 140);

        if (panelSetCondiciones.isVisible()) {
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

        botonJugador.setBounds(420, 120, 150, 150);
        botonJugador1.setBounds(700, 120, 150, 150);
        botonJugador2.setBounds(420, 330, 150, 150);
        botonJugador3.setBounds(700, 330, 150, 150);

        botonSetCondiciones.setBounds(50, 200, 200, 50);
        botonEmpezarPartida.setBounds(50, 260, 200, 50);
        botonAgregarBot.setBounds(50, 320,200,50);
        botonChat.setBounds(50, 540, 200, 50);
        botonSalir.setBounds(50, 600, 200, 50);

        labelSimbolos.setBounds(30, 31, 150, 50);
        simbolosAfecto.setBounds(200, 30, 150, 50);
        labelJugadorMano.setBounds(30, 140, 150, 50);
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
        for(PlayerDummy dummy : jugs) {
            switch(index) {
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
