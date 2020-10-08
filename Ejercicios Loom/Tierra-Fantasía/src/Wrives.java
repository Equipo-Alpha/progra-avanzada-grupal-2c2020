enum Estado {
	NORMAL, ENFURECIDO, MEDITANDO;
}

public class Wrives {
	
	public Estado state = Estado.NORMAL;
	public int salud = 87;
	public String arma= "Dientes";
	public double valorAtaque = 62;
	public int posicionX=0;
	public int ataquesRecibidos = 0;
	public int cantidadTurnos = 0;
	public double rangoAtaqueMax = 0.09;
	public double rangoAtaqueMin = 0;
	
	public double onAtaque(Raza objetivo) {
		if(puedoAtacar(objetivo))
		{
			if(cantidadTurnos >= 2)
			{
				this.state = Estado.NORMAL;
				cantidadTurnos = 0;
			}
			switch (state) {
			case NORMAL:
				cantidadTurnos++;
				return valorAtaque*(Math.pow(3, ataquesRecibidos));
			case ENFURECIDO:
				cantidadTurnos++;
				return valorAtaque*(Math.pow(3, ataquesRecibidos)) * 2;
			case MEDITANDO:
				return  0;
			}
		}
		return 0;
	}
	
	public void onRecibirAtaque(Raza atacante) {
		this.state = Estado.ENFURECIDO;
		ataquesRecibidos++;
		cantidadTurnos++;
		salud -= atacante.valorAtaque;
		if(cantidadTurnos >= 2)
		{
			this.state = Estado.NORMAL;
			cantidadTurnos = 0;
		}
	}
	
	public void onDescansar() {
		cantidadTurnos++;
		this.state = Estado.MEDITANDO;
		salud += 50;
	}
	
	public boolean puedoAtacar(Raza objetivo) {
		if(objetivo.posicionX < rangoAtaqueMax)
			return true;
		return false;
	}
	
}
