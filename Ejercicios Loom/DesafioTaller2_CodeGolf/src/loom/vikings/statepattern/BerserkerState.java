package loom.vikings.statepattern;

public class BerserkerState implements VikingState {

	@Override
	public void receiveAttack(Viking v) {
		v.setHealth((int)(v.getHealth() - ((double)Viking.DEFAULT_CAUSED_DAMAGE / 2)));
	}

	@Override
	public int attack(Viking v) {
		return Viking.DEFAULT_CAUSED_DAMAGE * 3;	
	}

	@Override
	public void meditate(Viking v) {
		v.setState(new NormalState());
	}

	@Override
	public String getStatus() {
		return "BERSERKER";
	}



}
