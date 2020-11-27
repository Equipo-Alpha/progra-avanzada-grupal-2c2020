package equipoalpha.loveletter.pantalla;

import equipoalpha.loveletter.carta.CartaTipo;
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
    public static BufferedImage reverso;
    public static BufferedImage reversoPeq;

    public static BufferedImage guardia;
    public static BufferedImage sacerdote;
    public static BufferedImage baron;
    public static BufferedImage mucama;
    public static BufferedImage principe;
    public static BufferedImage rey;
    public static BufferedImage condesa;
    public static BufferedImage princesa;

    public static BufferedImage guardiaNormal;
    public static BufferedImage sacerdoteNormal;
    public static BufferedImage baronNormal;
    public static BufferedImage mucamaNormal;
    public static BufferedImage principeNormal;
    public static BufferedImage reyNormal;
    public static BufferedImage condesaNormal;
    public static BufferedImage princesaNormal;
    public static BufferedImage reversoNormal;

    public static BufferedImage guardiaSW;
    public static BufferedImage sacerdoteSW;
    public static BufferedImage baronSW;
    public static BufferedImage mucamaSW;
    public static BufferedImage principeSW;
    public static BufferedImage reySW;
    public static BufferedImage condesaSW;
    public static BufferedImage princesaSW;
    public static BufferedImage reversoSW;

    public static void init() {
        try {
            background = ImageIO.read(LoveLetter.classLoader.getResource("assets/cards.png"));
            iconoBot.setImage(ImageIO.read(LoveLetter.classLoader.getResource("assets/bot.png")));
            backgroundPartida = ImageIO.read(LoveLetter.classLoader.getResource("assets/background.png"));
            iconoSuma.setImage(ImageIO.read(LoveLetter.classLoader.getResource("assets/plus.png")));
            iconoPrincipe.setImage(ImageIO.read(LoveLetter.classLoader.getResource("assets/principeIcono.png")));
            iconoPrincesa.setImage(ImageIO.read(LoveLetter.classLoader.getResource("assets/princesaIcono.png")));
            reverso = ImageIO.read(LoveLetter.classLoader.getResource("assets/reverso.png"));

            /// cartas
            guardiaNormal = ImageIO.read(LoveLetter.classLoader.getResource("assets/cartas/normales/guardia.png"));
            sacerdoteNormal = ImageIO.read(LoveLetter.classLoader.getResource("assets/cartas/normales/sacerdote.png"));
            baronNormal = ImageIO.read(LoveLetter.classLoader.getResource("assets/cartas/normales/baron.png"));
            mucamaNormal = ImageIO.read(LoveLetter.classLoader.getResource("assets/cartas/normales/mucama.png"));
            principeNormal = ImageIO.read(LoveLetter.classLoader.getResource("assets/cartas/normales/principe.png"));
            reyNormal = ImageIO.read(LoveLetter.classLoader.getResource("assets/cartas/normales/rey.png"));
            condesaNormal = ImageIO.read(LoveLetter.classLoader.getResource("assets/cartas/normales/condesa.png"));
            princesaNormal = ImageIO.read(LoveLetter.classLoader.getResource("assets/cartas/normales/princesa.png"));
            reversoNormal = ImageIO.read(LoveLetter.classLoader.getResource("assets/cartas/normales/reversoPeq.png"));

            guardiaSW = ImageIO.read(LoveLetter.classLoader.getResource("assets/cartas/starwars/atacante.png"));
            sacerdoteSW = ImageIO.read(LoveLetter.classLoader.getResource("assets/cartas/starwars/aliado.png"));
            baronSW = ImageIO.read(LoveLetter.classLoader.getResource("assets/cartas/starwars/justiciero.png"));
            mucamaSW = ImageIO.read(LoveLetter.classLoader.getResource("assets/cartas/starwars/maestrojedi.png"));
            principeSW = ImageIO.read(LoveLetter.classLoader.getResource("assets/cartas/starwars/heroe.png"));
            reySW = ImageIO.read(LoveLetter.classLoader.getResource("assets/cartas/starwars/lordsith.png"));
            condesaSW = ImageIO.read(LoveLetter.classLoader.getResource("assets/cartas/starwars/lordvader.png"));
            princesaSW = ImageIO.read(LoveLetter.classLoader.getResource("assets/cartas/starwars/princesa.png"));
            reversoSW = ImageIO.read(LoveLetter.classLoader.getResource("assets/cartas/starwars/reverso.png"));

            elegirNormales();
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

    public static void elegirNormales() {
        guardia = guardiaNormal;
        sacerdote = sacerdoteNormal;
        baron = baronNormal;
        mucama = mucamaNormal;
        principe = principeNormal;
        rey = reyNormal;
        condesa = condesaNormal;
        princesa = princesaNormal;
        reversoPeq = reversoNormal;
        CartaTipo.refreshAll();
    }

    public static void elegirStarWars() {
        guardia = guardiaSW;
        sacerdote = sacerdoteSW;
        baron = baronSW;
        mucama = mucamaSW;
        principe = principeSW;
        rey = reySW;
        condesa = condesaSW;
        princesa = princesaSW;
        reversoPeq = reversoSW;
        CartaTipo.refreshAll();
    }

    public static BufferedImage getImagenPorNombre(String nombre) {
        switch (nombre) {
            case "guardia": return guardia;
            case "sacerdote": return sacerdote;
            case "baron": return baron;
            case "mucama": return mucama;
            case "principe": return principe;
            case "rey": return rey;
            case "condesa": return condesa;
            case "princesa": return princesa;
        }
        throw new IllegalArgumentException("Nombre desconocido");
    }
}
