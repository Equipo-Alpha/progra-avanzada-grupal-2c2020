package loom.vikings.statepattern;

public class NormalState implements VikingState {

	@Override
	public void receiveAttack(Viking v) {
		v.setHealth(v.getHealth() - Viking.DEFAULT_CAUSED_DAMAGE);
		v.setState(new CholericState());
	}

	@Override
	public int attack(Viking v) {
		return Viking.DEFAULT_CAUSED_DAMAGE;
	}

	@Override
	public void meditate(Viking v) {
		v.setState(new CalmedDownState());
	}

	@Override
	public String getStatus() {
		return "NORMAL";
	}

}
