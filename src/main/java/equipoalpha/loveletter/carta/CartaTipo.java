package equipoalpha.loveletter.carta;

import equipoalpha.loveletter.pantalla.Imagenes;

import java.awt.image.BufferedImage;

public enum CartaTipo {
    GUARDIA("guardia", 1, 5, Imagenes.guardia),
    SACERDOTE("sacerdote", 2, 2, Imagenes.sacerdote),
    BARON("baron", 3, 2, Imagenes.baron),
    MUCAMA("mucama", 4, 2, Imagenes.mucama),
    PRINCIPE("principe", 5, 2, Imagenes.principe),
    REY("rey", 6, 1, Imagenes.rey),
    CONDESA("condesa", 7, 1, Imagenes.condesa),
    PRINCESA("princesa", 8, 1, Imagenes.princesa);

    public String nombre;
    public int fuerza;
    public int cantCartas;
    public BufferedImage imagen;

    CartaTipo(String nombre, int fuerza, int cantCartas, BufferedImage imagen) {
        this.nombre = nombre;
        this.fuerza = fuerza;
        this.cantCartas = cantCartas;
        this.imagen = imagen;
    }
}
