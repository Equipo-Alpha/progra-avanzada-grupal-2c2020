package equipoalpha.loveletter.pantalla;

import equipoalpha.loveletter.client.LoveLetter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Imagenes {
    public static BufferedImage background;
    public static ImageIcon iconoBot = new ImageIcon();
    public static BufferedImage backgroundPartida;
    public static ImageIcon iconoSuma = new ImageIcon();
    public static ImageIcon iconoPrincipe = new ImageIcon();
    public static ImageIcon iconoPrincesa = new ImageIcon();

    public static BufferedImage guardia;
    public static BufferedImage sacerdote;
    public static BufferedImage baron;
    public static BufferedImage mucama;
    public static BufferedImage principe;
    public static BufferedImage rey;
    public static BufferedImage condesa;
    public static BufferedImage princesa;
    public static BufferedImage reverso;
    public static BufferedImage reversoPeq;

    public static void init() {
        try {
            background = ImageIO.read(LoveLetter.classLoader.getResource("assets/cards.png"));
            iconoBot.setImage(ImageIO.read(LoveLetter.classLoader.getResource("assets/bot.png")));
            backgroundPartida = ImageIO.read(LoveLetter.classLoader.getResource("assets/background.png"));
            iconoSuma.setImage(ImageIO.read(LoveLetter.classLoader.getResource("assets/plus.png")));
            iconoPrincipe.setImage(ImageIO.read(LoveLetter.classLoader.getResource("assets/principeIcono.png")));
            iconoPrincesa.setImage(ImageIO.read(LoveLetter.classLoader.getResource("assets/princesaIcono.png")));

            /// cartas
            guardia = ImageIO.read(LoveLetter.classLoader.getResource("assets/guardia.png"));
            sacerdote = ImageIO.read(LoveLetter.classLoader.getResource("assets/sacerdote.png"));
            baron = ImageIO.read(LoveLetter.classLoader.getResource("assets/baron.png"));
            mucama = ImageIO.read(LoveLetter.classLoader.getResource("assets/mucama.png"));
            principe = ImageIO.read(LoveLetter.classLoader.getResource("assets/principe.png"));
            rey = ImageIO.read(LoveLetter.classLoader.getResource("assets/rey.png"));
            condesa = ImageIO.read(LoveLetter.classLoader.getResource("assets/condesa.png"));
            princesa = ImageIO.read(LoveLetter.classLoader.getResource("assets/princesa.png"));
            reverso = ImageIO.read(LoveLetter.classLoader.getResource("assets/reverso.png"));
            reversoPeq = ImageIO.read(LoveLetter.classLoader.getResource("assets/reversoPeq.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ImageIcon getIconoPorNombre(String nombre) {
        switch (nombre) {
            case "bot":
                return iconoBot;
            case "principe":
                return iconoPrincipe;
            case "princesa":
                return iconoPrincesa;
            default:
                return null;
        }
    }
}
