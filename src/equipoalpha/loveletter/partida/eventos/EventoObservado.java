package equipoalpha.loveletter.partida.eventos;

import equipoalpha.loveletter.jugador.Jugador;

import java.util.List;

public interface EventoObservado {
    void notificar(List<Jugador> observadores);

    void removerObservador(Jugador jugador);

    void cancelar();
}
