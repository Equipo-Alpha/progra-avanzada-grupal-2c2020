package unlam.taller.vikingos;

public class NormalState implements VikingState {

	@Override
	public void receiveAttack(Viking v) {
		v.setState(new CholericState());
	}

	@Override
	public int attack(Viking v) {
		return v.getAttackDamage();
		
	}

	@Override
	public void meditate(Viking v) {
		v.setState(new CalmedDownState());
	}

	@Override
	public void printStatus() {
		System.out.println("Current status: Normal");
	}

}
