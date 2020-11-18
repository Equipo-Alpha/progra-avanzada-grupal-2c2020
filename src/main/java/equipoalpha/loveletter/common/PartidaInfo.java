package equipoalpha.loveletter.common;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import equipoalpha.loveletter.carta.Carta;
import equipoalpha.loveletter.util.JsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class PartidaInfo implements SyncInfo {
    // informacion de la partida que debe tener el jugador cliente
    public int ronda = 0;
    public LinkedList<PlayerDummy> ordenReparto;
    public ArrayList<PlayerDummy> jugadoresEnLaRonda;
    public PlayerDummy jugadorEnTurno;
    public HashMap<PlayerDummy, ArrayList<Carta>> mapaCartasDescartadas;
    public int mazo; // cantidad de cartas del mazo
    public boolean cartaEliminada; // si la carta eliminada esta disponible

    @Override
    public void serializarData(JsonObject partidaData) {
        partidaData.addProperty("ronda", ronda);
        partidaData.add("jugadorEnTurno", new Gson().toJsonTree(jugadorEnTurno, PlayerDummy.class));
        partidaData.add("ordenReparto", new Gson().toJsonTree(ordenReparto, new TypeToken<LinkedList<PlayerDummy>>() {}.getType()));
        partidaData.add("jugadoresEnLaRonda", new Gson().toJsonTree(jugadoresEnLaRonda, new TypeToken<ArrayList<PlayerDummy>>() {}.getType()));
        partidaData.add("mapaCartasDescartadas", new Gson().toJsonTree(mapaCartasDescartadas, new TypeToken<HashMap<PlayerDummy, ArrayList<Carta>>>() {}.getType()));
        partidaData.addProperty("mazo", mazo);
        partidaData.addProperty("cartaEliminada", cartaEliminada);
    }

    @Override
    public void deserializarData(JsonObject partidaData) {
        this.ronda = JsonUtils.getInt(partidaData, "ronda");
        this.ordenReparto = new Gson().fromJson(partidaData.get("ordenReparto"), new TypeToken<LinkedList<PlayerDummy>>() {}.getType());
        this.jugadoresEnLaRonda = new Gson().fromJson(partidaData.get("jugadoresEnLaRonda"), new TypeToken<ArrayList<PlayerDummy>>() {}.getType());
        this.jugadorEnTurno = new Gson().fromJson(partidaData.get("jugadorEnTurno"), PlayerDummy.class);
        this.mapaCartasDescartadas = new Gson().fromJson(partidaData.get("mapaCartasDescartadas"), new TypeToken<HashMap<PlayerDummy, ArrayList<Carta>>>() {}.getType());
        this.mazo = JsonUtils.getInt(partidaData, "mazo");
        this.cartaEliminada = JsonUtils.getBoolean(partidaData, "cartaEliminada");
    }
}
