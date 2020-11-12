package equipoalpha.loveletter.pantalla;

import equipoalpha.loveletter.LoveLetter;
import equipoalpha.loveletter.jugador.Jugador;
import equipoalpha.loveletter.partida.Sala;

import java.awt.*;
import java.util.ArrayList;

public class AnimacionInicioRonda {
    private final Sala sala;
    private boolean animandoCartaDescartada = true, animacionCartaIsFinished = false, animacionCartaStarted = false;
    private boolean animandoJ = false, animacionIsFinihedJ = false, animacionStartedJ = false;
    private boolean animandoJ1 = false, animacionIsFinihedJ1 = false, animacionStartedJ1 = false;
    private boolean animandoJ2 = false, animacionIsFinihedJ2 = false, animacionStartedJ2 = false;
    private boolean animandoJ3 = false, animacionIsFinihedJ3 = false, animacionStartedJ3 = false;
    private boolean dibujandoJ = false, dibujandoJ1 = false, dibujandoJ2 = false, dibujandoJ3 = false;
    private boolean iniciado = false;
    private float xIni, yIni;
    private ArrayList<Jugador> ordenJugadores;

    public AnimacionInicioRonda(Sala sala) {
        this.sala = sala;
    }

    public void animar(Graphics2D g2) {
        if (!iniciado) {
            iniciar();
            return;
        }

        if (animandoCartaDescartada) {
            animacionCartaStarted = true;
            animacionCartaIsFinished = false;
            animandoCartaDescartada = false;
            xIni = 460;
            yIni = 250;
        }
        if (animacionCartaStarted) {
            g2.drawImage(Imagenes.reversoPeq, null, (int) (xIni -= 2), (int) (yIni += 1.6));
            if (yIni >= 610 || xIni <= 10) animacionCartaIsFinished = true;
        }
        if (animacionCartaIsFinished) {
            animacionCartaStarted = false;
            g2.drawImage(Imagenes.reversoPeq, null, 10, 610);
            animacionCartaIsFinished = false;
            setSiguienteJugador();
        }

        if (animandoJ) {
            animacionStartedJ = true;
            animacionIsFinihedJ = false;
            animandoJ = false;
            xIni = 460;
            yIni = 250;
        }
        if (animacionStartedJ) {
            g2.drawImage(Imagenes.reversoPeq, null, 10, 610);
            g2.drawImage(Imagenes.reversoPeq, null, (int) xIni, (int) (yIni += 2));
            if (yIni >= 510) animacionIsFinihedJ = true;
        }
        if (animacionIsFinihedJ) {
            animacionStartedJ = false;
            animacionIsFinihedJ = false;
            dibujandoJ = true;
            setSiguienteJugador();
        }

        if (animandoJ1) {
            animacionStartedJ1 = true;
            animacionIsFinihedJ = false;
            animacionIsFinihedJ1 = false;
            animandoJ1 = false;
            xIni = 460;
            yIni = 250;
        }
        if (animacionStartedJ1) {
            g2.drawImage(Imagenes.reversoPeq, null, 10, 610);
            g2.drawImage(Imagenes.reversoPeq, null, (int) (xIni -= 2), (int) (yIni));
            if (xIni <= 10) animacionIsFinihedJ1 = true;
        }
        if (animacionIsFinihedJ1) {
            animacionStartedJ1 = false;
            animacionIsFinihedJ1 = false;
            dibujandoJ1 = true;
            setSiguienteJugador();
        }

        if (animandoJ2) {
            animacionStartedJ2 = true;
            animacionIsFinihedJ1 = false;
            animacionIsFinihedJ2 = false;
            animandoJ2 = false;
            xIni = 450;
            yIni = 250;
        }
        if (animacionStartedJ2) {
            g2.drawImage(Imagenes.reversoPeq, null, 10, 610);
            g2.drawImage(Imagenes.reversoPeq, null, (int) (xIni), (int) (yIni -= 2));
            if (yIni <= 10) animacionIsFinihedJ2 = true;
        }
        if (animacionIsFinihedJ2) {
            animacionStartedJ2 = false;
            animacionIsFinihedJ2 = false;
            dibujandoJ2 = true;
            setSiguienteJugador();
        }


        if (animandoJ3) {
            animacionStartedJ3 = true;
            animacionIsFinihedJ2 = false;
            animacionIsFinihedJ3 = false;
            animandoJ3 = false;
            xIni = 460;
            yIni = 250;
        }
        if (animacionStartedJ3) {
            g2.drawImage(Imagenes.reversoPeq, null, 10, 610);
            g2.drawImage(Imagenes.reversoPeq, null, (int) (xIni += 2), (int) (yIni));
            if (xIni >= 925) animacionIsFinihedJ3 = true;
        }
        if (animacionIsFinihedJ3) {
            animacionStartedJ3 = false;
            animacionIsFinihedJ3 = false;
            dibujandoJ3 = true;
            setSiguienteJugador();
        }

        if (dibujandoJ)
            g2.drawImage(LoveLetter.getInstance().getJugador().carta1.getImagen(), null, 380, 500);
        if (dibujandoJ1)
            g2.drawImage(Imagenes.reversoPeq, null, 10, 250);
        if (dibujandoJ2)
            g2.drawImage(Imagenes.reversoPeq, null, 400, 10);
        if (dibujandoJ3)
            g2.drawImage(Imagenes.reversoPeq, null, 925, 250);
    }

    private void setSiguienteJugador() {
        if (ordenJugadores.isEmpty()) {
            reset();
            return;
        }
        Jugador jugador = ordenJugadores.remove(0);
        int index = sala.partida.jugadores.indexOf(jugador);
        switch (index) {
            case 0:
                animandoJ = true;
                break;
            case 1:
                animandoJ1 = true;
                break;
            case 2:
                animandoJ2 = true;
                break;
            case 3:
                animandoJ3 = true;
                break;
        }
    }

    private void iniciar() {
        this.iniciado = true;
        if (sala.partida.rondaActual.ordenReparto == null)
            return;
        this.ordenJugadores = new ArrayList<>(sala.partida.rondaActual.ordenReparto);
    }

    private void reset() {
        animandoCartaDescartada = true;
        animacionCartaIsFinished = false;
        animacionCartaStarted = false;
        animandoJ = false;
        animacionIsFinihedJ = false;
        animacionStartedJ = false;
        animandoJ1 = false;
        animacionIsFinihedJ1 = false;
        animacionStartedJ1 = false;
        animandoJ2 = false;
        animacionIsFinihedJ2 = false;
        animacionStartedJ2 = false;
        animandoJ3 = false;
        animacionIsFinihedJ3 = false;
        animacionStartedJ3 = false;
        iniciado = false;
        dibujandoJ = false;
        dibujandoJ1 = false;
        dibujandoJ2 = false;
        dibujandoJ3 = false;
        sala.partida.rondaActual.initTurnos();
    }
}
