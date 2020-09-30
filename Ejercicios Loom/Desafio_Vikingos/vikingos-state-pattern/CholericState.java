package unlam.taller.vikingos;

public class CholericState implements VikingState {

	@Override
	public void printStatus() {
		System.out.println("Current status: Choleric");
	}

	@Override
	public void receiveAttack(Viking v) {
		v.setState(new BerserkerState());
	}

	@Override
	public int attack(Viking v) {
		return v.getAttackDamage() * 2;
	}

	@Override
	public void meditate(Viking v) {
		v.setState(new NormalState());
	}

}
