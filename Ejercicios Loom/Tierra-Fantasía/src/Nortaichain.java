public class Nortaichain extends Raza {

	private static final int valorInicialSalud = 119;
	private static final int valorInicialAtaque = 43;

	public Nortaichain(int posIni) {
		this.salud = valorInicialSalud;
		this.arma = "katana";
		this.rangoAtaqueMax = 3;
		this.rangoAtaqueMin = 0;
		this.valorAtaque = valorInicialAtaque;
		this.posicionX = posIni;
	}

	@Override
	public double atacar(Raza objetivo) {
		if (objetivo.posicionX - this.posicionX < 0) {
			System.out.println(getClass().getName() + " ataca con una " + arma + " a " + objetivo.getClass().getName());
			double valorRecuperado = salud * 0.02;
			salud = (salud + valorRecuperado) > valorInicialSalud ? valorInicialSalud : (salud + valorRecuperado);
			return this.valorAtaque;
		}
		return 0;
	}

	@Override
	public void recibirAtaque(Raza atacante) {
		this.salud -= atacante.atacar(this);
		this.valorAtaque = valorInicialAtaque;
	}

	@Override
	public void descansar() {
		int valorRecuperado = valorInicialSalud / 2;
		salud = (salud + valorRecuperado) > valorInicialSalud ? valorInicialSalud : (salud + valorRecuperado);
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getSalud() {
		// TODO Auto-generated method stub
		return 0;
	}

}