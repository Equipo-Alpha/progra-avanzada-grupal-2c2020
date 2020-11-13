package equipoalpha.loveletter;

import equipoalpha.loveletter.jugador.Jugador;
import equipoalpha.loveletter.pantalla.Imagenes;
import equipoalpha.loveletter.pantalla.Ventana;
import equipoalpha.loveletter.util.Handler;

import javax.swing.*;

public class LoveLetter implements Runnable {
    public static final boolean DEBUGGING = true;
    private static final LoveLetter instance = new LoveLetter();
    public static ClassLoader classLoader = LoveLetter.class.getClassLoader();
    public static Handler handler = Handler.getInstance();
    public final int WIDTH = 1024, HEIGHT = 768;
    public int fps = 0;
    private Jugador jugador;
    private Thread thread;
    private boolean running = false;

    private LoveLetter() {}

    public static void main(String[] args) {
        Imagenes.init();
        LoveLetter game = LoveLetter.getInstance();
        game.start();
        SwingUtilities.invokeLater(Ventana::new);
        game.run();
    }

    public static LoveLetter getInstance() {
        return instance;
    }

    public synchronized void start() {
        this.thread = new Thread(this, "juego");
        this.thread.start();
        this.running = true;
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
        final int SECOND = 1000;
        final int TICKS_PER_SECOND = 20;
        final int SKIP_TICKS = SECOND / TICKS_PER_SECOND;
        final int FRAMES_PER_SECOND = 60;
        final int SKIP_FRAMES = SECOND / FRAMES_PER_SECOND;
        long next_game_tick = System.currentTimeMillis();
        long next_game_frame = System.currentTimeMillis();
        long next_frame_calc = System.currentTimeMillis();
        int frames = 0;
        while (this.running) {
            if (System.currentTimeMillis() > next_game_tick) {
                next_game_tick += SKIP_TICKS;
                tick();
            }
            if (System.currentTimeMillis() > next_game_frame) {
                frames++;
                next_game_frame += SKIP_FRAMES;
                render();
            }
            if (System.currentTimeMillis() > next_frame_calc) {
                this.fps = frames;
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
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }
}
