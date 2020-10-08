
public class RazaTest extends Raza {
	private boolean descansado = false;	
	private int cantAtaquesRealizados = 0;
	private int cantAtaquesRecibidos = 0;
	
	public RazaTest(int xInicial) {
		super();
		this.salud = 150;
		this.posicionX = xInicial;
		this.valorAtaque = 10;
		this.rangoAtaque = 50;
	}
	
	@Override
	public double atacar(Raza o) {		
		return this.valorAtaque;
	}
	
	@Override
	public void recibirAtaque(Raza o) {
		this.salud -= 10;
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