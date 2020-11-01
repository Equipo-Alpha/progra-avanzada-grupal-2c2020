package equipoalpha.loveletter.carta;

public enum CartaTipo {
	GUARDIA("guardia", 1, 5), 
	SACERDOTE("sacerdote", 2, 2), 
	BARON("baron", 3, 2), 
	MUCAMA("mucama", 4, 2),
	PRINCIPE("principe", 5, 2), 
	REY("rey", 6, 1), 
	CONDESA("condesa", 7, 1), 
	PRINCESA("princesa", 8, 1);

	public String nombre;
	public int fuerza;
	public int cantCartas;

	CartaTipo(String nombre, int fuerza, int cantCartas) {
		this.nombre = nombre;
		this.fuerza = fuerza;
		this.cantCartas = cantCartas;
	}
}
