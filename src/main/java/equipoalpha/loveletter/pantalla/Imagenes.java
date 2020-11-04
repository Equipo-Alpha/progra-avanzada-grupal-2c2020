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

    public static BufferedImage guardia;
    public static BufferedImage guardiaP;
    public static BufferedImage sacerdote;
    public static BufferedImage sacerdoteP;
    public static BufferedImage baron;
    public static BufferedImage baronP;
    public static BufferedImage mucama;
    public static BufferedImage mucamaP;
    public static BufferedImage principe;
    public static BufferedImage principeP;
    public static BufferedImage rey;
    public static BufferedImage reyP;
    public static BufferedImage condesa;
    public static BufferedImage condesaP;
    public static BufferedImage princesa;
    public static BufferedImage princesaP;
    public static BufferedImage reverso;
    public static BufferedImage reversoPeq;

    public static void init() {
        try {
            background = ImageIO.read(LoveLetter.classLoader.getResource("cards.png"));
            iconoBot.setImage(ImageIO.read(LoveLetter.classLoader.getResource("bot.png")));
            backgroundPartida = ImageIO.read(LoveLetter.classLoader.getResource("background.png"));
            iconoSuma.setImage(ImageIO.read(LoveLetter.classLoader.getResource("plus.png")));
            iconoPrincipe.setImage(ImageIO.read(LoveLetter.classLoader.getResource("principeIcono.png")));

            /// cartas
            guardia = ImageIO.read(LoveLetter.classLoader.getResource("guardia.png"));
            guardiaP = ImageIO.read(LoveLetter.classLoader.getResource("guardiaP.png"));
            sacerdote = ImageIO.read(LoveLetter.classLoader.getResource("sacerdote.png"));
            sacerdoteP = ImageIO.read(LoveLetter.classLoader.getResource("sacerdoteP.png"));
            baron = ImageIO.read(LoveLetter.classLoader.getResource("baron.png"));
            baronP = ImageIO.read(LoveLetter.classLoader.getResource("baronP.png"));
            mucama = ImageIO.read(LoveLetter.classLoader.getResource("mucama.png"));
            mucamaP = ImageIO.read(LoveLetter.classLoader.getResource("mucamaP.png"));
            principe = ImageIO.read(LoveLetter.classLoader.getResource("principe.png"));
            principeP = ImageIO.read(LoveLetter.classLoader.getResource("principeP.png"));
            rey = ImageIO.read(LoveLetter.classLoader.getResource("rey.png"));
            reyP = ImageIO.read(LoveLetter.classLoader.getResource("reyP.png"));
            condesa = ImageIO.read(LoveLetter.classLoader.getResource("condesa.png"));
            condesaP = ImageIO.read(LoveLetter.classLoader.getResource("condesaP.png"));
            princesa = ImageIO.read(LoveLetter.classLoader.getResource("princesa.png"));
            princesaP = ImageIO.read(LoveLetter.classLoader.getResource("princesaP.png"));
            reverso = ImageIO.read(LoveLetter.classLoader.getResource("reverso.png"));
            reversoPeq = ImageIO.read(LoveLetter.classLoader.getResource("reversoPeq.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
