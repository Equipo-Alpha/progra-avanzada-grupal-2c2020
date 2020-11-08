package equipoalpha.loveletter.pantalla;

import equipoalpha.loveletter.LoveLetter;
import equipoalpha.loveletter.jugador.Jugador;
import equipoalpha.loveletter.jugador.JugadorIA;
import equipoalpha.loveletter.partida.Sala;
import equipoalpha.loveletter.util.Drawable;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.util.function.Consumer;

public class PanelSala extends JPanel implements Drawable {
    private static final long serialVersionUID = 4L;
    private final Sala sala;
    private final Ventana parent;
    private final LoveLetter loveletter;
    private final JButton botonJugador;
    private final JButton botonSetCondiciones;
    private final JPanel panelSetCondiciones;
    private final JTextArea labelSimbolos;
    private final JComboBox<Integer> simbolosAfecto = new JComboBox<>();
    private final JTextArea labelJugadorMano;
    private final JComboBox<Jugador> jugadorMano = new JComboBox<>();
    private final JTextArea labelCreadorNull;
    private final JComboBox<Boolean> creadorNull = new JComboBox<>();
    private final JButton aceptarConfiguracion;
    private final JButton botonEmpezarPartida;
    private final JButton botonAgregarBot1;
    private final JButton botonAgregarBot2;
    private final JButton botonAgregarBot3;
    private final JButton botonSalir;
    private final JugadorIA bot1 = new JugadorIA("SrBot1");
    private final JugadorIA bot2 = new JugadorIA("SrBot2");
    private final JugadorIA bot3 = new JugadorIA("SrBot3");
    boolean bot1Agregado = false;
    boolean bot2Agregado = false;
    boolean bot3Agregado = false;

    public PanelSala(Ventana ventana) {
        this.loveletter = LoveLetter.getInstance();
        this.sala = new Sala("test", loveletter.getJugador());
        this.parent = ventana;

        botonSetCondiciones = new JButton("Configurar");
        botonEmpezarPartida = new JButton("Empezar");
        botonEmpezarPartida.setEnabled(false);
        botonSalir = new JButton("Salir de la Sala");
        aceptarConfiguracion = new JButton("Aceptar");
        botonJugador = new JButton();
        botonAgregarBot1 = new JButton();
        botonAgregarBot2 = new JButton();
        botonAgregarBot3 = new JButton();
        Font buttonFont = new Font("Arial", Font.BOLD, 22);
        botonSetCondiciones.setFont(buttonFont);
        botonEmpezarPartida.setFont(buttonFont);
        aceptarConfiguracion.setFont(buttonFont);
        botonJugador.setFont(buttonFont);
        botonAgregarBot1.setFont(buttonFont);
        botonAgregarBot2.setFont(buttonFont);
        botonAgregarBot3.setFont(buttonFont);
        botonSalir.setFont(buttonFont);
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
            checkYGuardarConfiguraciones(
                    simbolosAfecto.getItemAt(simbolosAfecto.getSelectedIndex()),
                    jugadorMano.getItemAt(jugadorMano.getSelectedIndex()),
                    creadorNull.getItemAt(creadorNull.getSelectedIndex()));
            panelSetCondiciones.setVisible(false);
        });
        botonSetCondiciones.addActionListener(actionEvent -> {
            panelSetCondiciones.setVisible(true);
            jugadorMano.removeAllItems();
            for (Jugador jugador : sala.jugadores) {
                jugadorMano.addItem(jugador);
            }
            panelSetCondiciones.requestFocus();
        });
        botonAgregarBot1.addActionListener(actionEvent -> {
            if (bot1Agregado) {
                sala.eliminarJugador(bot1);
                LoveLetter.handler.removeTickableObject(bot1);
                bot1Agregado = false;
            } else {
                sala.agregarJugador(bot1);
                bot1.registrar();
                bot1Agregado = true;
            }
        });
        botonAgregarBot2.addActionListener(actionEvent -> {
            if (bot2Agregado) {
                sala.eliminarJugador(bot2);
                LoveLetter.handler.removeTickableObject(bot2);
                bot2Agregado = false;
            } else {
                sala.agregarJugador(bot2);
                bot2.registrar();
                bot2Agregado = true;
            }
        });
        botonAgregarBot3.addActionListener(actionEvent -> {
            if (bot3Agregado) {
                sala.eliminarJugador(bot3);
                LoveLetter.handler.removeTickableObject(bot3);
                bot3Agregado = false;
            } else {
                sala.agregarJugador(bot3);
                bot3.registrar();
                bot3Agregado = true;
            }
        });
        botonSalir.addActionListener(actionEvent -> {
            loveletter.getJugador().salirSala();
            parent.onSalirSala(this);
        });
        botonEmpezarPartida.addActionListener(actionEvent -> {
            //TODO ver como implementar
            //loveletter.getJugador().iniciarPartida();
            sala.crearPartida();
            ventana.onPartidaEmpezada(this);
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
        add(botonJugador);
        add(botonAgregarBot1);
        add(botonAgregarBot2);
        add(botonAgregarBot3);
        add(botonSalir);
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

        if (panelSetCondiciones.isVisible()) {
            botonJugador.setEnabled(false);
            botonAgregarBot2.setEnabled(false);
        } else {
            botonJugador.setEnabled(true);
            botonAgregarBot2.setEnabled(true);
        }

        botonJugador.setIcon(loveletter.getJugador().icono);
        botonJugador.setBounds(420, 120, 150, 150);

        botonAgregarBot1.setIcon(Imagenes.iconoSuma);
        botonAgregarBot1.setOpaque(true);
        botonAgregarBot1.setBackground(new Color(255, 255, 255, 0));
        botonAgregarBot1.setBounds(700, 120, 150, 150);

        botonAgregarBot2.setIcon(Imagenes.iconoSuma);
        botonAgregarBot2.setOpaque(true);
        botonAgregarBot2.setBackground(new Color(255, 255, 255, 0));
        botonAgregarBot2.setBounds(420, 330, 150, 150);

        botonAgregarBot3.setIcon(Imagenes.iconoSuma);
        botonAgregarBot3.setOpaque(true);
        botonAgregarBot3.setBackground(new Color(255, 255, 255, 0));
        botonAgregarBot3.setBounds(700, 330, 150, 150);


        if (bot1Agregado) {
            botonAgregarBot1.setIcon(Imagenes.iconoBot);
        }
        if (bot2Agregado) {
            botonAgregarBot2.setIcon(Imagenes.iconoBot);
        }
        if (bot3Agregado) {
            botonAgregarBot3.setIcon(Imagenes.iconoBot);
        }

        botonSetCondiciones.setBounds(50, 200, 200, 50);
        botonEmpezarPartida.setBounds(50, 260, 200, 50);
        botonSalir.setBounds(50, 320, 200, 50);

        labelSimbolos.setBounds(30, 31, 150, 50);
        simbolosAfecto.setBounds(200, 30, 150, 50);
        labelJugadorMano.setBounds(30, 140, 150, 50);
        jugadorMano.setBounds(200, 130, 150, 50);
        labelCreadorNull.setBounds(30, 230, 150, 50);
        creadorNull.setBounds(200, 230, 150, 50);
        aceptarConfiguracion.setBounds(100, 330, 200, 50);
        panelSetCondiciones.setBounds(300, 150, 400, 400);
    }

    @Override
    public void render() {
        this.repaint();

        botonEmpezarPartida.setEnabled(sala.isConfigurada() && sala.jugadores.size() > 1);
    }

    private void checkYGuardarConfiguraciones(Integer simbolos, Jugador mano, Boolean creadorNull) {
        // TODO realmente hacer un check y que el nombre no sea solo para asustar
        sala.setCantSimbolosAfecto(simbolos);
        sala.setJugadorMano(mano);
        sala.setCreadorNull(creadorNull);
    }

    public Sala getSala() {
        return sala;
    }

}
