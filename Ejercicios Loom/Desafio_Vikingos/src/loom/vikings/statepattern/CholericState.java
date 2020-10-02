package loom.vikings.statepattern;

public class CholericState implements VikingState {

	@Override
	public String getStatus() {
		return "CHOLERIC";
	}

	@Override
	public void receiveAttack(Viking v) {
		v.setHealth(v.getHealth() - Viking.DEFAULT_CAUSED_DAMAGE * 2);
		v.setState(new BerserkerState());
	}

	@Override
	public int attack(Viking v) {
		return Viking.DEFAULT_CAUSED_DAMAGE * 2;
	}

	@Override
	public void meditate(Viking v) {
		v.setState(new NormalState());
	}

}
