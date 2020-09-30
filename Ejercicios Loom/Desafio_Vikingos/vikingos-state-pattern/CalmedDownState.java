package unlam.taller.vikingos;

public class CalmedDownState implements VikingState {

	@Override
	public void receiveAttack(Viking v) {
		v.setState(new CalmedDownState());
	}

	@Override
	public int attack(Viking v) {
		return 0;
	}

	@Override
	public void meditate(Viking v) {
		return; // Check if we have to do something
	}

	@Override
	public void printStatus() {
		System.out.println("Current status: Calmed Down");
	}


}
