package equipoalpha.loveletter.common;

import com.google.gson.JsonObject;
import equipoalpha.loveletter.util.JsonUtils;

public class PlayerDummy implements SyncInfo {
    public String nombre;
    public String icono; // si implementamos iconos personalizados va a tener que ser ImageIcon
    public boolean estaEnLaRonda = false;
    public boolean tieneCarta1 = false;
    public boolean tieneCarta2 = false;
    public boolean estaProtegido = false;
    public int cantSimbolos = 0;
    public int id;
    public int victorias;
    public int derrotas;

    public PlayerDummy(String nombre, String icono, int id) {
        this.nombre = nombre;
        this.icono = icono;
        this.id = id;
    }

    @Override
    public void serializarData(JsonObject dummyData) {
        dummyData.addProperty("nombre", nombre);
        dummyData.addProperty("icono", icono);
        dummyData.addProperty("id", id);
        dummyData.addProperty("estaEnLaRonda", estaEnLaRonda);
        dummyData.addProperty("tieneCarta1", tieneCarta1);
        dummyData.addProperty("tieneCarta2", tieneCarta2);
        dummyData.addProperty("estaProtegido", estaProtegido);
        dummyData.addProperty("cantSimbolos", cantSimbolos);
        dummyData.addProperty("victorias", victorias);
        dummyData.addProperty("derrotas", derrotas);
    }

    @Override
    public void deserializarData(JsonObject dummyData) {
        this.nombre = JsonUtils.getString(dummyData, "nombre");
        this.icono = JsonUtils.getString(dummyData, "icono");
        this.id = JsonUtils.getInt(dummyData, "id");
        this.estaEnLaRonda = JsonUtils.getBoolean(dummyData, "estaEnLaRonda");
        this.tieneCarta1 = JsonUtils.getBoolean(dummyData, "tieneCarta1");
        this.tieneCarta2 = JsonUtils.getBoolean(dummyData, "tieneCarta2");
        this.estaProtegido = JsonUtils.getBoolean(dummyData, "estaProtegido");
        this.cantSimbolos = JsonUtils.getInt(dummyData, "cantSimbolos");
        this.victorias = JsonUtils.getInt(dummyData, "victorias");
        this.derrotas = JsonUtils.getInt(dummyData, "derrotas");
    }

}
