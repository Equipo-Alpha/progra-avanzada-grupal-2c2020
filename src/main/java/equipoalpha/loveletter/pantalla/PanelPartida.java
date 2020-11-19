package equipoalpha.loveletter.pantalla;

import equipoalpha.loveletter.client.JugadorCliente;
import equipoalpha.loveletter.client.LoveLetter;
import equipoalpha.loveletter.carta.Carta;
import equipoalpha.loveletter.carta.CartaTipo;
import equipoalpha.loveletter.jugador.EstadosJugador;
import equipoalpha.loveletter.jugador.Jugador;
import equipoalpha.loveletter.partida.Sala;
import equipoalpha.loveletter.util.Drawable;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class PanelPartida extends JPanel implements Drawable {
    private static final long serialVersionUID = 5L;
    //private final Sala sala;
    private final Ventana parent;
    private final LoveLetter loveletter;
    private final JugadorCliente jugador;
    private ArrayList<Jugador> jugadoresAdibujar;
    private AnimacionInicioRonda AIR;
    private final JButton botonCarta1;
    private final JButton botonCarta2;
    private final JButton botonAbandonar;
    private final JComboBox<Jugador> jugadorElegido = new JComboBox<>();
    private final JButton botonConfirmarJugador = new JButton("Confirmar");
    private final JPanel panelElegirJugador;
    private final JComboBox<CartaTipo> cartaAdivinada = new JComboBox<>();
    private final JButton botonConfirmarCarta = new JButton("Confirmar");
    private final JPanel panelAdivinarCarta;
    private final JButton botonCartaViendo = new JButton();
    private final JButton botonTerminarDeVer = new JButton("Terminar de ver");
    private final JPanel panelViendoCarta;
    private boolean mostrarPanelJugador = true, mostrarPanelCarta = true, mostrarPanelViendo;

    private final JButton botonIconoJugador;
    private final JTextArea datosJugador = new JTextArea();
    private boolean viendoDatosJugador = false;
    private final JButton botonIconoJ1;
    private final JTextArea datosJ1 = new JTextArea();
    private boolean viendoDatosJ1 = false;
    private final JButton botonIconoJ2;
    private final JTextArea datosJ2 = new JTextArea();
    private boolean viendoDatosJ2 = false;
    private final JButton botonIconoJ3;
    private final JTextArea datosJ3 = new JTextArea();
    private boolean viendoDatosJ3 = false;

    private boolean seleccionando = true;
    private boolean animandoJ = true, animacionIsFinihedJ = false, animacionStartedJ = false;
    private boolean animandoJ1 = true, animacionIsFinihedJ1 = false, animacionStartedJ1 = false;
    private boolean animandoJ2 = true, animacionIsFinihedJ2 = false, animacionStartedJ2 = false;
    private boolean animandoJ3 = true, animacionIsFinihedJ3 = false, animacionStartedJ3 = false;
    int xIni, yIni;

    public PanelPartida(Ventana ventana) {
        this.parent = ventana;
        //this.sala = sala;
        this.loveletter = LoveLetter.getInstance();
        this.jugador = loveletter.getCliente().getJugadorCliente();
        botonCarta1 = new JButton();
        botonCarta2 = new JButton();
        botonAbandonar = new JButton("Abandonar Partida");
        Font font = new Font("Arial", Font.BOLD, 16);
        botonAbandonar.setFont(font);
        datosJugador.setFont(font);
        datosJugador.setForeground(Color.WHITE);
        datosJugador.setVisible(false);
        datosJ1.setFont(font);
        datosJ1.setForeground(Color.WHITE);
        datosJ1.setOpaque(true);
        datosJ1.setBackground(new Color(255, 255, 255, 0));
        datosJ1.setVisible(false);
        datosJ2.setFont(font);
        datosJ2.setForeground(Color.WHITE);
        datosJ2.setOpaque(true);
        datosJ2.setBackground(new Color(255, 255, 255, 0));
        datosJ2.setVisible(false);
        datosJ3.setFont(font);
        datosJ3.setForeground(Color.WHITE);
        datosJ3.setOpaque(true);
        datosJ3.setBackground(new Color(255, 255, 255, 0));
        datosJ3.setVisible(false);
        botonIconoJugador = new JButton();
        botonIconoJ1 = new JButton();
        botonIconoJ1.setOpaque(true);
        botonIconoJ1.setBackground(new Color(255, 255, 255, 0));
        botonIconoJ2 = new JButton();
        botonIconoJ2.setOpaque(true);
        botonIconoJ2.setBackground(new Color(255, 255, 255, 0));
        botonIconoJ3 = new JButton();
        botonIconoJ3.setOpaque(true);
        botonIconoJ3.setBackground(new Color(255, 255, 255, 0));
        add(botonCarta1);
        add(botonCarta2);
        add(botonAbandonar);
        add(botonIconoJugador);
        add(botonIconoJ1);
        add(botonIconoJ2);
        add(botonIconoJ3);
//        AIR = new AnimacionInicioRonda(sala);

        botonCarta1.addActionListener(actionEvent -> {
            jugador.descartarCarta1();
//            if (jugador.getEstado() == EstadosJugador.ELIGIENDOJUGADOR)
//                actualizarJugadores(jugador.getEstado().getCartaDescartada().getTipo());
        });
        botonCarta2.addActionListener(actionEvent -> {
            jugador.descartarCarta2();
//            if (jugador.getEstado() == EstadosJugador.ELIGIENDOJUGADOR)
//                actualizarJugadores(jugador.getEstado().getCartaDescartada().getTipo());
        });
        botonAbandonar.addActionListener(actionEvent -> {
            jugador.salirSala();
            parent.onSalirSala();
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
//            jugador.elegirJugador(jugadorElegido.getItemAt(jugadorElegido.getSelectedIndex()));
            panelElegirJugador.setVisible(false);
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
            panelAdivinarCarta.setVisible(false);
        });

        panelViendoCarta = new JPanel();
        panelViendoCarta.setVisible(false);
        panelViendoCarta.setBackground(new Color(0, 0, 0, 255));
        panelViendoCarta.setBorder(new BorderUIResource.LineBorderUIResource(new Color(255, 255, 255, 255)));
        panelViendoCarta.add(botonTerminarDeVer);
        panelViendoCarta.add(botonCartaViendo);
        botonTerminarDeVer.addActionListener(actionEvent -> {
            panelViendoCarta.setVisible(false);
            jugador.terminarDeVer();
        });
        add(panelElegirJugador);
        add(panelAdivinarCarta);
        add(panelViendoCarta);

        add(datosJugador);
        add(datosJ1);
        add(datosJ2);
        add(datosJ3);

        registrar();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
//        this.jugadoresAdibujar = new ArrayList<>(sala.partida.jugadores);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(Imagenes.backgroundPartida, null, 0, 0);

        botonAbandonar.setBounds(800, 670, 200, 40);

        botonIconoJugador.setIcon(jugador.icono);
        botonIconoJugador.setBounds(700, 500, 150, 150);
        //datosJugador.setText("Nombre: " + jugador + "\nSimbolos: " + jugador.cantSimbolosAfecto);
        datosJugador.setBounds(720, 455, 250, 60);
        datosJugador.setOpaque(true);
        datosJugador.setBackground(new Color(255, 255, 255, 0));
        botonIconoJ1.setVisible(false);
        botonIconoJ2.setVisible(false);
        botonIconoJ3.setVisible(false);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 24));
//        g2.drawString("RONDA NUMERO: " + sala.partida.ronda, 750, 25);

//        if (!sala.partida.rondaActual.turnosIniciados) {
//            botonCarta1.setVisible(false);
//            botonCarta2.setVisible(false);
//            AIR.animar(g2);
//            return;
//        }

        ArrayList<Carta> ALC;
        AffineTransform t = new AffineTransform();
//        ALC = sala.partida.rondaActual.mapaCartasDescartadas.get(jugador);
        t.translate(340, 400);
        t.scale(0.41, 0.41);
//        for (Carta carta : ALC) {
//            g2.drawImage(carta.getImagen(), t, null);
//            t.translate(60, 0);
//        }

//        if (sala.partida.rondaActual.jugadoresEnLaRonda.contains(jugador)) {
//            botonCarta1.setIcon(new ImageIcon(jugador.carta1.getImagen()));
//            botonCarta1.setVisible(true);
//            botonCarta1.setBounds(380, 500, 150, 210);
//            if (jugador.carta2 != null) {
//                if (animandoJ) {
//                    animacionStartedJ = true;
//                    animacionIsFinihedJ = false;
//                    animandoJ = false;
//                    botonCarta2.setVisible(false);
//                    botonCarta1.setEnabled(false);
//                    xIni = 460; yIni = 250;
//                }
//                if (animacionStartedJ) {
//                    g2.drawImage(Imagenes.reversoPeq, null, xIni, yIni+=2);
//                    if (yIni >= 510) animacionIsFinihedJ = true;
//                }
//                if (animacionIsFinihedJ) {
//                    animacionStartedJ = false;
//                    botonCarta2.setIcon(new ImageIcon(jugador.carta2.getImagen()));
//                    botonCarta2.setBounds(530, 500, 150, 210);
//                    botonCarta2.setVisible(true);
//                    botonCarta1.setEnabled(true);
//                }
//            } else {
//                botonCarta2.setVisible(false);
//                animandoJ = true;
//            }
//
//            if (jugador.getEstado() == EstadosJugador.DESCARTANDO) {
//                //botonCarta1.setEnabled(true);
//                botonCarta2.setEnabled(true);
//            } else if (jugador.getEstado() == EstadosJugador.DESCARTANDOCONDESA) {
//                if (jugador.carta1.getTipo() == CartaTipo.CONDESA) {
//                    botonCarta1.setEnabled(true);
//                    botonCarta2.setEnabled(false);
//                } else {
//                    botonCarta1.setEnabled(false);
//                    botonCarta2.setEnabled(true);
//                }
//            } else {
//                botonCarta1.setEnabled(false);
//                botonCarta2.setEnabled(false);
//            }
//
//            if (jugador.getEstado() == EstadosJugador.ELIGIENDOJUGADOR) {
//                if (mostrarPanelJugador) {
//                    panelElegirJugador.setVisible(true);
//                    panelElegirJugador.requestFocus();
//                    mostrarPanelJugador = false;
//                }
//            } else {
//                panelElegirJugador.setVisible(false);
//                mostrarPanelJugador = true;
//            }
//
//            if (jugador.getEstado() == EstadosJugador.ADIVINANDOCARTA) {
//                if (mostrarPanelCarta) {
//                    panelAdivinarCarta.setVisible(true);
//                    panelAdivinarCarta.requestFocus();
//                    mostrarPanelCarta = false;
//                }
//            } else {
//                panelAdivinarCarta.setVisible(false);
//                mostrarPanelCarta = true;
//            }
//
//            if (jugador.getEstado() == EstadosJugador.VIENDOCARTA) {
//                if (mostrarPanelViendo) {
//                    panelViendoCarta.setVisible(true);
//                    panelViendoCarta.requestFocus();
//                    //botonCartaViendo.setIcon(new ImageIcon(jugador.getEstado().getCartaViendo().getImagen()));
//                    mostrarPanelViendo = false;
//                }
//            } else {
//                panelViendoCarta.setVisible(false);
//                mostrarPanelViendo = true;
//            }
//
//        } else {
//            botonCarta1.setVisible(false);
//            botonCarta2.setVisible(false);
//            panelAdivinarCarta.setVisible(false);
//            panelElegirJugador.setVisible(false);
//            panelViendoCarta.setVisible(false);
//        }

        this.jugadoresAdibujar.remove(jugador); // ya me dibuje

        // mazo
//        if (!sala.partida.rondaActual.mazoVacio()) {
//            for (int i = 0; i < sala.partida.rondaActual.cantCartas(); i++) {
//                g2.drawImage(Imagenes.reversoPeq, null, 420 + i * 4, 230 + i * 4);
//            }
//        }

        // la carta eliminada
//        if (sala.partida.rondaActual.cartaEliminada != null) {
//            g2.drawImage(Imagenes.reversoPeq, null, 10, 610);
//        }

        int i = 0;
        for (Jugador jugador : jugadoresAdibujar) {
            switch (i) {
                case 0:
                    botonIconoJ1.setVisible(true);
                    //botonIconoJ1.setIcon(jugador.icono);
                    botonIconoJ1.setBounds(10, 140, 100, 100);
                    //datosJ1.setText("Nombre: " + jugador + "\nSimbolos: " + jugador.cantSimbolosAfecto);
                    datosJ1.setBounds(10, 95, 250, 60);
//                    if (sala.partida.rondaActual.jugadoresEnLaRonda.contains(jugador)) {
//                        g2.drawImage(Imagenes.reversoPeq, null, 10, 250);
//                        if (jugador.carta2 != null) {
//                            if (animandoJ1) {
//                                animacionStartedJ1 = true;
//                                animacionIsFinihedJ1 = false;
//                                animandoJ1 = false;
//                                xIni = 430; yIni = 250;
//                            }
//                            if (animacionStartedJ1) {
//                                g2.drawImage(Imagenes.reversoPeq, null, xIni-=2, yIni);
//                                if (xIni <= 80) animacionIsFinihedJ1 = true;
//                            }
//                            if (animacionIsFinihedJ1) {
//                                animacionStartedJ1 = false;
//                                g2.drawImage(Imagenes.reversoPeq, null, 80, 250);
//                            }
//                        } else animandoJ1 = true;
//                    }
//                    ALC = sala.partida.rondaActual.mapaCartasDescartadas.get(jugador);
//                    t = new AffineTransform();
//                    t.translate(10, 380);
//                    t.scale(0.41, 0.41);
//                    for (Carta carta : ALC) {
//                        g2.drawImage(carta.getImagen(), t, null);
//                        t.translate(60, 0);
//                    }
                    break;
                case 1:
                    botonIconoJ2.setVisible(true);
                    //botonIconoJ2.setIcon(jugador.icono);
                    botonIconoJ2.setBounds(290, 10, 100, 100);
                    //datosJ2.setText("Nombre: " + jugador + "\nSimbolos: " + jugador.cantSimbolosAfecto);
                    datosJ2.setBounds(250, 115, 250, 60);
//                    if (sala.partida.rondaActual.jugadoresEnLaRonda.contains(jugador)) {
//                        g2.drawImage(Imagenes.reversoPeq, null, 400, 10);
//                        if (jugador.carta2 != null) {
//                            if (animandoJ2) {
//                                animacionStartedJ2 = true;
//                                animacionIsFinihedJ2 = false;
//                                animandoJ2 = false;
//                                xIni = 450; yIni = 250;
//                            }
//                            if (animacionStartedJ2) {
//                                g2.drawImage(Imagenes.reversoPeq, null, xIni, yIni-=2);
//                                if (yIni <= 10) animacionIsFinihedJ2 = true;
//                            }
//                            if (animacionIsFinihedJ2) {
//                                animacionStartedJ2 = false;
//                                g2.drawImage(Imagenes.reversoPeq, null, 470, 10);
//                            }
//                        } else animandoJ2 = true;
//                    }
//                    ALC = sala.partida.rondaActual.mapaCartasDescartadas.get(jugador);
//                    t = new AffineTransform();
//                    t.translate(380, 130);
//                    t.scale(0.41, 0.41);
//                    for (Carta carta : ALC) {
//                        g2.drawImage(carta.getImagen(), t, null);
//                        t.translate(60, 0);
//                    }
                    break;
                case 2:
                    botonIconoJ3.setVisible(true);
                    //botonIconoJ3.setIcon(jugador.icono);
                    botonIconoJ3.setBounds(900, 140, 100, 100);
                    //datosJ3.setText("Nombre: " + jugador + "\nSimbolos: " + jugador.cantSimbolosAfecto);
                    datosJ3.setBounds(870, 95, 250, 60);
//                    if (sala.partida.rondaActual.jugadoresEnLaRonda.contains(jugador)) {
//                        g2.drawImage(Imagenes.reversoPeq, null, 925, 250);
//                        if (jugador.carta2 != null) {
//                            if (animandoJ3) {
//                                animacionStartedJ3 = true;
//                                animacionIsFinihedJ3 = false;
//                                animandoJ3 = false;
//                                xIni = 430; yIni = 250;
//                            }
//                            if (animacionStartedJ3) {
//                                g2.drawImage(Imagenes.reversoPeq, null, xIni+=2, yIni);
//                                if (xIni >= 850) animacionIsFinihedJ3 = true;
//                            }
//                            if (animacionIsFinihedJ3) {
//                                animacionStartedJ3 = false;
//                                g2.drawImage(Imagenes.reversoPeq, null, 850, 250);
//                            }
//                        } else animandoJ3 = true;
//                    }
//                    ALC = sala.partida.rondaActual.mapaCartasDescartadas.get(jugador);
//                    t = new AffineTransform();
//                    t.translate(935, 380);
//                    t.scale(0.41, 0.41);
//                    for (Carta carta : ALC) {
//                        g2.drawImage(carta.getImagen(), t, null);
//                        t.translate(-60, 0);
//                    }
                    break;
            }
            i++;
        }

        jugadorElegido.setBounds(100, 150, 200, 50);
        botonConfirmarJugador.setBounds(100, 250, 200, 50);
        panelElegirJugador.setBounds(300, 110, 400, 380);
        cartaAdivinada.setBounds(100, 150, 200, 50);
        botonConfirmarCarta.setBounds(100, 250, 200, 50);
        panelAdivinarCarta.setBounds(300, 110, 400, 380);
        botonCartaViendo.setBounds(125, 40, 150, 210);
        botonTerminarDeVer.setBounds(100, 270, 200, 50);
        panelViendoCarta.setBounds(300, 110, 400, 380);
    }

    @Override
    public void render() {
        this.repaint();
    }

    private void actualizarJugadores(CartaTipo tipo) {
        jugadorElegido.removeAllItems();
//        for (Jugador jugador : sala.partida.rondaActual.jugadoresEnLaRonda) {
//            if (jugador.equals(this.jugador) && tipo != CartaTipo.PRINCIPE)
//                continue; // no me agrego si no descarte el principe
//            //if (jugador.estaProtegido) continue; // si esta protegido no lo agrego
//            jugadorElegido.addItem(jugador);
//        }
    }
}
