package equipoalpha.loveletter.pantalla;

import equipoalpha.loveletter.client.LoveLetter;
import equipoalpha.loveletter.util.Drawable;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class VentanaTutorial extends JPanel implements Drawable {
    private static final long serialVersionUID = 95894L;
    private final JButton btnBack;
    private final JButton btnSig;
    private final JButton btnAnt;
    private BufferedImage actual;

    public VentanaTutorial() {
        actual = Imagenes.reglas1;
        Font buttonFont = new Font("Monotype Corsiva", Font.BOLD, 26);
        btnBack = new JButton("Volver al menú");
        btnBack.setForeground(Color.WHITE);
        btnBack.setBackground(Color.BLACK);
        btnBack.setFont(buttonFont);
        btnSig = new JButton("Siguiente");
        btnSig.setForeground(Color.WHITE);
        btnSig.setBackground(Color.BLACK);
        btnSig.setFont(buttonFont);
        btnAnt = new JButton("Anterior");
        btnAnt.setForeground(Color.WHITE);
        btnAnt.setBackground(Color.BLACK);
        btnAnt.setFont(buttonFont);

        btnBack.addActionListener(actionEvent -> LoveLetter.getInstance().getVentana().onSalirTutorial());
        btnSig.addActionListener(actionEvent -> setSig());
        btnAnt.addActionListener(actionEvent -> setAnt());
        add(btnBack);
        add(btnSig);
        add(btnAnt);

        registrar();
    }


    @Override
    public void render() {
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(Imagenes.fondo3, null, 0, 0);
        g2.drawImage(actual, null, 150, 60);

        if (actual.equals(Imagenes.reglas1)) {
            btnAnt.setVisible(false);
        } else btnAnt.setVisible(true);

        if (actual.equals(Imagenes.reglas3)) {
            btnSig.setVisible(false);
        } else btnSig.setVisible(true);

        btnBack.setBounds(390, 580, 200, 60);
        btnSig.setBounds(700, 580, 150, 60);
        btnAnt.setBounds(150, 580, 150, 60);
    }

    private void setSig() {
        if (actual.equals(Imagenes.reglas1)) {
            actual = Imagenes.reglas2;
        } else if (actual.equals(Imagenes.reglas2)) {
            actual = Imagenes.reglas3;
        }
    }

    private void setAnt() {
        if (actual.equals(Imagenes.reglas2)) {
            actual = Imagenes.reglas1;
        } else if (actual.equals(Imagenes.reglas3)) {
            actual = Imagenes.reglas2;
        }
    }

}
