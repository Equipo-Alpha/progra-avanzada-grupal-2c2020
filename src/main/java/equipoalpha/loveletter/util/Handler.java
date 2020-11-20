package equipoalpha.loveletter.util;

import java.util.LinkedList;

public class Handler {
    private static final Handler instance = new Handler();
    private static final Object syncHelper = new Object();
    private final LinkedList<Drawable> drawableObject = new LinkedList<>();

    private Handler() {}

    public static Handler getInstance() {
        return instance;
    }

    public void render() {
        synchronized (syncHelper) {
            for (Drawable object : drawableObject) {
                object.render();
            }
        }
    }

    public void addDrawableObject(Drawable object) {
        synchronized (syncHelper) {
            this.drawableObject.addLast(object);
        }
    }

    public void removeDrawableObject(Drawable object) {
        synchronized (syncHelper) {
            this.drawableObject.remove(object);
        }
    }
}
