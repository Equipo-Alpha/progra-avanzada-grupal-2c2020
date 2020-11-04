package equipoalpha.loveletter.carta;

import equipoalpha.loveletter.pantalla.Imagenes;

import java.awt.image.BufferedImage;

public enum CartaTipo {
    GUARDIA("guardia", 1, 5, Imagenes.guardia, Imagenes.guardiaP),
    SACERDOTE("sacerdote", 2, 2, Imagenes.sacerdote, Imagenes.sacerdoteP),
    BARON("baron", 3, 2, Imagenes.baron, Imagenes.baronP),
    MUCAMA("mucama", 4, 2, Imagenes.mucama, Imagenes.mucamaP),
    PRINCIPE("principe", 5, 2, Imagenes.principe, Imagenes.principeP),
    REY("rey", 6, 1, Imagenes.rey, Imagenes.reyP),
    CONDESA("condesa", 7, 1, Imagenes.condesa, Imagenes.condesaP),
    PRINCESA("princesa", 8, 1, Imagenes.princesa, Imagenes.princesaP);

    public String nombre;
    public int fuerza;
    public int cantCartas;
    public BufferedImage imagen;
    public BufferedImage imagenP;

    CartaTipo(String nombre, int fuerza, int cantCartas, BufferedImage imagen, BufferedImage imagenP) {
        this.nombre = nombre;
        this.fuerza = fuerza;
        this.cantCartas = cantCartas;
        this.imagen = imagen;
        this.imagenP = imagenP;
    }
}
