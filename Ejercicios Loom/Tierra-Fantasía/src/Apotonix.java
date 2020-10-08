public class Apotonix extends Raza{

	private boolean descansado = false;	
	private int cantAtaquesRealizados = 0;
	private int cantAtaquesRecibidos = 0;
	public double salud;
	public String arma;
	public int rangoAtaque;
	public double valorAtaque;
	public int posicionX;
	
	public Apotonix(int xInicial) {
		super();
		this.salud = 98;
		this.posicionX = xInicial;
		this.valorAtaque = 26;
		this.rangoAtaque = 18;
	}
	
	@Override	
	public double atacar(Raza o) {
		if(Math.abs(o.getX() - this.posicionX) > this.rangoAtaque){
			return 0;
		}
		
		return this.valorAtaque + 2 * this.cantAtaquesRealizados++;
	}
	
	@Override
	public void recibirAtaque(Raza o) {
		this.salud -= (o.atacar(this) * (this.descansado ? 0.25 : 1));
		this.salud *= 1.25;
		this.cantAtaquesRecibidos++;
		this.descansado = false;
	}
	
	@Override
	public void descansar() {
		this.descansado = true;
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return this.posicionX;
	}

	@Override
	public double getSalud() {
		// TODO Auto-generated method stub
		return this.salud;
	}
}

