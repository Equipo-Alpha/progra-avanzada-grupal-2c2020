package equipoalpha.loveletter.partida;

import equipoalpha.loveletter.carta.Carta;
import equipoalpha.loveletter.carta.CartaTipo;

public class Jugador implements Comparable<Jugador>{
	
	/*
	 * Nombre del jugador
	 */
	public String nombre;
	
	/*
	 * La mano del jugador
	 * carta1 es la carta que siempre tiene en la mano
	 * carta2 es la carta que roba del mazo
	 */
	public Carta carta1;
	public Carta carta2;
	
	/*
	 * La partida en la que actualmente esta jugando
	 */
	public Partida partidaJugando;
	
	/*
	 * Determina si el jugador esta o no protegido por haber descartado una mucama
	 */
	public boolean estaProtegido;
	
	/*
	 * Cantidad de simbolos de afecto que tiene el jugador
	 */
	public int cantSimbolosAfecto;
	
	public Jugador(String nombre) {
		this.nombre = nombre;
	}
	/*
	 * Crea una partida
	 */
	public Partida crearPartida() {
		return new Partida(this);
	}
	
	protected Carta descartarCarta1() {
		Carta cartaElegida = this.carta1;
		carta1.descartar(this);
		carta1 = carta2;
		carta2 = null;
		return cartaElegida;
	}
	
	protected Carta descartarCarta2() {
		Carta cartaElegida = this.carta2;
		carta2.descartar(this);
		carta2 = null;
		return cartaElegida;
	}
	
	/*
	 * roba una carta del mazo al principio del turno
	 */
	public void robarCarta(Carta cartaRobada) {
		carta2 = cartaRobada;
		
		//TODO: mover esta logica?
		if(cartaRobada.getTipo() == CartaTipo.CONDESA
				&& (carta1.getTipo() == CartaTipo.REY || carta1.getTipo() == CartaTipo.PRINCIPE)) {
			descartarCarta2();
		}
	}
	
	/*
	 * Generalmente llamado por otro jugador cuando descarta al guardia
	 */
	public boolean tieneCarta(Carta carta) {
		return carta1.equals(carta);
	}
	
	@Override
	public int compareTo(Jugador o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
