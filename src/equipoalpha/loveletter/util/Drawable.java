package equipoalpha.loveletter.util;

import equipoalpha.loveletter.LoveLetter;

public interface Drawable {
    void render();

    default void registrar() {
        LoveLetter.handler.addDrawableObject(this);
    }
}
