
public abstract class Raza {
	public double salud;
	public String arma;
	public int rangoAtaque;
	public double valorAtaque;
	public int posicionX;
	public double rangoAtaqueMax;
	public double rangoAtaqueMin;
	
	public abstract int getX();
	public abstract double atacar(Raza o);
	public abstract void recibirAtaque(Raza o);
	public abstract void descansar();
	public abstract double getSalud();
}
