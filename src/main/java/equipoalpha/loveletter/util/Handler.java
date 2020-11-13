package equipoalpha.loveletter.util;

import java.util.LinkedList;

public class Handler {
    private static final Handler instance = new Handler();
    private static final Object syncHelper = new Object();
    private final LinkedList<Tickable> tickableObject = new LinkedList<>();
    private final LinkedList<Drawable> drawableObject = new LinkedList<>();

    private Handler() {}

    public static Handler getInstance() {
        return instance;
    }

    public void tick() {
        synchronized (syncHelper) {
            for (Tickable object : tickableObject) {
                object.tick();
            }
        }
    }

    public void render() {
        synchronized (syncHelper) {
            for (Drawable object : drawableObject) {
                object.render();
            }
        }
    }

    public void addTickableObject(Tickable object) {
        synchronized (syncHelper) {
            this.tickableObject.addLast(object);
        }
    }

    public void removeTickableObject(Tickable object) {
        synchronized (syncHelper) {
            this.tickableObject.remove(object);
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
