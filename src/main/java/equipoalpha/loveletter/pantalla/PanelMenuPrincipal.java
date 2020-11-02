package equipoalpha.loveletter.pantalla;

import equipoalpha.loveletter.LoveLetter;
import equipoalpha.loveletter.util.Drawable;

import javax.swing.*;
import java.awt.*;

import static java.lang.System.exit;

public class PanelMenuPrincipal extends JPanel implements Drawable {
    private static final long serialVersionUID = 3L;
    private final Ventana parent;
    private final LoveLetter loveletter;
    private final JPanel equipo;
    private final JButton buttonCrearSala;
    private final JButton buttonUnirseSala;
    private final JButton buttonSalir;
    private final JButton buttonEquipo;
    private final JLabel ramiro;
    private final JLabel kevin;
    private final JLabel santiG;
    private final JLabel nico;
    private final JLabel ignacio;
    private final JLabel santiV;
    private final JLabel mati;
    private boolean isExpanding, isMaxSize, isAchicandose, isMinSize, moviendoCentro;
    private float sizeX = 0, sizeY = 0;
    private int centroX;

    public PanelMenuPrincipal(Ventana ventana) {
        parent = ventana;
        setSize(1024, 768);
        loveletter = LoveLetter.getInstance();
        equipo = new JPanel();
        equipo.setBackground(new Color(78, 73, 73, 225));
        add(equipo);
        equipo.setVisible(false);

        Font fEquipo = new Font("Dialog", Font.PLAIN, 18);
        ramiro = new JLabel("Ramiro Valle");
        ramiro.setFont(fEquipo);
        ramiro.setForeground(Color.WHITE);
        equipo.add(ramiro);
        kevin = new JLabel("Kevin Niedfeld");
        kevin.setFont(fEquipo);
        kevin.setForeground(Color.WHITE);
        equipo.add(kevin);
        santiG = new JLabel("Santiago Giasone");
        santiG.setFont(fEquipo);
        santiG.setForeground(Color.WHITE);
        equipo.add(santiG);
        nico = new JLabel("Nicolas Muryn");
        nico.setFont(fEquipo);
        nico.setForeground(Color.WHITE);
        equipo.add(nico);
        ignacio = new JLabel("Ignacio Arias");
        ignacio.setFont(fEquipo);
        ignacio.setForeground(Color.WHITE);
        equipo.add(ignacio);
        santiV = new JLabel("Santiago Vasquez");
        santiV.setFont(fEquipo);
        santiV.setForeground(Color.WHITE);
        equipo.add(santiV);
        mati = new JLabel("Matias Ramirez");
        mati.setFont(fEquipo);
        mati.setForeground(Color.WHITE);
        equipo.add(mati);

        isExpanding = isMaxSize = isMinSize = isAchicandose = moviendoCentro = false;
        centroX = (loveletter.WIDTH / 2) - 200;
        buttonCrearSala = new JButton("Crear sala");
        buttonUnirseSala = new JButton("Unirse sala");
        buttonSalir = new JButton("Salir");
        buttonEquipo = new JButton("Miembros");
        Font buttonFont = new Font("Arial", Font.BOLD, 26);
        buttonCrearSala.setFont(buttonFont);
        buttonUnirseSala.setFont(buttonFont);
        buttonSalir.setFont(buttonFont);
        buttonFont = new Font("Arial", Font.PLAIN, 14);
        buttonEquipo.setFont(buttonFont);
        add(buttonCrearSala);
        add(buttonUnirseSala);
        add(buttonSalir);
        add(buttonEquipo);

        buttonEquipo.addActionListener(actionEvent -> {
            if (!equipo.isVisible()) {
                isMinSize = false;
                isAchicandose = false;
                equipo.setVisible(true);
                isExpanding = true;
            } else {
                isMaxSize = false;
                isExpanding = false;
                isAchicandose = true;
            }
        });

        buttonCrearSala.addActionListener(actionEvent -> moviendoCentro = true);

        buttonSalir.addActionListener(actionEvent -> exit(0));

        registrar();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(Imagenes.background, null, 0, 0);

        if (moviendoCentro) {
            moverCentro(g2);
        } else {
            Color color = new Color(0, 0, 0, 185);
            g2.setColor(color);
            g2.fillRect((loveletter.WIDTH / 2) - 200, 0, 400, loveletter.HEIGHT);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 40));
            g2.drawString("LOVE LETTER", 370, 100);
            g2.setFont(new Font("Arial", Font.BOLD, 24));
            g2.drawString("Bienvenido " + loveletter.getJugador(), 25, 700);

            buttonUnirseSala.setBounds(360, 280, 300, 64);
            buttonCrearSala.setBounds(360, 380, 300, 64);
            buttonSalir.setBounds(360, 480, 300, 64);
        }
        buttonEquipo.setBounds(900, 10, 100, 32);

        if (isExpanding) expandirEquipo();
        if (isMaxSize) equipo.setBounds(800, 42, 200, 250);
        if (isAchicandose) achicarEquipo();
        if (isMinSize) equipo.setVisible(false);

        if (sizeX > 120) {
            ramiro.setVisible(true);
            kevin.setVisible(true);
            santiG.setVisible(true);
            nico.setVisible(true);
            ignacio.setVisible(true);
            santiV.setVisible(true);
            mati.setVisible(true);
            ramiro.setBounds(30, 20, 200, 20);
            kevin.setBounds(30, 50, 200, 20);
            santiG.setBounds(30, 80, 200, 20);
            nico.setBounds(30, 110, 200, 20);
            ignacio.setBounds(30, 140, 200, 20);
            santiV.setBounds(30, 170, 200, 20);
            mati.setBounds(30, 200, 200, 20);
        } else {
            ramiro.setVisible(false);
            kevin.setVisible(false);
            santiG.setVisible(false);
            nico.setVisible(false);
            ignacio.setVisible(false);
            santiV.setVisible(false);
            mati.setVisible(false);
        }

        if (LoveLetter.DEBUGGING) {
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Dialog", Font.BOLD, 16));
            g2.drawString("FPS: " + loveletter.fps + "", 20, 25);
        }
    }

    @Override
    public void render() {
        this.repaint();
    }

    private void expandirEquipo() {
        if (sizeX < 200 && sizeY < 250) {
            sizeX += 5;
            sizeY += 6.25;
        } else {
            sizeX = 200;
            sizeY = 250;
            isMaxSize = true;
            return;
        }
        equipo.setBounds(1000 - (int) sizeX, 42, (int) sizeX, (int) sizeY);
    }

    private void achicarEquipo() {
        if (sizeX > 2 && sizeY > 2) {
            sizeX -= 1;
            sizeY -= 1.25;
        } else {
            sizeX = 0;
            sizeY = 0;
            isMinSize = true;
            return;
        }
        equipo.setBounds(1000 - (int) sizeX, 42, (int) sizeX, (int) sizeY);
    }

    private void moverCentro(Graphics2D g2) {
        Color color = new Color(0, 0, 0, 185);
        g2.setColor(color);
        centroX -= 8;
        if (centroX > 10) g2.fillRect(centroX, 0, 400, loveletter.HEIGHT);
        else parent.onCrearSala(this);
    }
}
