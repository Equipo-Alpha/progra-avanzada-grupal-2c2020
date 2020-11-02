package equipoalpha.loveletter.pantalla;

import javax.swing.*;
import java.awt.*;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class Ventana {
    JFrame ventana;

    public Ventana() {
        ventana = new JFrame("Love Letter");
        ventana.setSize(1024, 768);
        ventana.setPreferredSize(new Dimension(1024, 768));
        PanelElegirNombre panelElegirNombre = new PanelElegirNombre(this);
        ventana.add(panelElegirNombre);
        ventana.pack();
        ventana.setDefaultCloseOperation(EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);
        ventana.setResizable(false);
        ventana.setVisible(true);
        panelElegirNombre.setVisible(true);
        ventana.setFocusable(true);
        ventana.requestFocusInWindow();
    }

    public void onLogin(JPanel panelAnterior) {
        PanelMenuPrincipal panelMenuPrincipal = new PanelMenuPrincipal(this);
        ventana.add(panelMenuPrincipal);
        ventana.pack();
        panelAnterior.setVisible(false);
        panelMenuPrincipal.setVisible(true);
    }

    public void onCrearSala(JPanel panelAnterior) {
        PanelSala panelJuego = new PanelSala(this);
        ventana.add(panelJuego);
        ventana.pack();
        panelAnterior.setVisible(false);
        panelJuego.setVisible(true);
    }

    public void onSalirSala(JPanel panelAnterior) {
        Component[] components = ventana.getComponents();
        for (Component comp : components) {
            if (comp instanceof PanelMenuPrincipal) {
                panelAnterior.setVisible(false);
                comp.setVisible(true);
                return;
            }
        }
        onLogin(panelAnterior); // deberia de encontrarlo pero bueno, por las dudas
    }

    public void onPartidaEmpezada(PanelSala panelAnterior) {
        PanelPartida panelPartida = new PanelPartida(this, panelAnterior.getSala());
        ventana.add(panelPartida);
        ventana.pack();
        panelAnterior.setVisible(false);
        panelPartida.setVisible(true);
        panelPartida.getSala().partida.initPartida();
    }

    public void onPartidaTerminada(JPanel panelAnterior) {
        Component[] components = ventana.getComponents();
        for (Component comp : components) {
            if (comp instanceof PanelSala) {
                panelAnterior.setVisible(false);
                comp.setVisible(true);
                return;
            }
        }
        onCrearSala(panelAnterior); // deberia de encontrarlo pero bueno, por las dudas
    }
}
