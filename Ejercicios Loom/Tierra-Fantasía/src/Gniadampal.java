public class Gniadampal extends Raza {

	private int cantAtk;
	private int buff;

	public Gniadampal(int posicionX) {
		this.salud = 175;
		this.arma = "Catapulta";
		this.valorAtaque = 37;
		this.rangoAtaqueMax = 5;
		this.posicionX = posicionX;
		this.cantAtk = 0;
		this.buff = 0;
	}

	@Override
	public double atacar(Raza enemigo) {
		if (enemigo.posicionX > this.rangoAtaque) {
			if (cantAtk == 2) {
				if (buff >= 0) {
					buff--;
					return enemigo.salud = -(this.valorAtaque * 3) / (3 / 4);

				}
				cantAtk = 0;
				return enemigo.salud = -this.valorAtaque / (3 / 4);

			}
			cantAtk++;
			return enemigo.salud = -this.valorAtaque;

		}
		return 0;
	}

	@Override
	public void descansar() {
		buff += 2;
	}

	@Override
	public void recibirAtaque(Raza enem) {
		if (this.salud - (enem.valorAtaque / 2) <= 0) {
			this.salud = 0;
			return;
		}

		this.salud -= (enem.valorAtaque / 2);
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