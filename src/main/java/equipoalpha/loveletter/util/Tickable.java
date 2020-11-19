package equipoalpha.loveletter.util;

import equipoalpha.loveletter.client.LoveLetter;

public interface Tickable {
    void tick();
    default void registrar(){
        LoveLetter.handler.addTickableObject(this);
    }
}
