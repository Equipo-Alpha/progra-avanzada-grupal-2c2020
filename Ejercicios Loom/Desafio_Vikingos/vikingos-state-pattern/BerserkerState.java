package unlam.taller.vikingos;

public class BerserkerState implements VikingState {

	@Override
	public void receiveAttack(Viking v) {
		//Check what to do when it receives an attack because there aren't other states after this
	}

	@Override
	public int attack(Viking v) {
		return v.getAttackDamage() * 3;		
	}

	@Override
	public void meditate(Viking v) {
		v.setState(new NormalState());
	}

	@Override
	public void printStatus() {
		System.out.println("Current status: Berseker");
	}



}
