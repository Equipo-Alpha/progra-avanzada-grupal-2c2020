package equipoalpha.loveletter.jugador;

import equipoalpha.loveletter.carta.Carta;
import equipoalpha.loveletter.carta.CartaTipo;
import equipoalpha.loveletter.pantalla.Imagenes;
import equipoalpha.loveletter.server.JugadorServer;
import equipoalpha.loveletter.util.Tickable;
import equipoalpha.loveletter.util.excepcion.JugadorNoValido;

import java.util.*;

import static java.lang.System.exit;

public class JugadorIA extends JugadorServer implements Runnable {
    private final Map<CartaTipo, Integer> cartasDescartadas = new HashMap<>();
    private final Map<JugadorServer, CartaTipo> cartasConocidas = new HashMap<>();
    private final Random random = new Random();
    private int tickcount = 200; // espera 200 ticks para jugar
    private Thread thread;
    private boolean running = false;

    public JugadorIA(String nombre) {
        super(null, -2);
        this.nombre = nombre;
        this.iconoNombre = "bot";
        this.thread = new Thread(this);
        this.thread.start();
        this.running = true;
    }

    @Override
    public void onComienzoTurno(Carta cartaRobada) {
        this.estaProtegido = false;
        System.out.println("Turno de " + nombre);
        robarCarta(cartaRobada);
        //Actualizo la carta que me toco
        int cant = cartasDescartadas.remove(carta2.getTipo());
        cartasDescartadas.put(carta2.getTipo(), ++cant);
    }

    public void onElegirJugador() throws JugadorNoValido {
        super.elegirJugador(elegirJugadorIA());
    }

    public void onAdivinarCarta() {
        super.elegirCarta(adivinarCarta());
    }

    private void elegirCartaAJugar() {
        if (this.facade.getEstadoActual() == EstadosJugador.DESCARTANDOCONDESA) {
            if (carta1.getTipo() == CartaTipo.CONDESA) descartarCarta1();
            else descartarCarta2();
            return;
        }

        if (carta1.getTipo() == carta2.getTipo()) {
            descartarCarta2();
            return;
        }

        if (carta2.getTipo().fuerza < carta1.getTipo().fuerza) {
            Carta temp = carta1;
            carta1 = carta2;
            carta2 = temp;
        }

        if (carta2.getTipo() == CartaTipo.PRINCESA) {
            descartarCarta1();
            return;
        }
        if (carta2.getTipo() == CartaTipo.CONDESA) {
            descartarCarta1();
            return;
        }

        if (carta1.getTipo() == CartaTipo.GUARDIA && conozcoCartas()) {
            descartarCarta1();
            return;
        }
        if (carta2.getTipo() == CartaTipo.GUARDIA && conozcoCartas()) {
            descartarCarta2();
            return;
        }

        if (carta1.getTipo() == CartaTipo.PRINCIPE && mayorCartaConocida() > 5) {
            descartarCarta1();
            return;
        }
        if (carta2.getTipo() == CartaTipo.PRINCIPE && mayorCartaConocida() > 5) {
            descartarCarta2();
            return;
        }

        if (carta1.getTipo() == CartaTipo.BARON && menorCartaConocida() < carta2.getTipo().fuerza) {
            descartarCarta1();
            return;
        }
        if (carta2.getTipo() == CartaTipo.BARON && menorCartaConocida() < carta1.getTipo().fuerza) {
            descartarCarta2();
            return;
        }

        if (carta2.getTipo() == CartaTipo.REY && mayorCartaConocida() > carta1.getTipo().fuerza) {
            descartarCarta2();
            return;
        }
        if (carta2.getTipo() == CartaTipo.REY) {
            descartarCarta1();
            return;
        }

        if (carta2.getTipo() == CartaTipo.PRINCIPE && carta1.getTipo() == CartaTipo.BARON) {
            descartarCarta2();
            return;
        }
        if (carta2.getTipo() == CartaTipo.PRINCIPE) {
            descartarCarta1();
            return;
        }

        if (carta2.getTipo() == CartaTipo.MUCAMA) {
            descartarCarta2();
            return;
        }

        if (carta2.getTipo() == CartaTipo.BARON) {
            descartarCarta1();
            return;
        }

        if (carta2.getTipo() == CartaTipo.SACERDOTE) {
            descartarCarta2();
            return;
        }

        descartarCarta2();
    }

    private JugadorServer elegirJugadorIA() {
        ArrayList<JugadorServer> disponibles = new ArrayList<>(rondaJugando.jugadoresEnLaRonda);

        if (facade.getCartaDescartada().getTipo() != CartaTipo.PRINCIPE)
            disponibles.remove(this); //si no es principe me remuevo

        disponibles.removeIf(protegidos -> protegidos.estaProtegido); //si el jugador esta protegido lo remuevo.
        if (disponibles.isEmpty()) return null; // si no queda nadie, no eligo a nadie.

        JugadorServer jugador; // jugador a elegir
        switch (facade.getCartaDescartada().getTipo()) {
            case GUARDIA:
            case PRINCIPE:
            case REY:
                jugador = mayorCartaConocida(disponibles);
                if (jugador == null) return elegirJugadorRandom(disponibles);
                else return jugador;

            case SACERDOTE:
                for (JugadorServer jugadorS : disponibles)
                    if (!cartasConocidas.containsKey(jugadorS))
                        return jugadorS;

                return elegirJugadorRandom(disponibles);

            case BARON:
                jugador = menorCartaConocida(disponibles);
                if (jugador == null) return elegirJugadorRandom(disponibles);
                if (carta1.getTipo().fuerza < cartasConocidas.get(jugador).fuerza) {
                    disponibles.remove(jugador);
                    return elegirJugadorRandom(disponibles);
                } else return jugador;

        }
        return null; //No deberia llegar aca, exception?
    }

    private CartaTipo adivinarCarta() {
        Jugador jugadorElegido = facade.getJugadorElegido();
        CartaTipo carta = cartasConocidas.getOrDefault(jugadorElegido, null);
        if (carta != null) {
            if (carta != CartaTipo.GUARDIA) return carta;
        }

        CartaTipo adivinada = CartaTipo.SACERDOTE; // por defecto se elige el sacerdote.
        for (CartaTipo tipo : CartaTipo.values()) {
            // se elige la carta de menos fuerza que no se descarto
            if (tipo != CartaTipo.GUARDIA && cartasDescartadas.get(tipo) == 0) {
                return tipo;
            }
            // si todas se descartaron al menos 1 vez, se elige la de mayor fuerza menos descartada.
            if (tipo != CartaTipo.GUARDIA && cartasDescartadas.get(tipo) < tipo.cantCartas)
                adivinada = tipo;
        }
        return adivinada;
    }

    private JugadorServer elegirJugadorRandom(List<JugadorServer> disponibles) {
        int nmroRandom = random.nextInt(disponibles.size());
        return disponibles.get(nmroRandom);
    }

    private boolean conozcoCartas() {
        return !cartasConocidas.isEmpty();
    }

    private JugadorServer menorCartaConocida(List<JugadorServer> lista) {
        JugadorServer menor = null;
        for (JugadorServer jugador : cartasConocidas.keySet()) {
            if (!lista.contains(jugador)) continue;
            if (menor == null) menor = jugador;
            if (cartasConocidas.get(jugador).fuerza < cartasConocidas.get(menor).fuerza)
                menor = jugador;
        }
        return menor;
    }

    private int menorCartaConocida() {
        int menor = 100;
        for (JugadorServer jugador : cartasConocidas.keySet()) {
            if (cartasConocidas.containsKey(jugador) && cartasConocidas.get(jugador).fuerza < menor
                    && !jugador.estaProtegido) {
                menor = cartasConocidas.get(jugador).fuerza;
            }
        }
        return menor;
    }

    private JugadorServer mayorCartaConocida(List<JugadorServer> lista) {
        JugadorServer mayor = null;
        for (JugadorServer jugador : cartasConocidas.keySet()) {
            if (!lista.contains(jugador)) continue;
            if (mayor == null) mayor = jugador;
            if (cartasConocidas.getOrDefault(jugador, CartaTipo.GUARDIA).fuerza > cartasConocidas.getOrDefault(mayor, CartaTipo.GUARDIA).fuerza)
                mayor = jugador;
        }
        return mayor;
    }

    private int mayorCartaConocida() {
        int mayor = 0;
        for (JugadorServer jugador : cartasConocidas.keySet()) {
            if (cartasConocidas.containsKey(jugador) && cartasConocidas.get(jugador).fuerza > mayor
                    && !jugador.estaProtegido) {
                mayor = cartasConocidas.get(jugador).fuerza;
            }
        }
        return mayor;
    }

    public void verCarta(JugadorServer jugador) {
        cartasConocidas.put(jugador, jugador.carta1.getTipo());
        rondaJugando.onFinalizarDescarte(this);
    }

    public void inicioRonda() {
        cartasConocidas.clear();
        cartasDescartadas.clear();

        for (CartaTipo tipo : CartaTipo.values()) {
            cartasDescartadas.putIfAbsent(tipo, 0);
        }
        //Actualizo la carta que me toco
        cartasDescartadas.remove(carta1.getTipo());
        cartasDescartadas.put(carta1.getTipo(), 1);
    }

    public void finTurno(JugadorServer jugador, Carta carta) {
        if (jugador.equals(this)) return;
        if (carta == null) return;

        int cant = cartasDescartadas.remove(carta.getTipo());
        cartasDescartadas.put(carta.getTipo(), ++cant);
        if (cartasConocidas.get(jugador) == carta.getTipo()) cartasConocidas.remove(jugador);
    }

    public void agregarCartaJugadorEliminado(Carta carta) {
        int cant = cartasDescartadas.remove(carta.getTipo());
        cartasDescartadas.put(carta.getTipo(), ++cant);
    }

    @Override
    public void salirSala() {
        super.salirSala();
        try {
            this.running = false;
            this.thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void tick() {
        if (this.getEstado().getEstadoActual() == null) return;

        if (this.getEstado().getEstadoActual() == EstadosJugador.DESCARTANDO ||
                this.getEstado().getEstadoActual() == EstadosJugador.DESCARTANDOCONDESA) {
            if (--tickcount <= 0) {
                tickcount = 200;
                elegirCartaAJugar();
                return;
            }
        }

        if (this.getEstado().getEstadoActual() == EstadosJugador.ELIGIENDOJUGADOR) {
            if (--tickcount <= 0) {
                tickcount = 200;
                try {
                    onElegirJugador();
                    return;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    exit(0);
                }
            }
        }

        if (this.getEstado().getEstadoActual() == EstadosJugador.ADIVINANDOCARTA) {
            if (--tickcount <= 0) {
                tickcount = 200;
                onAdivinarCarta();
            }
        }
    }

    @Override
    public void run() {
        final int SECOND = 1000;
        final int TICKS_PER_SECOND = 20;
        final int SKIP_TICKS = SECOND / TICKS_PER_SECOND;
        long next_game_tick = System.currentTimeMillis();
        while(this.running) {
            if (System.currentTimeMillis() > next_game_tick) {
                next_game_tick += SKIP_TICKS;
                tick();
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
