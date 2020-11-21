package equipoalpha.loveletter.util;

import equipoalpha.loveletter.client.LoveLetter;

public interface Drawable {
    void render();

    default void registrar() {
        LoveLetter.handler.addDrawableObject(this);
    }
}
