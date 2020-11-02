package equipoalpha.loveletter.pantalla;

import equipoalpha.loveletter.LoveLetter;
import equipoalpha.loveletter.carta.CartaTipo;
import equipoalpha.loveletter.jugador.EstadosJugador;
import equipoalpha.loveletter.jugador.Jugador;
import equipoalpha.loveletter.partida.Sala;
import equipoalpha.loveletter.util.Drawable;

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
    private JComboBox<Jugador> jugadorElegido = new JComboBox<>();
    private JButton botonConfirmarJugador = new JButton("Confirmar");
    private JPanel panelElegirJugador;
    private JComboBox<CartaTipo> cartaAdivinada = new JComboBox<>();
    private JButton botonConfirmarCarta = new JButton("Confirmar");
    private JPanel panelAdivinarCarta;
    private boolean actualizarJugadores = false;

    public PanelPartida(Ventana ventana, Sala sala) {
        this.parent = ventana;
        this.sala = sala;
        this.loveletter = LoveLetter.getInstance();
        this.jugador = loveletter.getJugador();
        botonCarta1 = new JButton();
        botonCarta2 = new JButton();
        add(botonCarta1);
        add(botonCarta2);

        panelElegirJugador = new JPanel();
        panelElegirJugador.setVisible(false);
        panelElegirJugador.setBackground(new Color(0, 0, 0, 255));
        panelElegirJugador.setBorder(new BorderUIResource.LineBorderUIResource(new Color(255, 255, 255, 255)));
        panelElegirJugador.add(jugadorElegido);
        panelElegirJugador.add(botonConfirmarJugador);

        panelAdivinarCarta = new JPanel();
        panelAdivinarCarta.setVisible(false);
        panelAdivinarCarta.setBackground(new Color(0, 0, 0, 255));
        panelAdivinarCarta.setBorder(new BorderUIResource.LineBorderUIResource(new Color(255, 255, 255, 255)));
        panelAdivinarCarta.add(cartaAdivinada);
        panelAdivinarCarta.add(botonConfirmarCarta);
        registrar();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.jugadoresAdibujar = new ArrayList<>(sala.partida.rondaActual.jugadoresEnLaRonda);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(Imagenes.backgroundPartida, null, 0, 0);

        if (jugadoresAdibujar.contains(jugador)) {
            botonCarta1.setIcon(new ImageIcon(jugador.carta1.getImagen()));
            botonCarta1.setVisible(true);
            botonCarta1.setBounds(380, 500, 150, 210);
            if (jugador.carta2 != null) {
                botonCarta2.setIcon(new ImageIcon(jugador.carta2.getImagen()));
                botonCarta2.setBounds(530, 500, 150, 210);
                botonCarta2.setVisible(true);
            } else botonCarta2.setVisible(false);

            if (jugador.getEstado().getEstadoActual() == EstadosJugador.DESCARTANDO ||
                    jugador.getEstado().getEstadoActual() == EstadosJugador.DESCARTANDOCONDESA) {
                botonCarta1.setEnabled(true);
                botonCarta2.setEnabled(true);
            } else {
                botonCarta1.setEnabled(false);
                botonCarta2.setEnabled(false);
            }

        } else {
            botonCarta1.setVisible(false);
            botonCarta2.setVisible(false);
        }

        this.jugadoresAdibujar.remove(jugador); // ya me dibuje

        // mazo
        if(!sala.partida.rondaActual.mazoVacio()) {
            //TODO buscar imagen de algun mazo tal vez?
            g2.drawImage(Imagenes.reversoPeq, null, 380, 200);
            g2.drawImage(Imagenes.reversoPeq, null, 390, 205);
            g2.drawImage(Imagenes.reversoPeq, null, 400, 210);
        }

        // la carta eliminada
        if(sala.partida.rondaActual.cartaEliminada != null) {
            g2.drawImage(Imagenes.reversoPeq, null, 10, 610);
        }

        int i = 0;
        for (Jugador jugador : jugadoresAdibujar) {
            switch (i) {
                case 0:
                    g2.drawImage(Imagenes.reversoPeq, null, 10,250);
                    if(jugador.carta2 != null) {
                        g2.drawImage(Imagenes.reversoPeq, null, 80,250);
                    }
                    break;
                case 1:
                    g2.drawImage(Imagenes.reversoPeq, null, 400,10);
                    if(jugador.carta2 != null) {
                        g2.drawImage(Imagenes.reversoPeq, null, 470,10);
                    }
                    break;
                case 2:
                    g2.drawImage(Imagenes.reversoPeq, null, 925,250);
                    if(jugador.carta2 != null) {
                        g2.drawImage(Imagenes.reversoPeq, null, 850,250);
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
        if(panelElegirJugador.isVisible()) {
            actualizarJugadores = true;
        }
        if(actualizarJugadores) {
            jugadorElegido.removeAllItems();
            for(Jugador jugador : sala.partida.rondaActual.jugadoresEnLaRonda) {
                jugadorElegido.addItem(jugador);
            }
            actualizarJugadores = false;
        }
    }
}
