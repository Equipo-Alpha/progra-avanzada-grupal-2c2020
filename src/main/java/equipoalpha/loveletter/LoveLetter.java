package equipoalpha.loveletter;

import equipoalpha.loveletter.jugador.Jugador;
import equipoalpha.loveletter.pantalla.Imagenes;
import equipoalpha.loveletter.pantalla.Ventana;
import equipoalpha.loveletter.util.Handler;

import javax.swing.*;

public class LoveLetter implements Runnable {
    public static final boolean DEBUGGING = true;
    public static ClassLoader classLoader = LoveLetter.class.getClassLoader();
    public static Handler handler;
    private static LoveLetter instance;
    public final int WIDTH = 1024, HEIGHT = 768;
    private final int SECOND = 1000;
    private final int FRAMES_PER_SECOND = 60;
    private final int SKIP_FRAMES = SECOND / FRAMES_PER_SECOND;
    private final int TICKS_PER_SECOND = 20;
    public final int SKIP_TICKS = SECOND / TICKS_PER_SECOND;
    public int loops = 0;
    public int fps = 0;
    private Jugador jugador;
    private Thread thread;
    private boolean running = false;

    public LoveLetter() {
        instance = this;
        handler = new Handler();
        Imagenes.init();
    }

    public static void main(String[] args) {
        LoveLetter game = new LoveLetter();
        game.start();
        SwingUtilities.invokeLater(Ventana::new);
        game.run();
    }

    public static LoveLetter getInstance() {
        return instance;
    }

    public synchronized void start() {
        thread = new Thread(this, "juego");
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        long next_game_tick = System.currentTimeMillis();
        long next_game_frame = System.currentTimeMillis();
        long next_frame_calc = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            if (System.currentTimeMillis() > next_game_tick) {
                loops++;
                next_game_tick += SKIP_TICKS;
                tick();
            }
            if (System.currentTimeMillis() > next_game_frame) {
                frames++;
                next_game_frame += SKIP_FRAMES;
                render();
            }
            if (System.currentTimeMillis() > next_frame_calc) {
                fps = frames;
                next_frame_calc += SECOND;
                frames = 0;
            }
        }
        stop();
    }

    private void render() {
        handler.render();
    }

    private void tick() {
        handler.tick();
    }

    public Jugador getJugador() {
        return this.jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }
}
