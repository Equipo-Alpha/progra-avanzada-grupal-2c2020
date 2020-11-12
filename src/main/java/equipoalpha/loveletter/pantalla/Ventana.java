package equipoalpha.loveletter.pantalla;

import equipoalpha.loveletter.LoveLetter;
import equipoalpha.loveletter.util.Drawable;

import javax.swing.*;
import java.awt.*;

public class Ventana {
    JFrame ventana;

    public Ventana() {
        ventana = new JFrame("Love Letter");
        ventana.setSize(1024, 768);
        ventana.setPreferredSize(new Dimension(1024, 768));
        PanelElegirNombre panelElegirNombre = new PanelElegirNombre(this);
        ventana.add(panelElegirNombre);
        ventana.pack();
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);
        ventana.setResizable(false);
        ventana.setVisible(true);
        panelElegirNombre.setVisible(true);
        ventana.setFocusable(true);
        ventana.requestFocusInWindow();
    }

    public void onLogin(PanelElegirNombre panelAnterior) {
        PanelMenuPrincipal panelMenuPrincipal = new PanelMenuPrincipal(this);
        ventana.add(panelMenuPrincipal);
        ventana.pack();
        panelAnterior.setVisible(false);
        LoveLetter.handler.removeDrawableObject(panelAnterior);
        panelMenuPrincipal.setVisible(true);
    }

    public void onCrearSala(PanelMenuPrincipal panelAnterior) {
        PanelSala panelJuego = new PanelSala(this);
        ventana.add(panelJuego);
        ventana.pack();
        panelAnterior.setVisible(false);
        LoveLetter.handler.removeDrawableObject(panelAnterior);
        panelJuego.setVisible(true);
    }

    public void onSalirSala(Drawable panelAnterior) {
        Container container = ventana.getContentPane();
        for (Component component : container.getComponents()) {
            if (component instanceof PanelMenuPrincipal) {
                ((JPanel) panelAnterior).setVisible(false);
                LoveLetter.handler.removeDrawableObject(panelAnterior);
                component.setVisible(true);
                LoveLetter.handler.addDrawableObject((Drawable) component);
                return;
            }
        }
    }

    public void onPartidaEmpezada(PanelSala panelAnterior) {
        PanelPartida panelPartida = new PanelPartida(this, panelAnterior.getSala());
        ventana.add(panelPartida);
        ventana.pack();
        panelAnterior.setVisible(false);
        LoveLetter.handler.removeDrawableObject(panelAnterior);
        panelPartida.setVisible(true);
        panelPartida.getSala().partida.initPartida();
    }

    public void onPartidaTerminada(PanelPartida panelAnterior) {
        Container container = ventana.getContentPane();
        for (Component component : container.getComponents()) {
            if (component instanceof PanelSala) {
                panelAnterior.setVisible(false);
                LoveLetter.handler.removeDrawableObject(panelAnterior);
                component.setVisible(true);
                LoveLetter.handler.addDrawableObject((Drawable) component);
                return;
            }
        }
    }
}
