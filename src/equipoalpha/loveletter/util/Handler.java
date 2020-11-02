package equipoalpha.loveletter.util;

import java.util.LinkedList;

public class Handler {
    private final LinkedList<Tickable> tickableObject = new LinkedList<>();
    private final LinkedList<Drawable> drawableObject = new LinkedList<>();

    public void tick() {
        for (Tickable object : tickableObject) {
            object.tick();
        }
    }

    public void render() {
        for (Drawable object : drawableObject) {
            object.render();
        }
    }

    public void addTickableObject(Tickable object) {
        this.tickableObject.add(object);
    }

    public void addDrawableObject(Drawable object) {
        this.drawableObject.add(object);
    }
}
