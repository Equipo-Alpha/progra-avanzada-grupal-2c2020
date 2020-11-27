package equipoalpha.loveletter.carta;

import equipoalpha.loveletter.pantalla.Imagenes;

import java.awt.image.BufferedImage;

public enum CartaTipo {
    GUARDIA("guardia", 1, 5, Imagenes.guardia, Descripcion.GUARDIA),
    SACERDOTE("sacerdote", 2, 2, Imagenes.sacerdote, Descripcion.SACERDOTE),
    BARON("baron", 3, 2, Imagenes.baron, Descripcion.BARON),
    MUCAMA("mucama", 4, 2, Imagenes.mucama, Descripcion.MUCAMA),
    PRINCIPE("principe", 5, 2, Imagenes.principe, Descripcion.PRINCIPE),
    REY("rey", 6, 1, Imagenes.rey, Descripcion.REY),
    CONDESA("condesa", 7, 1, Imagenes.condesa, Descripcion.CONDESA),
    PRINCESA("princesa", 8, 1, Imagenes.princesa, Descripcion.PRINCESA);

    public String nombre;
    public int fuerza;
    public int cantCartas;
    public BufferedImage imagen;
    public String descripcion;

    CartaTipo(String nombre, int fuerza, int cantCartas, BufferedImage imagen, String desc) {
        this.nombre = nombre;
        this.fuerza = fuerza;
        this.cantCartas = cantCartas;
        this.imagen = imagen;
        this.descripcion = desc;
    }

    public static void refreshAll() {
        for (CartaTipo c : values()) {
            c.imagen = Imagenes.getImagenPorNombre(c.nombre);
        }
    }
}
