package equipoalpha.loveletter.pantalla;

import equipoalpha.loveletter.LoveLetter;

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
            background = ImageIO.read(LoveLetter.classLoader.getResource("cards.png"));
            iconoBot.setImage(ImageIO.read(LoveLetter.classLoader.getResource("bot.png")));
            backgroundPartida = ImageIO.read(LoveLetter.classLoader.getResource("background.png"));
            iconoSuma.setImage(ImageIO.read(LoveLetter.classLoader.getResource("plus.png")));
            iconoPrincipe.setImage(ImageIO.read(LoveLetter.classLoader.getResource("principeIcono.png")));
            iconoPrincesa.setImage(ImageIO.read(LoveLetter.classLoader.getResource("princesaIcono.png")));

            /// cartas
            guardia = ImageIO.read(LoveLetter.classLoader.getResource("guardia.png"));
            sacerdote = ImageIO.read(LoveLetter.classLoader.getResource("sacerdote.png"));
            baron = ImageIO.read(LoveLetter.classLoader.getResource("baron.png"));
            mucama = ImageIO.read(LoveLetter.classLoader.getResource("mucama.png"));
            principe = ImageIO.read(LoveLetter.classLoader.getResource("principe.png"));
            rey = ImageIO.read(LoveLetter.classLoader.getResource("rey.png"));
            condesa = ImageIO.read(LoveLetter.classLoader.getResource("condesa.png"));
            princesa = ImageIO.read(LoveLetter.classLoader.getResource("princesa.png"));
            reverso = ImageIO.read(LoveLetter.classLoader.getResource("reverso.png"));
            reversoPeq = ImageIO.read(LoveLetter.classLoader.getResource("reversoPeq.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
