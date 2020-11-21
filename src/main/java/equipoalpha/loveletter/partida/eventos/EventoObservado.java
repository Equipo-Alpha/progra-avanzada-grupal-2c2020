package equipoalpha.loveletter.partida.eventos;

import equipoalpha.loveletter.server.JugadorServer;

import java.util.List;

public interface EventoObservado {
    void notificar(List<JugadorServer> observadores);

    void removerObservador(JugadorServer jugador);

    void cancelar();
}
