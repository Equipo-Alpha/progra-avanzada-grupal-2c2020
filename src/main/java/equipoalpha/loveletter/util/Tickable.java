package equipoalpha.loveletter.util;

import equipoalpha.loveletter.LoveLetter;

public interface Tickable {
    void tick();
    default void registrar(){
        LoveLetter.handler.addTickableObject(this);
    }
}
