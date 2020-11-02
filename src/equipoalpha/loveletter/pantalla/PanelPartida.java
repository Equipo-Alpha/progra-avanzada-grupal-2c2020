package equipoalpha.loveletter.pantalla;

import equipoalpha.loveletter.LoveLetter;
import equipoalpha.loveletter.carta.CartaTipo;
import equipoalpha.loveletter.jugador.EstadosJugador;
import equipoalpha.loveletter.jugador.Jugador;
import equipoalpha.loveletter.partida.Sala;
import equipoalpha.loveletter.util.Drawable;
import equipoalpha.loveletter.util.excepcion.JugadorNoValido;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
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

    public PanelPartida(Ventana ventana, Sala sala) {
        this.parent = ventana;
        this.sala = sala;
        this.loveletter = LoveLetter.getInstance();
        this.jugador = loveletter.getJugador();
        botonCarta1 = new JButton();
        botonCarta2 = new JButton();
        botonAbandonar = new JButton("Abandonar Partida");
        botonAbandonar.setFont(new Font("Arial", Font.BOLD, 16));
        add(botonCarta1);
        add(botonCarta2);
        add(botonAbandonar);

        botonCarta1.addActionListener(actionEvent -> {
            jugador.descartarCarta1();
            actualizarJugadores(jugador.getEstado().getCartaDescartada().getTipo());
        });
        botonCarta2.addActionListener(actionEvent -> {
            jugador.descartarCarta2();
            actualizarJugadores(jugador.getEstado().getCartaDescartada().getTipo());
        });
        botonAbandonar.addActionListener(actionEvent -> {
            jugador.salirSala();
            ventana.onSalirSala(this);
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
        registrar();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.jugadoresAdibujar = new ArrayList<>(sala.partida.rondaActual.jugadoresEnLaRonda);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(Imagenes.backgroundPartida, null, 0, 0);

        botonAbandonar.setBounds(800, 670, 200, 40);

        if (jugadoresAdibujar.contains(jugador)) {
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
            g2.drawImage(Imagenes.reversoPeq, null, 380, 200);
            g2.drawImage(Imagenes.reversoPeq, null, 390, 205);
            g2.drawImage(Imagenes.reversoPeq, null, 400, 210);
        }

        // la carta eliminada
        if (sala.partida.rondaActual.cartaEliminada != null) {
            g2.drawImage(Imagenes.reversoPeq, null, 10, 610);
        }

        int i = 0;
        for (Jugador jugador : jugadoresAdibujar) {
            switch (i) {
                case 0:
                    g2.drawImage(Imagenes.reversoPeq, null, 10, 250);
                    if (jugador.carta2 != null) {
                        g2.drawImage(Imagenes.reversoPeq, null, 80, 250);
                    }
                    break;
                case 1:
                    g2.drawImage(Imagenes.reversoPeq, null, 400, 10);
                    if (jugador.carta2 != null) {
                        g2.drawImage(Imagenes.reversoPeq, null, 470, 10);
                    }
                    break;
                case 2:
                    g2.drawImage(Imagenes.reversoPeq, null, 925, 250);
                    if (jugador.carta2 != null) {
                        g2.drawImage(Imagenes.reversoPeq, null, 850, 250);
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
            jugadorElegido.addItem(jugador);
        }
    }
}
