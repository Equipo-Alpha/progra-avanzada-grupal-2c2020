package equipoalpha.loveletter.pantalla;

import equipoalpha.loveletter.LoveLetter;
import equipoalpha.loveletter.carta.Carta;
import equipoalpha.loveletter.carta.CartaTipo;
import equipoalpha.loveletter.jugador.EstadosJugador;
import equipoalpha.loveletter.jugador.Jugador;
import equipoalpha.loveletter.partida.Sala;
import equipoalpha.loveletter.util.Drawable;
import equipoalpha.loveletter.util.excepcion.JugadorNoValido;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImageOp;
import java.util.ArrayList;

public class PanelPartida extends JPanel implements Drawable {
    private static final long serialVersionUID = 5L;
    private final Sala sala;
    private final Ventana parent;
    private final LoveLetter loveletter;
    private final Jugador jugador;
    private ArrayList<Jugador> jugadoresAdibujar;
    private JButton botonCarta1;
    private JButton botonCarta2;
    private JButton botonAbandonar;
    private JComboBox<Jugador> jugadorElegido = new JComboBox<>();
    private JButton botonConfirmarJugador = new JButton("Confirmar");
    private JPanel panelElegirJugador;
    private JComboBox<CartaTipo> cartaAdivinada = new JComboBox<>();
    private JButton botonConfirmarCarta = new JButton("Confirmar");
    private JPanel panelAdivinarCarta;
    private boolean mostrarPanelJugador = true, mostrarPanelCarta = true;

    private JButton botonIconoJugador;
    private JTextArea datosJugador = new JTextArea();
    private boolean viendoDatosJugador = false;
    private JButton botonIconoJ1;
    private JTextArea datosJ1 = new JTextArea();
    private boolean viendoDatosJ1 = false;
    private JButton botonIconoJ2;
    private JTextArea datosJ2 = new JTextArea();
    private boolean viendoDatosJ2 = false;
    private JButton botonIconoJ3;
    private JTextArea datosJ3 = new JTextArea();
    private boolean viendoDatosJ3 = false;

    public PanelPartida(Ventana ventana, Sala sala) {
        this.parent = ventana;
        this.sala = sala;
        this.loveletter = LoveLetter.getInstance();
        this.jugador = loveletter.getJugador();
        botonCarta1 = new JButton();
        botonCarta2 = new JButton();
        botonAbandonar = new JButton("Abandonar Partida");
        Font font = new Font("Arial", Font.BOLD, 16);
        botonAbandonar.setFont(font);
        datosJugador.setFont(font);
        datosJugador.setVisible(false);
        datosJ1.setFont(font);
        datosJ1.setVisible(false);
        datosJ2.setFont(font);
        datosJ2.setVisible(false);
        datosJ3.setFont(font);
        datosJ3.setVisible(false);
        botonIconoJugador = new JButton();
        botonIconoJ1 = new JButton();
        botonIconoJ2 = new JButton();
        botonIconoJ3 = new JButton();
        add(botonCarta1);
        add(botonCarta2);
        add(botonAbandonar);
        add(botonIconoJugador);
        add(botonIconoJ1);
        add(botonIconoJ2);
        add(botonIconoJ3);

        botonCarta1.addActionListener(actionEvent -> {
            jugador.descartarCarta1();
            if(jugador.getEstado().getEstadoActual() == EstadosJugador.ELIGIENDOJUGADOR)
                actualizarJugadores(jugador.getEstado().getCartaDescartada().getTipo());
        });
        botonCarta2.addActionListener(actionEvent -> {
            jugador.descartarCarta2();
            if(jugador.getEstado().getEstadoActual() == EstadosJugador.ELIGIENDOJUGADOR)
                actualizarJugadores(jugador.getEstado().getCartaDescartada().getTipo());
        });
        botonAbandonar.addActionListener(actionEvent -> {
            jugador.salirSala();
            parent.onSalirSala(this);
        });
        botonIconoJugador.addActionListener(actionEvent -> {
            if (viendoDatosJugador) {
                datosJugador.setVisible(false);
                viendoDatosJugador = false;
            } else {
                datosJugador.setVisible(true);
                viendoDatosJugador = true;
            }
        });
        botonIconoJ1.addActionListener(actionEvent -> {
            if (viendoDatosJ1) {
                datosJ1.setVisible(false);
                viendoDatosJ1 = false;
            } else {
                datosJ1.setVisible(true);
                viendoDatosJ1 = true;
            }
        });
        botonIconoJ2.addActionListener(actionEvent -> {
            if (viendoDatosJ2) {
                datosJ2.setVisible(false);
                viendoDatosJ2 = false;
            } else {
                datosJ2.setVisible(true);
                viendoDatosJ2 = true;
            }
        });
        botonIconoJ3.addActionListener(actionEvent -> {
            if (viendoDatosJ3) {
                datosJ3.setVisible(false);
                viendoDatosJ3 = false;
            } else {
                datosJ3.setVisible(true);
                viendoDatosJ3 = true;
            }
        });

        panelElegirJugador = new JPanel();
        panelElegirJugador.setVisible(false);
        panelElegirJugador.setBackground(new Color(0, 0, 0, 255));
        panelElegirJugador.setBorder(new BorderUIResource.LineBorderUIResource(new Color(255, 255, 255, 255)));
        panelElegirJugador.add(jugadorElegido);
        panelElegirJugador.add(botonConfirmarJugador);
        botonConfirmarJugador.addActionListener(actionEvent -> {
            try {
                jugador.elegirJugador(jugadorElegido.getItemAt(jugadorElegido.getSelectedIndex()));
            } catch (JugadorNoValido jugadorNoValido) {
                jugadorNoValido.printStackTrace();
                jugador.salirSala();
            }
        });

        panelAdivinarCarta = new JPanel();
        panelAdivinarCarta.setVisible(false);
        panelAdivinarCarta.setBackground(new Color(0, 0, 0, 255));
        panelAdivinarCarta.setBorder(new BorderUIResource.LineBorderUIResource(new Color(255, 255, 255, 255)));
        for (CartaTipo tipo : CartaTipo.values()) {
            if (tipo != CartaTipo.GUARDIA) {
                cartaAdivinada.addItem(tipo);
            }
        }
        panelAdivinarCarta.add(cartaAdivinada);
        panelAdivinarCarta.add(botonConfirmarCarta);
        botonConfirmarCarta.addActionListener(actionEvent -> {
            jugador.elegirCarta(cartaAdivinada.getItemAt(cartaAdivinada.getSelectedIndex()));
        });
        add(panelElegirJugador);
        add(panelAdivinarCarta);

        add(datosJugador);
        add(datosJ1);
        add(datosJ2);
        add(datosJ3);

        registrar();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.jugadoresAdibujar = new ArrayList<>(sala.partida.jugadores);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(Imagenes.backgroundPartida, null, 0, 0);

        botonAbandonar.setBounds(800, 670, 200, 40);

        botonIconoJugador.setIcon(jugador.icono);
        botonIconoJugador.setBounds(700, 500, 150, 150);
        datosJugador.setText("Nombre: " + jugador + "\n\nSimbolos: " + jugador.cantSimbolosAfecto);
        datosJugador.setBounds(720, 435, 250,60);
        datosJugador.setOpaque(true);
        datosJugador.setBackground(new Color(255, 255, 255, 0));
        botonIconoJ1.setVisible(false);
        botonIconoJ2.setVisible(false);
        botonIconoJ3.setVisible(false);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 24));
        g2.drawString("RONDA NUMERO: " + sala.partida.ronda, 750, 25);

        ArrayList<Carta> ALC;
        if (sala.partida.rondaActual.jugadoresEnLaRonda.contains(jugador)) {
            ALC = sala.partida.rondaActual.mapaCartasDescartadas.get(jugador);

            int y = 0;
            for (Carta carta : ALC) {
                g2.drawImage(carta.getImagenP(), null, 315 + 30*y, 400);
                y++;
            }

            botonCarta1.setIcon(new ImageIcon(jugador.carta1.getImagen()));
            botonCarta1.setVisible(true);
            botonCarta1.setBounds(380, 500, 150, 210);
            if (jugador.carta2 != null) {
                botonCarta2.setIcon(new ImageIcon(jugador.carta2.getImagen()));
                botonCarta2.setBounds(530, 500, 150, 210);
                botonCarta2.setVisible(true);
            } else botonCarta2.setVisible(false);

            if (jugador.getEstado().getEstadoActual() == EstadosJugador.DESCARTANDO) {
                botonCarta1.setEnabled(true);
                botonCarta2.setEnabled(true);
            } else {
                botonCarta1.setEnabled(false);
                botonCarta2.setEnabled(false);
            }

            if (jugador.getEstado().getEstadoActual() == EstadosJugador.DESCARTANDOCONDESA) {
                botonCarta2.setEnabled(true);
            }

            if (jugador.getEstado().getEstadoActual() == EstadosJugador.ELIGIENDOJUGADOR) {
                if(mostrarPanelJugador) {
                    panelElegirJugador.setVisible(true);
                    panelElegirJugador.requestFocus();
                    mostrarPanelJugador = false;
                }
            } else {
                panelElegirJugador.setVisible(false);
                mostrarPanelJugador = true;
            }

            if (jugador.getEstado().getEstadoActual() == EstadosJugador.ADIVINANDOCARTA) {
                if(mostrarPanelCarta) {
                    panelAdivinarCarta.setVisible(true);
                    panelAdivinarCarta.requestFocus();
                    mostrarPanelCarta = false;
                }
            } else {
                panelAdivinarCarta.setVisible(false);
                mostrarPanelCarta = true;
            }

        } else {
            botonCarta1.setVisible(false);
            botonCarta2.setVisible(false);
        }

        this.jugadoresAdibujar.remove(jugador); // ya me dibuje

        // mazo
        if (!sala.partida.rondaActual.mazoVacio()) {
            //TODO buscar imagen de algun mazo tal vez?
            for (int i = 0; i < sala.partida.rondaActual.cantCartas(); i++) {
                g2.drawImage(Imagenes.reversoPeq, null, 420 + i * 4, 230 + i * 4);
            }
        }

        // la carta eliminada
        if (sala.partida.rondaActual.cartaEliminada != null) {
            g2.drawImage(Imagenes.reversoPeq, null, 10, 610);
        }

        int i = 0;
        for (Jugador jugador : jugadoresAdibujar) {
            int y;
            switch (i) {
                case 0:
                    botonIconoJ1.setVisible(true);
                    botonIconoJ1.setIcon(jugador.icono);
                    botonIconoJ1.setOpaque(true);
                    botonIconoJ1.setBackground(new Color(255, 255, 255, 0));
                    botonIconoJ1.setBounds(10, 140, 100, 100);
                    datosJ1.setText("Nombre: " + jugador + "\n\nSimbolos: " + jugador.cantSimbolosAfecto);
                    datosJ1.setBounds(10, 75, 250,60);
                    datosJ1.setOpaque(true);
                    datosJ1.setBackground(new Color(255, 255, 255, 0));
                    if (sala.partida.rondaActual.jugadoresEnLaRonda.contains(jugador)) {
                        g2.drawImage(Imagenes.reversoPeq, null, 10, 250);
                        if (jugador.carta2 != null) {
                            g2.drawImage(Imagenes.reversoPeq, null, 80, 250);
                        }
                    }
                    ALC = sala.partida.rondaActual.mapaCartasDescartadas.get(jugador);
                    y = 0;
                    for (Carta carta : ALC) {
                        g2.drawImage(carta.getImagenP(), null,10 + 30 * y, 380);
                        y++;
                    }
                    break;
                case 1:
                    botonIconoJ2.setVisible(true);
                    botonIconoJ2.setIcon(jugador.icono);
                    botonIconoJ2.setOpaque(true);
                    botonIconoJ2.setBackground(new Color(255, 255, 255, 0));
                    botonIconoJ2.setBounds(290, 10, 100, 100);
                    datosJ2.setText("Nombre: " + jugador + "\n\nSimbolos: " + jugador.cantSimbolosAfecto);
                    datosJ2.setBounds(250, 115, 250,60);
                    datosJ2.setOpaque(true);
                    datosJ2.setBackground(new Color(255, 255, 255, 0));
                    if (sala.partida.rondaActual.jugadoresEnLaRonda.contains(jugador)) {
                        g2.drawImage(Imagenes.reversoPeq, null, 400, 10);
                        if (jugador.carta2 != null) {
                            g2.drawImage(Imagenes.reversoPeq, null, 470, 10);
                        }
                    }
                    ALC = sala.partida.rondaActual.mapaCartasDescartadas.get(jugador);
                    y = 0;
                    for (Carta carta : ALC) {
                        g2.drawImage(carta.getImagenP(), null, 380 + 30*y, 130);
                        y++;
                    }
                    break;
                case 2:
                    botonIconoJ3.setVisible(true);
                    botonIconoJ3.setIcon(jugador.icono);
                    botonIconoJ3.setOpaque(true);
                    botonIconoJ3.setBackground(new Color(255, 255, 255, 0));
                    botonIconoJ3.setBounds(900, 140, 100, 100);
                    datosJ3.setText("Nombre: " + jugador + "\n\nSimbolos: " + jugador.cantSimbolosAfecto);
                    datosJ3.setBounds(870, 75, 250,60);
                    datosJ3.setOpaque(true);
                    datosJ3.setBackground(new Color(255, 255, 255, 0));
                    if (sala.partida.rondaActual.jugadoresEnLaRonda.contains(jugador)) {
                        g2.drawImage(Imagenes.reversoPeq, null, 925, 250);
                        if (jugador.carta2 != null) {
                            g2.drawImage(Imagenes.reversoPeq, null, 850, 250);
                        }
                    }
                    ALC = sala.partida.rondaActual.mapaCartasDescartadas.get(jugador);
                    y = 0;
                    for (Carta carta : ALC) {
                        g2.drawImage(carta.getImagenP(), null, 935 - 30*y, 380);
                        y++;
                    }
                    break;
            }
            i++;
        }

        jugadorElegido.setBounds(100, 150, 200, 50);
        botonConfirmarJugador.setBounds(100, 250, 200, 50);
        panelElegirJugador.setBounds(300, 150, 400, 400);
        cartaAdivinada.setBounds(100, 150, 200, 50);
        botonConfirmarCarta.setBounds(100, 250, 200, 50);
        panelAdivinarCarta.setBounds(300, 150, 400, 400);
    }

    @Override
    public void render() {
        this.repaint();
    }

    public Sala getSala() {
        return sala;
    }

    private void actualizarJugadores(CartaTipo tipo) {
        jugadorElegido.removeAllItems();
        for (Jugador jugador : sala.partida.rondaActual.jugadoresEnLaRonda) {
            if (jugador.equals(this.jugador) && tipo != CartaTipo.PRINCIPE)
                continue; // no me agrego si no descarte el principe
            if (jugador.estaProtegido) continue; // si esta protegido no lo agrego
            jugadorElegido.addItem(jugador);
        }
    }
}
