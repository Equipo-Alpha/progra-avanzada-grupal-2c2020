package equipoalpha.loveletter.common;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import equipoalpha.loveletter.util.JsonUtils;

import java.util.ArrayList;

public class SalaInfo implements SyncInfo{
    // informacion de la sala que debe tener el cliente
    public String nombre;
    public PlayerDummy creador;
    public ArrayList<PlayerDummy> jugadores;
    public boolean isConfigurada = false;

    public SalaInfo() {
        this.jugadores = new ArrayList<>();
    }

    @Override
    public void serializarData(JsonObject salaData) {
        salaData.addProperty("nombre", nombre);
        salaData.add("creador", new Gson().toJsonTree(creador));
        salaData.add("jugadores", new Gson().toJsonTree(jugadores, new TypeToken<ArrayList<PlayerDummy>>(){}.getType()));
        salaData.addProperty("configurada", isConfigurada);
    }

    @Override
    public void deserializarData(JsonObject salaData) {
        this.jugadores.clear();
        this.nombre = JsonUtils.getString(salaData, "nombre");
        this.creador = new Gson().fromJson(salaData.get("creador"), PlayerDummy.class);
        this.jugadores = new Gson().fromJson(salaData.get("jugadores"), new TypeToken<ArrayList<PlayerDummy>>(){}.getType());
        this.isConfigurada = JsonUtils.getBoolean(salaData, "configurada");
    }
}
