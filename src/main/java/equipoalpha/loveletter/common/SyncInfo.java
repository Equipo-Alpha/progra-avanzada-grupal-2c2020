package equipoalpha.loveletter.common;

import com.google.gson.JsonObject;

public interface SyncInfo {
    void serializarData(JsonObject object);
    void deserializarData(JsonObject object);
}
