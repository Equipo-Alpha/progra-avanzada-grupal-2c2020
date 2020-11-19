package equipoalpha.loveletter.jugador;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import equipoalpha.loveletter.carta.Carta;
import equipoalpha.loveletter.carta.CartaTipo;
import equipoalpha.loveletter.common.SyncInfo;
import equipoalpha.loveletter.util.JsonUtils;

public abstract class Jugador implements SyncInfo {
    /**
     * Nombre del jugador
     */
    public String nombre;
    /**
     * La mano del jugador carta1 es la carta que siempre tiene en la mano carta2 es
     * la carta que roba del mazo
     */
    public Carta carta1;
    public Carta carta2;
    public String iconoNombre;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.iconoNombre = "principe";
    }

    public abstract void crearSala(String nombre);

    public abstract void iniciarPartida();

    public abstract void confirmarInicio();

    public abstract void cancelarInicio();

    public abstract void descartarCarta1();

    public abstract void descartarCarta2();

    public abstract void elegirCarta(CartaTipo cartaAdivinada);

    public abstract void terminarDeVer();

    @Override
    public void serializarData(JsonObject object) {
        object.addProperty("nombre", this.nombre);
        object.addProperty("icono", this.iconoNombre);
        if (carta1 != null)
            object.add("carta1", (new Gson().toJsonTree(carta1)));
        if (carta2 != null)
            object.add("carta2", (new Gson().toJsonTree(carta1)));
    }

    @Override
    public void deserializarData(JsonObject object) {
        this.nombre = JsonUtils.getString(object, "nombre");
        this.iconoNombre = JsonUtils.getString(object, "icono");

        if (JsonUtils.hasElement(object,"carta1"))
            this.carta1 = new Gson().fromJson(object.get("carta1"), Carta.class);
        else
            this.carta1 = null;

        if (JsonUtils.hasElement(object, "carta2"))
            this.carta2 = new Gson().fromJson(object.get("carta2"), Carta.class);
        else
            this.carta2 = null;
    }

    public boolean tieneCarta(CartaTipo tipo) {
        if (carta2 != null) {
            return (carta1.getTipo() == tipo || carta2.getTipo() == tipo);
        } else
            return carta1.getTipo() == tipo;
    }

    public int getFuerzaCarta() {
        return carta1.getTipo().fuerza;
    }

    public abstract void salirSala();

    @Override
    public String toString() {
        return nombre;
    }

}
